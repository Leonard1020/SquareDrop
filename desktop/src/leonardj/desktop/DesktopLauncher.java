package leonardj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import leonardj.ColorDrop;

public class DesktopLauncher {

	public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = ColorDrop.TITLE;
        cfg.width = ColorDrop.WIDTH; //* ColorDrop.SCALE;
        cfg.height = ColorDrop.HEIGHT; //* ColorDrop.SCALE;
        //cfg.resizable = false;
        cfg.useGL30 = false;
        new LwjglApplication(new ColorDrop(), cfg);
	}
}
