package examples.apps.markers;

import static com.harium.groundtruth.GridBuilder.buildGrid;

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
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.groundtruth.FPSCameraControllerV2;
import com.harium.groundtruth.MarkerBuilder;
import com.harium.propan.FixedPerspectiveCamera;
import com.harium.propan.core.graphics.Graphics3D;
import examples.apps.BaseApplication;
import java.util.ArrayList;
import java.util.List;

public class MultipleCircleMarker extends BaseApplication {

  private static final float FOV = 60;

  ModelBatch modelBatch;
  Environment environment;

  Model model;
  ModelInstance grid;

  ModelInstance originMarker;
  ModelInstance leftMarker;
  ModelInstance upperMarker;
  List<ModelInstance> markers = new ArrayList<>();

  private FPSCameraControllerV2 controller;

  public MultipleCircleMarker(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new FixedPerspectiveCamera(FOV, w, h);
    camera.position.set(0, 2, 3);
    // Do not change it
    camera.lookAt(camera.position.x, camera.position.y, camera.position.z - 1);
    camera.near = 0.1f;
    camera.far = 1800f;
    camera.update();

    controller = new FPSCameraControllerV2(camera);
    controller.turnPitch(-20);

    modelBatch = new ModelBatch();

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();

    MarkerBuilder markerBuilder = new MarkerBuilder();

    Model originMarkerModel = getMarkerModel(modelBuilder, markerBuilder, Color.BLACK);
    this.originMarker = new ModelInstance(originMarkerModel, 0, 0, 0);

    Model blueMarkerModel = getMarkerModel(modelBuilder, markerBuilder, Color.BLUE);
    this.leftMarker = new ModelInstance(blueMarkerModel, -1, 0, 0);

    Model yellowMarkerModel = getMarkerModel(modelBuilder, markerBuilder, Color.YELLOW);
    this.upperMarker = new ModelInstance(yellowMarkerModel, 0, 0, -1);

    markers.add(originMarker);
    markers.add(leftMarker);
    markers.add(upperMarker);

    float gridY = -0.01f;
    float gridX = -4f;
    float gridZ = -4f;

    Model gridModel = buildGrid(modelBuilder, gridX, gridZ);
    grid = new ModelInstance(gridModel, 0, gridY, 0);
  }

  private Model getMarkerModel(ModelBuilder modelBuilder, MarkerBuilder markerBuilder, Color blue) {
    return markerBuilder.color(blue).circleMarker(modelBuilder, 0.1f);
  }

  public void display(Graphics3D graphics3D) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0, 0, 0, 1f);

    modelBatch.begin(camera);
    modelBatch.render(grid, environment);

    for (ModelInstance marker : markers) {
      modelBatch.render(marker, environment);
    }

    modelBatch.end();
  }

  public void dispose() {
    model.dispose();
    modelBatch.dispose();
  }

  @Override
  protected String getFilename() {
    return "multiple-" + System.currentTimeMillis();
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
