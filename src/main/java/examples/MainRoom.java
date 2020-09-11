package examples;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;
import examples.apps.room.Room;

public class MainRoom extends Propan {

  public MainRoom() {
    super(1280, 720);
  }

  public static void main(String[] args) {
    MainRoom main = new MainRoom();
    main.setTitle("Ground Truth");
    main.init();
  }

  public ApplicationGL startApplication() {
    return new Room(w, h);
  }
}
