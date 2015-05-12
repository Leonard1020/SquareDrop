package leonardj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import leonardj.handlers.Content;
import leonardj.states.GSM;
import leonardj.states.MenuState;
import java.util.prefs.BackingStoreException;

public class ColorDrop extends ApplicationAdapter {

    public static final String TITLE = "Square Drop";
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static Content res;

    private GSM gsm;
    private SpriteBatch sb;

    @Override
    public void create () {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);

        res = new Content();

        res.loadAtlas("pack.pack", "pack");

        sb = new SpriteBatch();
        gsm = new GSM();

        com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
        int highscore = prefs.getInteger("highscore");

        gsm.setHighScore(highscore);
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
    }
}
