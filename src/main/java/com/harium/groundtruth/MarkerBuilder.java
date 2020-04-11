package com.harium.groundtruth;

import static com.harium.groundtruth.MaterialLibrary.inkMaterial;
import static com.harium.groundtruth.MaterialLibrary.paperMaterial;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;

public class MarkerBuilder {

  private static final float PAPER_SIZE = 0.001f;

  public static Model circleMarker(ModelBuilder modelBuilder, float size) {
    modelBuilder.begin();

    modelBuilder.node();
    MeshPartBuilder mpb = modelBuilder.part("board", GL20.GL_TRIANGLES, 3, paperMaterial());
    BoxShapeBuilder.build(mpb, size * 3, PAPER_SIZE, size * 3);

    mpb = modelBuilder.part("symbol", GL20.GL_TRIANGLES, 3, inkMaterial());
    CylinderShapeBuilder.build(mpb, size, PAPER_SIZE, size, 32);

    return modelBuilder.end();
  }

  public static Model gridMarker(ModelBuilder modelBuilder, int width, int height, float size) {
    modelBuilder.begin();

    float border = 0.1f;
    float spacing = 0.01f;
    float markerW = border*2 + (size + spacing) * width;
    float markerH = border*2 + (size + spacing) * height;

    modelBuilder.node();
    MeshPartBuilder mpb = modelBuilder.part("board", GL20.GL_TRIANGLES, 3, paperMaterial());
    BoxShapeBuilder.build(mpb, markerW, PAPER_SIZE, markerH);

    float px = (-markerW+size) / 2;
    float py = (-markerH+size) / 2;

    for (int j = 0; j < height; j++) {
      for (int i = 0; i < width; i++) {

        float mx = px + border + (size + spacing) * i;
        float my = py+ border + (size + spacing) * j;

        mpb = modelBuilder.part("symbol" + i, GL20.GL_TRIANGLES, 3, inkMaterial());
        BoxShapeBuilder.build(mpb, mx, PAPER_SIZE * 2, my, size, PAPER_SIZE, size);
      }
    }

    return modelBuilder.end();
  }

}
