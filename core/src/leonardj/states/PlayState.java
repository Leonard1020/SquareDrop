package leonardj.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import leonardj.ColorDrop;
import leonardj.entities.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.prefs.Preferences;

public class PlayState extends State{

    public static final int BOX_SIZE = 100;
    public static final int COLOR_BOX_SIZE = 75;
    public static final int OUTLINE_OFFSET = 8;
    public static final float FADE_IN = -0.15f;

    public static final int CENTER_WIDTH = ColorDrop.WIDTH / 2;
    public static final int Y_HEIGHT = 250;

    public static final float[] whiteColor = {1, 1, 1, 1};
    public static final float[] redColor = {1, 0, 0, 1};
    public static final float[] blueColor = {0, 0, 1, 1};
    public static final float[] yellowColor = {1, 1, 0, 1};
    public static final float[] purpleColor = {1, 0, 1, 1};
    public static final float[] greenColor = {0, 1, 0, 1};
    public static final float[] orangeColor = {1, 0.5f, 0, 1};
    public static final float[] greyLine = {0.6f, 0.6f, 0.6f, 1};

    public static final int MAX_FINGERS = 2;

    public Score score;

    public TextImage scoreImage;

    private Array<FallingBox> fallingBoxes;
    private Array<float[]> fallingColors;
    private boolean sendBox = false;
    private float fallTimer = 2;
    private float fallSpeed = 100;

    private TextureRegion boxOutline;

    private Box box;

    private Box red;

    private Box blue;

    private Box yellow;

    private Glow glow;

    private TextImage back;

    public PlayState(GSM gsm) {
        super(gsm);

        score = new Score();
        scoreImage = new TextImage(Integer.toString(score.getScore()), ColorDrop.WIDTH - 75, ColorDrop.HEIGHT - 25);
        scoreImage.setSize(30);
        score.setScore(0);

        back = new TextImage("back", 75, ColorDrop.HEIGHT - 25);
        back.setSize(30);

        boxOutline = ColorDrop.res.getAtlas("pack").findRegion("shape");

        fallingBoxes = new Array<FallingBox>();
        fallingColors = new Array<float[]>();
        fallingColors.add(whiteColor);
        fallingColors.add(redColor);
        fallingColors.add(blueColor);
        fallingColors.add(yellowColor);
        fallingColors.add(purpleColor);
        fallingColors.add(greenColor);
        fallingColors.add(orangeColor);

        createBoxes();
    }

    public void createBoxes(){
        box = new Box(CENTER_WIDTH, Y_HEIGHT, BOX_SIZE, BOX_SIZE);
        box.setTimer(FADE_IN);

        red = new Box(CENTER_WIDTH - 150, Y_HEIGHT - 150, COLOR_BOX_SIZE, COLOR_BOX_SIZE);
        red.setTimer(FADE_IN);
        red.setColor(redColor[0], redColor[1], redColor[2], redColor[3]);

        blue = new Box(CENTER_WIDTH, Y_HEIGHT - 150, COLOR_BOX_SIZE, COLOR_BOX_SIZE);
        blue.setTimer(FADE_IN);
        blue.setColor(blueColor[0], blueColor[1], blueColor[2], blueColor[3]);

        yellow = new Box(CENTER_WIDTH + 150, Y_HEIGHT - 150, COLOR_BOX_SIZE, COLOR_BOX_SIZE);
        yellow.setTimer(FADE_IN);
        yellow.setColor(yellowColor[0], yellowColor[1], yellowColor[2], yellowColor[3]);
    }

    public void handleColorChange(boolean red, boolean blue, boolean yellow){
        if (!red && !blue && !yellow){ //white
            box.setColor(whiteColor[0], whiteColor[1], whiteColor[2], whiteColor[3]);
        }
        if (red && !blue && !yellow){ //red
            box.setColor(redColor[0], redColor[1], redColor[2], redColor[3]);
        }
        if (!red && blue && !yellow){ //blue
            box.setColor(blueColor[0], blueColor[1], blueColor[2], blueColor[3]);
        }
        if (!red && !blue && yellow){ //yellow
            box.setColor(yellowColor[0], yellowColor[1], yellowColor[2], yellowColor[3]);
        }
        if (red && blue && !yellow){ //purple (red and blue)
            box.setColor(purpleColor[0], purpleColor[1], purpleColor[2], purpleColor[3]);
        }
        if (!red && blue && yellow){ //green (blue and yellow)
            box.setColor(greenColor[0], greenColor[1], greenColor[2], greenColor[3]);
        }
        if (red && !blue && yellow){ //orange (red and yellow)
            box.setColor(orangeColor[0], orangeColor[1], orangeColor[2], orangeColor[3]);
        }
    }

