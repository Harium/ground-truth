package com.harium.groundtruth;

import static com.badlogic.gdx.graphics.VertexAttributes.*;
import static com.harium.groundtruth.MaterialLibrary.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;
import com.badlogic.gdx.math.Vector3;

/**
 * More info at: https://github.com/libgdx/libgdx/wiki/ModelBuilder,-MeshBuilder-and-MeshPartBuilder
 */
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

    public Model tileBox(ModelBuilder modelBuilder, float size) {
        return tileBox(modelBuilder, size, paperColor);
    }

    public Model tileBox(ModelBuilder modelBuilder, float size, Color color) {
        modelBuilder.begin();

        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder
                .part("tile", GL20.GL_TRIANGLES, Usage.Position, diffuseMaterial(color));
        BoxShapeBuilder.build(mpb, 1, size, 1);

        return modelBuilder.end();
    }

    public Model tile(ModelBuilder modelBuilder, Color color) {
        modelBuilder.begin();

        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder
                .part("tile", GL20.GL_TRIANGLES, 3, diffuseMaterial(color));

        mpb.setColor(color);
        mpb.setUVRange(0, 1, 1, 0);
        Vector3 a = new Vector3(-0.5f, 0, 0.5f);
        Vector3 b = new Vector3(0.5f, 0, 0.5f);
        Vector3 c = new Vector3(0.5f, 0, -0.5f);
        Vector3 d = new Vector3(-0.5f, 0, -0.5f);
        Vector3 normal = new Vector3(Vector3.Y);
        mpb.rect(a, b, c, d, normal);

        return modelBuilder.end();
    }

    public Model tile(ModelBuilder modelBuilder, Texture diffuse) {
        return tile(modelBuilder, textureMaterial(diffuse));
    }

    public Model tile(ModelBuilder modelBuilder, Texture diffuse, boolean alpha) {
        Material material;
        if (alpha) {
            material = textureAlphaMaterial(diffuse);
        } else {
            material = textureMaterial(diffuse);
        }
        return tile(modelBuilder, material);
    }

    private Model tile(ModelBuilder modelBuilder, Material material) {
        modelBuilder.begin();

        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder
                .part("tile", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates, material);

        Vector3 a = new Vector3(-0.5f, 0, 0.5f);
        Vector3 b = new Vector3(0.5f, 0, 0.5f);
        Vector3 c = new Vector3(0.5f, 0, -0.5f);
        Vector3 d = new Vector3(-0.5f, 0, -0.5f);
        Vector3 normal = new Vector3(Vector3.Y);

        VertexInfo v1 = new VertexInfo().setPos(a).setNor(normal).setCol(null).setUV(0, 0);
        VertexInfo v2 = new VertexInfo().setPos(b).setNor(normal).setCol(null).setUV(1, 0);
        VertexInfo v3 = new VertexInfo().setPos(c).setNor(normal).setCol(null).setUV(1, 1);
        VertexInfo v4 = new VertexInfo().setPos(d).setNor(normal).setCol(null).setUV(0, 1);
        mpb.rect(v1, v2, v3, v4);

        return modelBuilder.end();
    }

    public Model gridMarker(ModelBuilder modelBuilder, int width, int height, float size) {
        return gridMarker(modelBuilder, width, height, size, 0.05f);
    }

    public Model box(ModelBuilder modelBuilder, float size, Color color) {
        modelBuilder.begin();
        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder
                .part("box", GL20.GL_TRIANGLES, 3, diffuseMaterial(color));
        BoxShapeBuilder.build(mpb, size, size, size);

        return modelBuilder.end();
    }

    public Model gridMarker(ModelBuilder modelBuilder, int width, int height, float size, float spacing) {
        modelBuilder.begin();

        float border = 0.1f;
        float markerAreaW = border * 2 + (size * width) + spacing * (width - 1);
        float markerAreaH = border * 2 + (size * height) + spacing * (height - 1);

        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder.part("board", GL20.GL_TRIANGLES, 3, paperMaterial());
        BoxShapeBuilder.build(mpb, markerAreaW, markerSize, markerAreaH);

        float px = (-markerAreaW + size) / 2;
        float py = (-markerAreaH + size) / 2;

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
