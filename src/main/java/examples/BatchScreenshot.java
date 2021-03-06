package examples;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;
import examples.apps.BatchApplication;

public class BatchScreenshot extends Propan {

  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  public BatchScreenshot() {
    super(WIDTH, HEIGHT);
  }

  public static void main(String[] args) {
    BatchScreenshot main = new BatchScreenshot();
    main.setTitle("Ground Truth");
    main.init();
  }

  public ApplicationGL startApplication() {
    return new BatchApplication(w, h);
  }
}
