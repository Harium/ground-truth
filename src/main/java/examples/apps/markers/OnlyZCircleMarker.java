package examples.apps.markers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.groundtruth.MarkerBuilder;
import com.harium.propan.core.graphics.Graphics3D;
import examples.apps.BaseApplication;

public class OnlyZCircleMarker extends BaseApplication {

    private static final float FOV = 60;
    // 3 cm
    public static final float SIZE_IN_M = 0.03f;

    ModelBatch modelBatch;
    Environment environment;

    Model model;
    ModelInstance marker;

    private FPSCameraControllerV2 controller;

    public OnlyZCircleMarker(int w, int h) {
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

        modelBatch = new ModelBatch();
        ModelBuilder modelBuilder = new ModelBuilder();

        MarkerBuilder markerBuilder = new MarkerBuilder().color(Color.BLUE);

        Model markerModel = markerBuilder.circleMarker(modelBuilder, SIZE_IN_M);
        this.marker = new ModelInstance(markerModel, 0, 0, 1);
        this.marker.transform.rotate(Vector3.X, 90);
    }

    public void display(Graphics3D graphics3D) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1f);

        modelBatch.begin(camera);
        modelBatch.render(marker, environment);
        modelBatch.end();
    }

    public void dispose() {
        model.dispose();
        modelBatch.dispose();
    }

    @Override
    protected String getFilename() {
        return "z_only";
    }

    @Override
    public void update(long now) {
        super.update(now);
        controller.update(now);
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        super.updateKeyboard(event);
        controller.updateKeyboard(event);
    }
}
