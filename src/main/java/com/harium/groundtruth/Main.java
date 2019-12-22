package com.harium.groundtruth;

import com.harium.groundtruth.examples.SphereOrigin;
import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;

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
    return new SphereOrigin(w, h);
  }
}