    public void handleCatch(){
        if (fallingBoxes.size > 0){
            FallingBox fallingBox = fallingBoxes.get(0);
            if (fallingBox.getY() <= box.getY()){
                glow = new Glow(fallingBox.getX(), fallingBox.getY(), BOX_SIZE, BOX_SIZE);
                float[] glowColor = fallingBox.getColor();
                glow.setColor(glowColor[0], glowColor[1], glowColor[2]);
                float[] fallingBoxColorSet = fallingBox.getColor();
                float[] boxColorSet = box.getColor();
                if (fallingBoxColorSet[0] == boxColorSet[0] && fallingBoxColorSet[1] == boxColorSet[1]
                        && fallingBoxColorSet[2] == boxColorSet[2] && fallingBoxColorSet[3] == boxColorSet[3]){
                    fallingBoxes.removeIndex(0);
                    score.addScore(5);
                } else {
                    fallingBoxes.clear();
                    gsm.set(new TransitionState(gsm, this, new ScoreState(gsm, score.getScore(), ScoreState.ScoreType.GAME_SCORE), TransitionState.Type.BLACK_FADE));
                }
                if (score.getScore() > gsm.getHighScore()){
                    gsm.setHighScore(score.getScore());
                    com.badlogic.gdx.Preferences prefs = Gdx.app.getPreferences("My Preferences");
                    prefs.putInteger("highscore", score.getScore());
                    prefs.flush();
                }
            }
        }
    }

    @Override
    public void handleInput() {
        boolean redPressed = false;
        boolean bluePressed = false;
        boolean yellowPressed = false;
        for (int i = 0; i < MAX_FINGERS; i++){
            if (Gdx.input.isTouched(i)){
                mouse.x = Gdx.input.getX(i);
                mouse.y = Gdx.input.getY(i);
                camera.unproject(mouse);
                if (red.contain(mouse.x, mouse.y)){
                    redPressed = true;
                }
                if (blue.contain(mouse.x, mouse.y)){
                    bluePressed = true;
                }
                if (yellow.contain(mouse.x, mouse.y)){
                    yellowPressed = true;
                }
                if (back.contain(mouse.x, mouse.y)){
                    gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.Type.EXPAND));
                }
            }
        }
        handleColorChange(redPressed, bluePressed, yellowPressed);
    }

    @Override
    public void update(float dt) {
        handleInput();

        scoreImage.setText("" + score.getScore());

        box.update(dt);

        red.update(dt);
        blue.update(dt);
        yellow.update(dt);

        fallTimer -= dt;
        if (fallTimer <= 0){
            sendBox = true;
            fallTimer = 4.5f - (0.01f * score.getScore()) + (float)Math.random();
            if (4.5f - (0.01f * score.getScore()) <= 0){
                fallTimer = (float)Math.random() * 0.75f + 0.5f;
            }
        }
        if (sendBox){
            if (score.getScore() > 10 && score.getScore() % 50 == 0){
                fallSpeed += 25;
            }
            FallingBox boxToFall = new FallingBox(fallSpeed, CENTER_WIDTH, ColorDrop.HEIGHT + 50, BOX_SIZE, BOX_SIZE);
            int i = MathUtils.random(fallingColors.size - 1);
            if (score.getScore() <= 10){
                i = MathUtils.random(3);
            }
            float[] set = fallingColors.get(i);
            boxToFall.setColor(set[0], set[1], set[2], set[3]);
            fallingBoxes.add(boxToFall);
            sendBox = false;
        }

        for (FallingBox fb: fallingBoxes){
            fb.update(dt);
        }

        if (glow != null){
            glow.update(dt);
            if (glow.shouldRemove()){
                glow = null;
            }
        }

        handleCatch();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.setColor(1, 1, 1, 1);
        back.render(sb);
        scoreImage.render(sb);

        sb.setColor(greyLine[0], greyLine[1], greyLine[2], greyLine[3]);

        sb.draw(boxOutline, CENTER_WIDTH - 5, 250, 10, 600);
        int outlineSize = BOX_SIZE + OUTLINE_OFFSET;
        int center = outlineSize / 2;
        sb.draw(boxOutline, CENTER_WIDTH - center, Y_HEIGHT - center, outlineSize, outlineSize);
        outlineSize = COLOR_BOX_SIZE + OUTLINE_OFFSET;
        center = outlineSize / 2;
        sb.draw(boxOutline, CENTER_WIDTH - 151 - center, Y_HEIGHT - 150 - center, outlineSize, outlineSize);
        sb.draw(boxOutline, CENTER_WIDTH - center - 1, Y_HEIGHT - 150 - center, outlineSize, outlineSize);
        sb.draw(boxOutline, CENTER_WIDTH + 149 - center, Y_HEIGHT - 150 - center, outlineSize, outlineSize);

        box.render(sb);

        red.render(sb);
        blue.render(sb);
        yellow.render(sb);

        for (FallingBox fb: fallingBoxes){
            fb.render(sb);
        }

        if (glow != null){
            glow.render(sb);
        }

        sb.end();
    }
}
