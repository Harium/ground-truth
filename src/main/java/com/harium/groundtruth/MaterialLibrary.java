package com.harium.groundtruth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class MaterialLibrary {

  public static Material paperMaterial() {
    Material material = new Material();
    material.set(ColorAttribute.createDiffuse(Color.WHITE));
    return material;
  }

  public static Material inkMaterial() {
    Material material = new Material();
    material.set(ColorAttribute.createDiffuse(Color.BLACK));
    return material;
  }

}
