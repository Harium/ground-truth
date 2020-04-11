package com.harium.groundtruth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class GridBuilder {

  public static Model buildGrid(ModelBuilder modelBuilder, float gridX, float gridZ) {
    modelBuilder.begin();
    MeshPartBuilder builder;
    builder = modelBuilder.part("Grid", GL20.GL_LINES, 3, new Material());

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

    return modelBuilder.end();
  }

}
