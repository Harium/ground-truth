package com.harium.propan;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * TODO Delete normalizeUp when PR is merged: https://github.com/libgdx/libgdx/pull/6078
 * and a new libgdx version is published
 */
public class FixedPerspectiveCamera extends PerspectiveCamera {

    static final Vector3 tmpVec = new Vector3();

    public FixedPerspectiveCamera() {
        super();
    }

    public FixedPerspectiveCamera(float fov, int w, int h) {
        super(fov, w, h);
    }

    @Override
    public void normalizeUp() {
        this.tmpVec.set(this.direction).crs(this.up);
        this.up.set(this.tmpVec).crs(this.direction).nor();
    }

    @Override
    public void update(boolean updateFrustum) {
        float aspect = this.viewportWidth / this.viewportHeight;
        this.projection.setToProjection(Math.abs(this.near), Math.abs(this.far), this.fieldOfView, aspect);

        // Avoid add+sub operation
        this.view.setToLookAt(direction, up);
        // Reuse combined matrix
        this.view.mul(this.combined.setToTranslation(-position.x, -position.y, -position.z));

        this.combined.set(this.projection);
        Matrix4.mul(this.combined.val, this.view.val);
        if (updateFrustum) {
            this.invProjectionView.set(this.combined);
            Matrix4.inv(this.invProjectionView.val);
            this.frustum.update(this.invProjectionView);
        }
    }

}
