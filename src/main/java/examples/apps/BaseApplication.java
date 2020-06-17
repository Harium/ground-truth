package examples.apps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.propan.core.context.ApplicationGL;

public abstract class BaseApplication extends ApplicationGL {

    public BaseApplication(int w, int h) {
        super(w, h);
    }

    public void takeScreenShot() {
        String id = "-" + System.currentTimeMillis();
        String filename = getFilename() + id + ".png";
        FileHandle file = Gdx.files.absolute("screenshots/" + filename);
        if (file.exists()) {
            return;
        }

        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(),
            Gdx.graphics.getBackBufferHeight(), true);

        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(),
            Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);

        PixmapIO.writePNG(file, pixmap);
        pixmap.dispose();

        System.out.println("Photo taken: " + filename);
    }

    private String getClassName() {
        String name = getClass().getSimpleName();
        StringBuilder snakeCase = new StringBuilder();

        snakeCase.append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isUpperCase(name.charAt(i))) {
                snakeCase.append(name.charAt(i));
            } else {
                snakeCase.append("_");
                snakeCase.append(Character.toLowerCase(name.charAt(i)));
            }
        }

        return snakeCase.toString();
    }

    protected String getFilename() {
        return getClassName();
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyUp(KeyEvent.VK_P) || "p".charAt(0) == event.getChar()) {
            takeScreenShot();
        }
    }
}
