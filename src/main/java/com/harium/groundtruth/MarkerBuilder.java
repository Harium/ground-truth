package com.harium.groundtruth;

import static com.harium.groundtruth.MaterialLibrary.diffuseMaterial;
import static com.harium.groundtruth.MaterialLibrary.inkMaterial;
import static com.harium.groundtruth.MaterialLibrary.paperMaterial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;

public class MarkerBuilder {

  private float markerSize = 0.001f;
  private Color paperColor = Color.WHITE;
  private Color markerColor = Color.BLACK;

  public Model circleMarker(ModelBuilder modelBuilder, float size) {
    modelBuilder.begin();

    modelBuilder.node();
    MeshPartBuilder mpb = modelBuilder
        .part("board", GL20.GL_TRIANGLES, 3, diffuseMaterial(paperColor));
    BoxShapeBuilder.build(mpb, size * 3, markerSize, size * 3);

    mpb = modelBuilder.part("symbol", GL20.GL_TRIANGLES, 3, diffuseMaterial(markerColor));
    CylinderShapeBuilder.build(mpb, size, markerSize, size, 32);

    return modelBuilder.end();
  }

  public Model gridMarker(ModelBuilder modelBuilder, int width, int height, float size) {
    modelBuilder.begin();

    float border = 0.1f;
    float spacing = 0.01f;
    float markerW = border * 2 + (size + spacing) * width;
    float markerH = border * 2 + (size + spacing) * height;

    modelBuilder.node();
    MeshPartBuilder mpb = modelBuilder.part("board", GL20.GL_TRIANGLES, 3, paperMaterial());
    BoxShapeBuilder.build(mpb, markerW, markerSize, markerH);

    float px = (-markerW + size) / 2;
    float py = (-markerH + size) / 2;

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {

        float mx = px + border + (size + spacing) * i;
        float my = py + border + (size + spacing) * j;

        mpb = modelBuilder.part("symbol" + i, GL20.GL_TRIANGLES, 3, inkMaterial());
        BoxShapeBuilder.build(mpb, mx, markerSize * 2, my, size, markerSize, size);
      }
    }

    return modelBuilder.end();
  }

  public MarkerBuilder color(Color markerColor) {
    this.markerColor = markerColor;
    return this;
  }
}
