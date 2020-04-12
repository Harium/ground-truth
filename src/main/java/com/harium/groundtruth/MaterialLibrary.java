package com.harium.groundtruth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

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

}
