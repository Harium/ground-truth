package com.harium.groundtruth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

public class MaterialLibrary {

  public static Material paperMaterial() {
    return diffuseMaterial(Color.WHITE);
  }

  public static Material inkMaterial() {
    return diffuseMaterial(Color.BLACK);
  }

  public static Material diffuseMaterial(Color color) {
    Material material = new Material();
    material.set(ColorAttribute.createDiffuse(color));
    return material;
  }

  public static Material textureMaterial(Texture texture) {
    Material material = new Material();
    material.set(TextureAttribute.createDiffuse(texture));
    return material;
  }

  public static Material textureAlphaMaterial(Texture texture) {
    Material material = new Material();
    material.set(TextureAttribute.createDiffuse(texture),
                 new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
    return material;
  }

}
