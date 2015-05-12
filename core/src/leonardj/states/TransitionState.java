package leonardj.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import leonardj.ColorDrop;
import leonardj.entities.Expand;

public class TransitionState extends State {

    public enum Type{
        BLACK_FADE, EXPAND;
    }

    private State prev;
    private State next;
    private Type type;

    private TextureRegion shape;

    //Black Fade
    private float maxTimer;
    private float timer;
    private float alpha;

    //Expand
    private Expand[][] expands;
    private boolean doneExpanding;
    private boolean doneContracting;

    protected TransitionState(GSM gsm, State prev, State next, Type type) {
        super(gsm);
        this.prev = prev;
        this.next = next;
        this.type = type;

        shape = ColorDrop.res.getAtlas("pack").findRegion("shape");

        if (type == Type.BLACK_FADE){
            maxTimer = 2;
        } else if (type == Type.EXPAND){
            int size = 40;
            expands = new Expand[40][24];
            for (int row = 0; row < expands.length; row++){
                for (int col = 0; col < expands[0].length; col++){
                    expands[row][col] = new Expand(col * size + size / 2, row * size + size / 2, size, size);
                    expands[row][col].setTimer((-(expands.length - row) - col) * 0.05f);
                }
            }
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        timer += dt;
        if (type == Type.BLACK_FADE){
            if (timer >= maxTimer){
                gsm.set(next);
            }
        } else if (type == Type.EXPAND){
            if (!doneExpanding){
                boolean ok = true;
                for (int row = 0; row < expands.length; row++){
                    for (int col = 0; col < expands[0].length; col++){
                        expands[row][col].update(dt);
                        if (!expands[row][col].isDoneExpanding()){
                            ok = false;
                        }
                    }
                }
                if (ok && !doneExpanding){
                    doneExpanding = true;
                    for (int row = 0; row < expands.length; row++){
                        for (int col = 0; col < expands[0].length; col++){
                            expands[row][col].setContracting((-(expands.length - row) - col) * 0.05f);
                        }
                    }
                }
            } else {
                boolean ok = true;
                for (int row = 0; row < expands.length; row++){
                    for (int col = 0; col < expands[0].length; col++){
                        expands[row][col].update(dt);
                        if (!expands[row][col].isDoneContracting()){
                            ok = false;
                        }
                    }
                    if (ok && !doneContracting){
                        doneContracting = true;
                        gsm.set(next);
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (type == Type.BLACK_FADE){
            if (timer < maxTimer / 2){
                alpha = timer / (maxTimer / 2);
                prev.render(sb);
            } else {
                alpha = 1 - (timer / (maxTimer / 2));
                next.render(sb);
            }
            if (alpha > 1){
                alpha = 1;
            }
            if (alpha < 0){
                alpha = 0;
            }
            sb.setColor(0, 0, 0, alpha);
            sb.setProjectionMatrix(camera.combined);
            sb.begin();
            sb.draw(shape, 0, 0, ColorDrop.WIDTH, ColorDrop.HEIGHT);
            sb.end();
            sb.setColor(1, 1, 1, 1);
        } else if (type == Type.EXPAND){
            if (!doneExpanding){
                prev.render(sb);
            } else {
                next.render(sb);
            }
            sb.setProjectionMatrix(camera.combined);
            sb.begin();
            for (int row = 0; row < expands.length; row++){
                for (int col = 0; col < expands[0].length; col++){
                    expands[row][col].render(sb);
                }
            }
            sb.end();
        }
    }
}
