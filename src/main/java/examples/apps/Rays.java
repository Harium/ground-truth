package examples.apps;

import static com.harium.groundtruth.GridBuilder.buildGrid;
import static examples.apps.SphereRays.whiteMaterial;

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

  ModelInstance rayAO;
  ModelInstance rayBO;
  ModelInstance rayCO;

  float cx, cy, fx, fy;

  private FPSCameraController controller;

  public Rays(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new PerspectiveCamera(FOV, w, h);
    camera.position.set(0, 0, 0);
    camera.lookAt(0, 0, -1);
    camera.near = 0.1f;
    camera.far = 1800f;
    camera.update();

    // Intrinsics
    //cx = w / 2;
    //cy = h / 2;
    //fx = camera.combined.val[Matrix3.M00] * cx;
    //fy = camera.combined.val[Matrix3.M00] * cy;

    cx = 320;
    cy = 240;
    fx = 415.69217f;
    fy = 311.76913f;

    System.out.println("fx: " + fx);
    System.out.println("fy: " + fy);
    System.out.println("cx: " + cx);
    System.out.println("cy: " + cy);

    /*fx: 311.76913
    fy: 233.82686
    cx: 320.0
    cy: 240.0*/

    controller = new FPSCameraController(camera);

    modelBatch = new ModelBatch();

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();
    /*Model sphere = modelBuilder.createSphere(RADIUS*2, RADIUS*2, RADIUS*2, 32, 32,
        whiteMaterial(),
        VertexAttributes.Usage.Position);*/
    //this.sphere = new ModelInstance(sphere, -0.5f, 0.75f, -1f);


    Model sphere = modelBuilder.createSphere(RADIUS * 2, RADIUS * 2, RADIUS * 2, 32, 32,
        whiteMaterial(),
        VertexAttributes.Usage.Position);
    //this.sphere = new ModelInstance(sphere, 0, 0, -1f);
    this.sphere = new ModelInstance(sphere, -0.5f, 0.75f+RADIUS, -2f);

    float gridY = -0.1f;
    float gridX = -4f;
    float gridZ = -4f;

    Model gridModel = buildGrid(modelBuilder, gridX, gridZ);
    grid = new ModelInstance(gridModel, 0, gridY, 0);

    // Defining Rays

    // 172, 76
    // 209, 38
    // 256, 80
    //camera.unproject()

    float sphereZ = -0.2f;
    float raySize = 1500;

    Vector3 rA = getRay(172, 78, sphereZ, raySize);
    Vector3 rB = getRay(209, 38, sphereZ, raySize);
    Vector3 rC = getRay(256, 80, sphereZ, raySize);

    Vector3 rAO = getRay(390, 240, sphereZ, raySize);
    Vector3 rBO = getRay(250, 240, sphereZ, raySize);

    MeshPartBuilder builder;

    rayA = rayModel(modelBuilder, rA, "Ray A");
    rayB = rayModel(modelBuilder, rB, "Ray B");
    rayC = rayModel(modelBuilder, rC, "Ray C");

    rayAO = rayModel(modelBuilder, rAO, "Ray AO");

    Vector3 up = new Vector3(-5.427526f,6.7517347f,-21.098787f);
    Vector3 down = new Vector3(6.529201f,-5.1549277f,-19.245327f);

    //Vector3 v = new Vector3(down).sub(up);

    //v.scl(2);
    rayAO = new ModelInstance(sphere, up.scl(0.1f));
    rayBO = new ModelInstance(sphere, down.scl(0.1f));
    //rayBO = rayModel(modelBuilder, v, "Ray BO");
  }

  private Vector3 getRay(int x, int y, float sphereZ, float raySize) {

    //float ndcX = (float) (2.0 * x) / cx * 2 - 1.0f;
    // Invert y to compensate screen position
    //float ndcY = (float) -((2.0 * y) / cy * 2 - 1.0f);

    float u = (x - cx) / fx;
    float v = (y - cy) / fy;
    Vector3 ray = new Vector3(u, -v, -1);

    /*Vector3 ray = camera.unproject(new Vector3(x, y, sphereZ));
    ray.y = -ray.y;
    ray.z = -ray.z;*/
    ray.nor();
    ray.scl(raySize);
    return ray;
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
    //modelBatch.render(rayA, environment);
    //modelBatch.render(rayB, environment);
    //modelBatch.render(rayC, environment);

    // Origin Rays
    modelBatch.render(rayAO, environment);
    modelBatch.render(rayBO, environment);
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
