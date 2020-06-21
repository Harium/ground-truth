package examples;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;
import examples.apps.markers.CircleMarker;
import examples.apps.markers.MultipleCircleMarker;
import examples.apps.markers.MultipleOnlyZCircleMarker;
import examples.apps.markers.OnlyZCircleMarker;

public class Main extends Propan {

  public Main() {
    super(1280, 720);
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.setTitle("Ground Truth");
    main.init();
  }

  public ApplicationGL startApplication() {
    //return new Rays(w, h);
    // Markers
    //return new CircleMarker(w, h);
    //return new MultipleCircleMarker(w, h);
    //return new OnlyZCircleMarker(w, h);
    return new MultipleOnlyZCircleMarker(w, h);
  }
}
