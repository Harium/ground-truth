package com.harium.groundtruth.examples;

import com.harium.propan.core.context.ApplicationGL;
import com.harium.propan.core.graphics.Graphics3D;
import java.util.ArrayList;
import java.util.List;

public class BatchApplication extends ApplicationGL {

  private List<BaseApplication> applications = new ArrayList<>();

  public BatchApplication(int w, int h) {
    super(w, h);
  }

  @Override
  public void init(Graphics3D graphics3D) {
    applications.add(new SphereOrigin(w, h));
  }

  public void display(Graphics3D graphics3D) {
    for (BaseApplication app : applications) {
      app.init(graphics3D);
      app.display(graphics3D);
      app.takeScreenShot();
      app.dispose(graphics3D);
    }
    System.exit(0);
  }
}
