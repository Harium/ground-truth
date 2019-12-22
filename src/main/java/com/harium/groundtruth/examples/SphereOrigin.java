package com.harium.groundtruth.examples;

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
import com.harium.propan.core.graphics.Graphics3D;

public class SphereOrigin extends BaseApplication {

  private static final float FOV = 60;
  private static final float RADIUS = 0.5f;

  ModelBatch modelBatch;
  Environment environment;

  Model model;
  ModelInstance instance;

  public SphereOrigin(int w, int h) {
    super(w, h);
  }

  public void init(Graphics3D g) {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

    camera = new PerspectiveCamera(FOV, w, h);
    camera.position.set(0, 0, 0);
    camera.lookAt(0, 0, 1);
    camera.near = 0.1f;
    camera.far = 300f;
    camera.update();

    Material whiteMaterial = new Material();
    whiteMaterial.set(ColorAttribute.createDiffuse(Color.WHITE));

    modelBatch = new ModelBatch();
    ModelBuilder modelBuilder = new ModelBuilder();
    Model sphere = modelBuilder.createSphere(RADIUS * 2, RADIUS * 2, RADIUS * 2, 32, 32,
        whiteMaterial,
        VertexAttributes.Usage.Position);
    instance = new ModelInstance(sphere, 0, 0, 3);
  }

  public void display(Graphics3D graphics3D) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    Gdx.gl.glClearColor(0, 0, 0, 1f);

    modelBatch.begin(camera);
    modelBatch.render(instance, environment);
    modelBatch.end();
  }

  public void dispose() {
    model.dispose();
    modelBatch.dispose();
  }

}
