package examples.apps.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.groundtruth.MarkerBuilder;
import com.harium.propan.core.graphics.Graphics3D;
import examples.apps.BaseApplication;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static examples.apps.tiles.TileDiffuseFloor.buildTile;

public class TileTextureFloor extends BaseApplication {

    protected static final float FOV = 60;

    protected ModelBatch modelBatch;
    protected Environment environment;

    protected FPSCameraControllerV2 controller;

    protected List<ModelInstance> instances = new ArrayList<>();

    private Texture texture;

    public TileTextureFloor(int w, int h) {
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
        texture = new Texture(Gdx.files.internal("grass_001/grass_001_color.jpg"));
        instances.add(buildTile(0, 0, texture));
    }

    public static ModelInstance buildTile(int x, int y, Texture texture) {
        ModelBuilder modelBuilder = new ModelBuilder();
        MarkerBuilder markerBuilder = new MarkerBuilder();
        Model markerModel = markerBuilder.tile(modelBuilder, texture);
        ModelInstance marker = new ModelInstance(markerModel, x, y, 0);

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
