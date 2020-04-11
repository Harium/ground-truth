package com.harium.groundtruth;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.harium.propan.core.camera.FPSCameraController;

/**
 * Remove when code is published with propan-gdx
 */
public class FPSCameraControllerV2 extends FPSCameraController {

  public FPSCameraControllerV2(Camera camera) {
    super(camera);
  }

  public void strafe(float amount) {
    Vector3 right = new Vector3(camera.direction).crs(camera.up).nor();
    right.scl(amount);
    camera.position.add(right);
    camera.update();
  }

  public void moveUp(float amount) {
    Vector3 direction = new Vector3(camera.up).nor();
    direction.scl(amount);
    camera.position.add(direction);
    camera.update();
  }

  public void turnPitch(float degrees) {
    Vector3 sideAxis = new Vector3(camera.direction).crs(camera.up).nor();
    camera.direction.rotate(sideAxis, degrees);
    camera.update();
  }

  public void turnYaw(float degrees) {
    camera.direction.rotate(camera.up, degrees);
    camera.update();
  }

  public void moveAhead(float amount) {
    Vector3 backward = new Vector3(camera.direction).nor();
    backward.scl(amount);
    camera.position.add(backward);
    camera.update();
  }
}
