package examples.apps.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.groundtruth.MarkerBuilder;
import com.harium.propan.FixedPerspectiveCamera;
import com.harium.propan.core.graphics.Graphics3D;
import examples.apps.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class MirrorRoom extends BaseApplication {

    private PerspectiveCamera mirrorCamera;
    private ModelInstance mirrorPlane;

    protected static final float FOV = 60;
    // 1 cm
    public static final float TILE_HEIGHT = 0.01f;
    public static final float BOX_SIZE = 1f;

    protected ModelBatch modelBatch;
    protected Environment environment;

    protected FPSCameraControllerV2 controller;

    protected List<ModelInstance> instances = new ArrayList<>();

    private ModelInstance floor;

    public MirrorRoom(int w, int h) {
        super(w, h);
    }

    public void init(Graphics3D g) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        camera = new FixedPerspectiveCamera(FOV, w, h);
        camera.position.set(0, 1.6f, -5);
        camera.lookAt(0,0,0);
        camera.near = 0.01f;
        camera.far = 1000f;
        camera.update();

        mirrorCamera = new FixedPerspectiveCamera(FOV, w, h);
        updateMirror();

        controller = new FPSCameraControllerV2(camera);

        // Setup scene
        modelBatch = new ModelBatch();

        // Floor
        ModelInstance green = buildBox(0, 1, 0,Color.GREEN);
        instances.add(green);

        ModelInstance blue = buildBox(2, 1, 0,Color.BLUE);
        instances.add(blue);

        ModelInstance red = buildBox(0, 1, 4, Color.RED);
        instances.add(red);

        mirrorPlane = Room.buildTile(0,0, new Color(1,1,1,0.8f));
        //mirrorPlane.transform.rotate(Vector3.Z, 180);
        mirrorPlane.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        mirrorPlane.transform.scl(8);
    }

    private void updateMirror() {
        // Reflect camera based on mirror position
        mirrorCamera.position.set(camera.position);
        mirrorCamera.direction.set(camera.direction);
        mirrorCamera.up.set(camera.up);
        mirrorCamera.near = camera.near;
        mirrorCamera.far = camera.far * 20;
        mirrorCamera.update();

        mirrorCamera.combined.set(camera.combined).scl(1,-1,1);
        //mirrorCamera.transform(new Matrix4().scl(1,-1,1));

        //mirrorCamera.update();

        // Reflect Camera
        /*Ray ray = new Ray(camera.position, camera.direction);
        Plane mirrorPlane = new Plane(Vector3.Y, 0);
        Vector3 intersection = new Vector3();
        Intersector.intersectRayPlane(ray, mirrorPlane, intersection);
        mirrorCamera.rotateAround(intersection, Vector3.Z, 180);*/

        /*Matrix4 matrix4 = new Matrix4().setToScaling(1,-1,1);
        mirrorCamera.transform(matrix4);
        mirrorCamera.lookAt(0,0,0);
        mirrorCamera.update();*/
    }

    private ModelInstance buildBox(int x, int y, int z, Color color) {
        ModelBuilder modelBuilder = new ModelBuilder();
        MarkerBuilder markerBuilder = new MarkerBuilder();
        Model markerModel = markerBuilder.box(modelBuilder, BOX_SIZE, color);
        ModelInstance marker = new ModelInstance(markerModel, x, y, z);

        return marker;
    }

    public void display(Graphics3D graphics3D) {
        //displayReflectedScene(graphics3D);
        displayMirror(graphics3D);
    }

    public void displayReflectedScene(Graphics3D graphics3D) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        modelBatch.begin(camera);
        for (ModelInstance marker : instances) {
            modelBatch.render(marker, environment);
        }
        modelBatch.end();

        modelBatch.begin(mirrorCamera);
        for (ModelInstance marker : instances) {
            modelBatch.render(marker, environment);
        }
        modelBatch.render(mirrorPlane);
        modelBatch.end();
    }

    public void displayMirror(Graphics3D graphics3D) {
        /* Fill with one stencil buffer where the ground is */
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 1);
        Gdx.gl.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);
        Gdx.gl.glColorMask(false, false, false, false);
        //Gdx.gl.glDepthMask(false);
        // Draw Mirror plane without lights to mask the stencil
        modelBatch.begin(camera);
        modelBatch.render(mirrorPlane);
        modelBatch.end();
        //Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(true, true, true, true);

        /* Reflection */
        Gdx.gl.glStencilFunc(GL20.GL_EQUAL, 1, 1); //Draw only where stencil buffer is 1
        Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP); //Stencil buffer read only

        // Mirror camera
        // Draw scene reflected
        modelBatch.begin(mirrorCamera);
        for (ModelInstance marker : instances) {
            modelBatch.render(marker, environment);
        }
        // Do not render the plane
        modelBatch.end();
        Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);

        /* Draw Scene */
        modelBatch.begin(camera);
        for (ModelInstance marker : instances) {
            modelBatch.render(marker, environment);
        }
        modelBatch.render(mirrorPlane);
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
        updateMirror();
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        super.updateKeyboard(event);
        controller.updateKeyboard(event);
    }
}
