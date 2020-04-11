package examples.apps;

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
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.graphics.Graphics3D;

public class SphereRays extends BaseApplication {

  private static final float FOV = 60;
  private static final float RADIUS = 0.2f;

  ModelBatch modelBatch;
  Environment environment;

  Model model;
  ModelInstance instance;

  ModelInstance rayA;
  ModelInstance rayB;
  ModelInstance rayC;

  public SphereRays(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new PerspectiveCamera(FOV, w, h);
    camera.position.set(0, 0, 0);
    camera.lookAt(0, 0, -1);
    camera.near = 0.1f;
    camera.far = 300f;
    camera.update();

    // 172, 76
    // 209, 38
    // 256, 80
    //camera.unproject()

    float sphereZ = -2;

    float raySize = 100;

    Vector3 rA = camera.unproject(new Vector3(172, 78, sphereZ));
    //rA.nor();
    //rA.scl(raySize);

    Vector3 rB = camera.unproject(new Vector3(209, 38, sphereZ));
    rB.nor();
    rB.scl(raySize);

    Vector3 rC = camera.unproject(new Vector3(256, 80, sphereZ));
    rC.nor();
    rC.scl(raySize);

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();
    Model sphere = modelBuilder.createSphere(RADIUS * 2, RADIUS * 2, RADIUS * 2, 32, 32,
        whiteMaterial(),
        VertexAttributes.Usage.Position);
    instance = new ModelInstance(sphere, -0.5f, 0.75f, sphereZ);

    /*modelBuilder.begin();
    MeshPartBuilder builder = modelBuilder.part("Ray A", 1, 3, new Material());
    builder.setColor(Color.RED);
    builder.line(0.0f, 0.0f, 0f, rA.x, rA.y, rA.z);
    Model lineModel = modelBuilder.end();
    rayA = new ModelInstance(lineModel);*/

    Model ray = modelBuilder.createSphere(RADIUS * 2, RADIUS*2, RADIUS * 2, 32, 32,
        solidMaterial(Color.RED),
        VertexAttributes.Usage.Position);
    rayA = new ModelInstance(ray, rA.x, rA.y, rA.z);

  }

  public static Material whiteMaterial() {
    return solidMaterial(Color.WHITE);
  }

  public static Material solidMaterial(Color color) {
    Material whiteMaterial = new Material();
    whiteMaterial.set(ColorAttribute.createDiffuse(color));
    return whiteMaterial;
  }

  public void display(Graphics3D graphics3D) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0, 0, 0, 1f);

    modelBatch.begin(camera);
    modelBatch.render(instance, environment);
    modelBatch.render(rayA, environment);
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

}
