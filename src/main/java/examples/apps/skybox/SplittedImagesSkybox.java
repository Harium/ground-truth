package examples.apps.skybox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.propan.core.graphics.Graphics3D;
import examples.apps.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class SplittedImagesSkybox extends BaseApplication {

    protected static final float FOV = 60;

    protected ModelBatch modelBatch;
    protected Environment environment;

    protected FPSCameraControllerV2 controller;

    protected List<ModelInstance> instances = new ArrayList<>();

    private CubeSkybox skybox;

    public SplittedImagesSkybox(int w, int h) {
        super(w, h);
    }

    public void init(Graphics3D g) {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        camera = new PerspectiveCamera(FOV, w, h);
        camera.position.set(0, 1.6f, -2);
        camera.direction.set(0,0,1);
        camera.near = 0.1f;
        camera.far = 1800f;
        camera.update();

        controller = new FPSCameraControllerV2(camera);

        modelBatch = new ModelBatch();

        // Floor
        skybox = new CubeSkybox(camera.far);

        skybox.bottom = new Texture(Gdx.files.internal("skybox_sh/sh_dn.png"));
        skybox.top = new Texture(Gdx.files.internal("skybox_sh/sh_up.png"));
        skybox.front = new Texture(Gdx.files.internal("skybox_sh/sh_ft.png"));
        skybox.back = new Texture(Gdx.files.internal("skybox_sh/sh_bk.png"));
        skybox.left = new Texture(Gdx.files.internal("skybox_sh/sh_lf.png"));
        skybox.right = new Texture(Gdx.files.internal("skybox_sh/sh_rt.png"));
        skybox.init();

        instances.add(skybox.bottomTile);
        instances.add(skybox.topTile);
        instances.add(skybox.frontTile);
        instances.add(skybox.backTile);
        instances.add(skybox.rightTile);
        instances.add(skybox.leftTile);
    }

    public void display(Graphics3D graphics3D) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1f);

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
        return "skybox";
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
