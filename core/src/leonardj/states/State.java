package leonardj.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import leonardj.ColorDrop;

public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera camera;
    protected Vector3 mouse;

    protected State(GSM gsm){
        this.gsm = gsm;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ColorDrop.WIDTH, ColorDrop.HEIGHT);
        mouse = new Vector3();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
}
