package examples;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;
import examples.apps.tiles.TileDiffuseFloor;
import examples.apps.tiles.TileTextureFloor;

public class MainTile extends Propan {

  public MainTile() {
    super(1280, 720);
  }

  public static void main(String[] args) {
    MainTile main = new MainTile();
    main.setTitle("Ground Tile");
    main.init();
  }

  public ApplicationGL startApplication() {
    //return new TileDiffuseFloor(w, h);
    return new TileTextureFloor(w, h);
  }
}
