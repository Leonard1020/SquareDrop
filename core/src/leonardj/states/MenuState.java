package leonardj.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import leonardj.ColorDrop;
import leonardj.entities.TextImage;

public class MenuState extends State {

    private TextImage title1;
    private TextImage title2;
    private TextImage play;
    private TextImage highScore1;
    private TextImage highScore2;

    public MenuState(GSM gsm){
        super(gsm);

        title1 = new TextImage("square", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 + 225);
        title2 = new TextImage("drop", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 + 150);
        play = new TextImage("play", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2);
        play.setSize(50);
        highScore1 = new TextImage("high", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 - 100);
        highScore2 = new TextImage("score", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 - 150);
        highScore1.setSize(50);
        highScore2.setSize(50);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            camera.unproject(mouse);
            if (play.contain(mouse.x, mouse.y)){
                State next = new PlayState(gsm);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.EXPAND));
            } else if (highScore1.contain(mouse.x, mouse.y) || highScore2.contain(mouse.x, mouse.y)){
                State next = new ScoreState(gsm, gsm.getHighScore(), ScoreState.ScoreType.HIGH_SCORE);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        title1.render(sb);
        title2.render(sb);
        play.render(sb);
        highScore1.render(sb);
        highScore2.render(sb);
        sb.end();
    }
}
