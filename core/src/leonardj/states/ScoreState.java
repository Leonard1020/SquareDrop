package leonardj.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import leonardj.ColorDrop;
import leonardj.entities.TextImage;

public class ScoreState extends State {

    public enum ScoreType{
        HIGH_SCORE, GAME_SCORE;
    }

    private TextImage image;

    private TextImage title1;
    private TextImage title2;

    private ScoreType scoreType;

    public ScoreState(GSM gsm, int score, ScoreType scoreType) {
        super(gsm);
        if (scoreType == ScoreType.HIGH_SCORE){
            title1 = new TextImage("high", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 + 200);
            title2 = new TextImage("score", ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2 + 135);
            title1.setSize(50);
            title2.setSize(50);
            image = new TextImage(Integer.toString(score), ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2);
        } else {
            image = new TextImage(Integer.toString(score), ColorDrop.WIDTH / 2, ColorDrop.HEIGHT / 2);
        }
        this.scoreType = scoreType;

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            if (scoreType == ScoreType.HIGH_SCORE){
                gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.Type.BLACK_FADE));
            } else {
                gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.Type.EXPAND));
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
        image.render(sb);
        if (scoreType == ScoreType.HIGH_SCORE){
            title1.render(sb);
            title2.render(sb);
        }
        sb.end();
    }
}
