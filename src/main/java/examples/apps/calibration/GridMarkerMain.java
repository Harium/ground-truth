package examples.apps.calibration;

import com.harium.propan.Propan;
import com.harium.propan.core.context.ApplicationGL;

public class GridMarkerMain extends Propan {

    public GridMarkerMain() {
        super(1280, 720);
    }

    public static void main(String[] args) {
        GridMarkerMain main = new GridMarkerMain();
        main.setTitle("Ground Truth");
        main.init();
    }

    public ApplicationGL startApplication() {
        return new GridMarker(w, h);
    }
}
