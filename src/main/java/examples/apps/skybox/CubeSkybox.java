package examples.apps.skybox;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import static com.harium.groundtruth.MaterialLibrary.textureMaterial;

public class CubeSkybox {

    protected float scale = 10;

    public Texture bottom;
    public Texture top;
    public Texture front;
    public Texture back;
    public Texture left;
    public Texture right;

    public ModelInstance bottomTile;
    public ModelInstance topTile;
    public ModelInstance frontTile;
    public ModelInstance backTile;
    public ModelInstance rightTile;
    public ModelInstance leftTile;

    public CubeSkybox(float scale) {
        this.scale = scale;
    }

    public void init() {
        bottomTile = buildTile(bottom);
        bottomTile.transform.translate(0, -scale * 0.5f, 0);
        bottomTile.transform.scl(scale);

        topTile = buildTile(top);
        topTile.transform.translate(0, scale * 0.5f, 0);
        topTile.transform.scl(scale);
        topTile.transform.rotate(Vector3.Y, 180);
        topTile.transform.rotate(Vector3.Z, 180);

        frontTile = buildTile(front);
        frontTile.transform.translate(0, 0, scale * 0.5f);
        frontTile.transform.scl(scale);
        frontTile.transform.rotate(Vector3.X, -90);

        rightTile = buildTile(right);
        rightTile.transform.translate(-scale * 0.5f, 0, 0);
        rightTile.transform.scl(scale);
        rightTile.transform.rotate(Vector3.X, -90);
        rightTile.transform.rotate(Vector3.Z, -90);

        leftTile = buildTile(left);
        leftTile.transform.translate(scale * 0.5f, 0, 0);
        leftTile.transform.scl(scale);
        leftTile.transform.rotate(Vector3.X, -90);
        leftTile.transform.rotate(Vector3.Z, 90);

        backTile = buildTile(back);
        backTile.transform.translate(0, 0, -scale * 0.5f);
        backTile.transform.scl(scale);
        backTile.transform.rotate(Vector3.X, 90);
        backTile.transform.rotate(Vector3.Y, 180);
    }

    public ModelInstance buildTile(Texture texture) {
        ModelBuilder modelBuilder = new ModelBuilder();

        modelBuilder.begin();

        modelBuilder.node();
        MeshPartBuilder mpb = modelBuilder.part("tile", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
                | VertexAttributes.Usage.TextureCoordinates, textureMaterial(texture));

        Vector3 a = new Vector3(-0.5f, 0, 0.5f);
        Vector3 b = new Vector3(0.5f, 0, 0.5f);
        Vector3 c = new Vector3(0.5f, 0, -0.5f);
        Vector3 d = new Vector3(-0.5f, 0, -0.5f);
        Vector3 normal = new Vector3(Vector3.Y);

        MeshPartBuilder.VertexInfo v1 = new MeshPartBuilder.VertexInfo().setPos(a).setNor(normal).setCol(null).setUV(0, 0);
        MeshPartBuilder.VertexInfo v2 = new MeshPartBuilder.VertexInfo().setPos(b).setNor(normal).setCol(null).setUV(1, 0);
        MeshPartBuilder.VertexInfo v3 = new MeshPartBuilder.VertexInfo().setPos(c).setNor(normal).setCol(null).setUV(1, 1);
        MeshPartBuilder.VertexInfo v4 = new MeshPartBuilder.VertexInfo().setPos(d).setNor(normal).setCol(null).setUV(0, 1);
        mpb.rect(v1, v2, v3, v4);

        ModelInstance marker = new ModelInstance(modelBuilder.end());

        return marker;
    }

}
