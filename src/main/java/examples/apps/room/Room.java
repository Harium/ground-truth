package examples.apps.room;

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

import java.util.ArrayList;
import java.util.List;

public class Room extends BaseApplication {

    protected static final float FOV = 60;
    // 1 cm
    public static final float TILE_HEIGHT = 0.01f;

    protected ModelBatch modelBatch;
    protected Environment environment;

    protected FPSCameraControllerV2 controller;

    protected List<ModelInstance> instances = new ArrayList<>();

    public Room(int w, int h) {
        super(w, h);
    }

    public void init(Graphics3D g) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        camera = new PerspectiveCamera(FOV, w, h);
        camera.position.set(0, 1.6f, -2);
        camera.lookAt(0,0,0);
        camera.near = 0.1f;
        camera.far = 180f;
        camera.update();

        controller = new FPSCameraControllerV2(camera);

        modelBatch = new ModelBatch();

        // Floor
        instances.add(buildTile(0, 0, Color.WHITE));

        // Back
        ModelInstance back = buildTile(0, 0, Color.BLUE);
        back.transform.translate(0,0.5f, 0.5f);
        back.transform.rotate(Vector3.X, 90);
        instances.add(back);

        // Right
        ModelInstance right = buildTile(0, 0, Color.RED);
        right.transform.translate(0.5f,0.5f,0);
        right.transform.rotate(Vector3.Y, 90);
        right.transform.rotate(Vector3.X, 90);
        instances.add(right);
    }

    public static ModelInstance buildTile(int x, int y, Color color) {
        ModelBuilder modelBuilder = new ModelBuilder();
        MarkerBuilder markerBuilder = new MarkerBuilder();
        Model markerModel = markerBuilder.tileBox(modelBuilder, TILE_HEIGHT, color);
        ModelInstance marker = new ModelInstance(markerModel, x, y, TILE_HEIGHT);
        //marker.transform.rotate(Vector3.X, 90);

        return marker;
    }

    public void display(Graphics3D graphics3D) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        // Transparent background
        //Gdx.gl.glClearColor(0, 0, 0, 0f);

        modelBatch.begin(camera);
        for (ModelInstance marker : instances) {
            modelBatch.render(marker, environment);
        }
        modelBatch.end();
    }

    public void dispose() {
        for (ModelInstance marker : instances) {
            marker.model.dispose();
        }
        modelBatch.dispose();
    }

    @Override
    protected String getFilename() {
        return "room";
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
