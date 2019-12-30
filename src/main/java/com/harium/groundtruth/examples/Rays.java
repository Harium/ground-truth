package com.harium.groundtruth.examples;

import static com.harium.groundtruth.examples.SphereRays.whiteMaterial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.propan.core.camera.FPSCameraController;
import com.harium.propan.core.graphics.Graphics3D;

public class Rays extends BaseApplication {

  private static final float FOV = 60;
  private static final float RADIUS = 0.2f;

  ModelBatch modelBatch;
  Environment environment;

  Model model;
  ModelInstance sphere;
  ModelInstance grid;

  ModelInstance rayA;
  ModelInstance rayB;
  ModelInstance rayC;

  private FPSCameraController controller;

  public Rays(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new PerspectiveCamera(FOV, w, h);
    camera.position.set(0, 0, 1);
    camera.lookAt(0, 0, -1);
    camera.near = 0.1f;
    camera.far = 300f;
    camera.update();

    controller = new FPSCameraController(camera);

    modelBatch = new ModelBatch();

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();
    Model sphere = modelBuilder.createSphere(RADIUS * 2, RADIUS * 2, RADIUS * 2, 32, 32,
        whiteMaterial(),
        VertexAttributes.Usage.Position);
    this.sphere = new ModelInstance(sphere, 0, 0, -0.2f);

    buildGrid(modelBuilder);

    // Defining Rays

    // 172, 76
    // 209, 38
    // 256, 80
    //camera.unproject()

    float sphereZ = -0.2f;
    float raySize = 100;

    Vector3 rA = camera.unproject(new Vector3(172, 78, sphereZ));
    //rA.nor();
    //rA.scl(raySize);

    Vector3 rB = camera.unproject(new Vector3(209, 38, sphereZ));
    //rB.nor();
    //rB.scl(raySize);

    Vector3 rC = camera.unproject(new Vector3(256, 80, sphereZ));
    //rC.nor();
    //rC.scl(raySize);

    MeshPartBuilder builder;

    rayA = rayModel(modelBuilder, rA, "Ray A");
    rayB = rayModel(modelBuilder, rB, "Ray B");
    rayC = rayModel(modelBuilder, rC, "Ray C");
  }

  private ModelInstance rayModel(ModelBuilder modelBuilder, Vector3 ray, String id) {
    MeshPartBuilder builder;
    modelBuilder.begin();
    builder = modelBuilder.part(id, 1, 3, new Material());
    builder.setColor(Color.BLUE);
    builder.line(Vector3.Zero, ray);
    Model lineModel = modelBuilder.end();
    return new ModelInstance(lineModel);
  }

  private void buildGrid(ModelBuilder modelBuilder) {
    MeshPartBuilder builder;
    modelBuilder.begin();
    builder = modelBuilder.part("Grid", 1, 3, new Material());

    float gridY = -0.1f;
    float gridX = -4f;
    float gridZ = -4f;

    float spacing = 0.1f;
    float size = 300f;
    // Vertical Lines
    builder.setColor(Color.GREEN);
    for (int i = 0; i < 80; i++) {
      builder.line(-size, 0, gridZ + spacing * i, size, 0, gridZ + spacing * i);
    }

    // Horizontal Lines
    builder.setColor(Color.RED);
    for (int i = 0; i < 100; i++) {
      builder.line(gridX + spacing * i, 0, -size, gridX + spacing * i, 0, size);
    }

    Model gridModel = modelBuilder.end();
    grid = new ModelInstance(gridModel, 0, gridY, 0);
  }

  private Material solidMaterial(Color color) {
    Material whiteMaterial = new Material();
    whiteMaterial.set(ColorAttribute.createDiffuse(color));
    return whiteMaterial;
  }

  public void display(Graphics3D graphics3D) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0, 0, 0, 1f);

    /*renderer.begin();
    renderer.line(Vector3.Zero, new Vector3(10,10, -1));
    renderer.end();*/
    /*Gdx.gl.glLineWidth(1);
    renderer.setProjectionMatrix(camera.combined);
    renderer.begin(ShapeRenderer.ShapeType.Line);

    float spacing = 100f;
    float size = 100f;
    renderer.setColor(Color.GREEN);
    // Vertical Lines
    for (int i = 0; i < 20; i++) {
      renderer.line(new Vector3(-size / 2, 0, spacing * i), new Vector3(size / 2, 0, spacing * i));
    }
    //renderer.line( new Vector3(10,10,-10), new Vector3(10,10,10));
    //renderer.line( new Vector3(0,0,-100), new Vector3(0,0,100));
    //renderer.line( new Vector3(-100,0,-100), new Vector3(-100,0,-100));
    renderer.end();
    Gdx.gl.glLineWidth(1);*/

    modelBatch.begin(camera);
    modelBatch.render(grid, environment);
    modelBatch.render(sphere, environment);
    modelBatch.render(rayA, environment);
    modelBatch.render(rayB, environment);
    modelBatch.render(rayC, environment);
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
