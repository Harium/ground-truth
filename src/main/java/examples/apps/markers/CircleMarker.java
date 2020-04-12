package examples.apps.markers;

import static com.harium.groundtruth.GridBuilder.buildGrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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

public class CircleMarker extends BaseApplication {

  private static final float FOV = 60;

  ModelBatch modelBatch;
  Environment environment;

  Model model;
  ModelInstance grid;
  ModelInstance marker;

  private FPSCameraControllerV2 controller;

  public CircleMarker(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new PerspectiveCamera(FOV, w, h);
    camera.position.set(0, 2, 3);
    // Do not change it
    camera.lookAt(camera.position.x, camera.position.y, camera.position.z-1);
    camera.near = 0.1f;
    camera.far = 1800f;
    camera.update();

    controller = new FPSCameraControllerV2(camera);
    controller.turnPitch(-20);

    modelBatch = new ModelBatch();

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();

    MarkerBuilder markerBuilder = new MarkerBuilder();

    Model markerModel = markerBuilder.circleMarker(modelBuilder, 0.1f);
    this.marker = new ModelInstance(markerModel, 0, 0, 0);

    float gridY = -0.01f;
    float gridX = -4f;
    float gridZ = -4f;

    Model gridModel = buildGrid(modelBuilder, gridX, gridZ);
    grid = new ModelInstance(gridModel, 0, gridY, 0);
  }

  public void display(Graphics3D graphics3D) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0, 0, 0, 1f);

    modelBatch.begin(camera);
    modelBatch.render(marker, environment);
    modelBatch.render(grid, environment);

    modelBatch.end();
  }

  public void dispose() {
    model.dispose();
    modelBatch.dispose();
  }

  @Override
  protected String getFilename() {
    return "r02x-05y075z-2";
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
