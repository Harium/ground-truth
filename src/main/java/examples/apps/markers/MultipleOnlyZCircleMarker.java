package examples.apps.markers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.groundtruth.MarkerBuilder;
import com.harium.propan.core.graphics.Graphics3D;

public class MultipleOnlyZCircleMarker extends OnlyZCircleMarker {

    public MultipleOnlyZCircleMarker(int w, int h) {
        super(w, h);
    }

    public void init(Graphics3D g) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        camera = new PerspectiveCamera(FOV, w, h);
        camera.position.set(0, 0, 0);
        // Look into +Z direction
        camera.lookAt(camera.position.x, camera.position.y, camera.position.z + 1);
        camera.near = 0.1f;
        camera.far = 180f;
        camera.update();

        controller = new FPSCameraControllerV2(camera);

        modelBatch = new ModelBatch();

        instances.add(buildMarker(0, 0, DISTANCE_IN_M, Color.BLUE));
        instances.add(buildMarker(0.3f, 0, DISTANCE_IN_M, Color.RED));
        instances.add(buildMarker(-0.3f, 0, DISTANCE_IN_M, Color.YELLOW));
        instances.add(buildMarker(0, 0.2f, DISTANCE_IN_M, Color.GREEN));
        instances.add(buildMarker(0, -0.2f, DISTANCE_IN_M, Color.MAGENTA));
    }

    private ModelInstance buildMarker(float x, float y, float z, Color color) {
        ModelBuilder modelBuilder = new ModelBuilder();
        MarkerBuilder markerBuilder = new MarkerBuilder().color(color);
        Model markerModel = markerBuilder.circleMarker(modelBuilder, SIZE_IN_M);
        ModelInstance marker = new ModelInstance(markerModel, x, y, z);
        marker.transform.rotate(Vector3.X, 90);

        return marker;
    }

    @Override
    protected String getFilename() {
        return "multi_z_only";
    }

}
