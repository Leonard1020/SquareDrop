package leonardj.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import leonardj.ColorDrop;
import leonardj.entities.Score;
import leonardj.entities.TextImage;

import java.util.Stack;

public class GSM {

    private Stack<State> states;

    private Score highscore;

    public GSM(){
        highscore = new Score();
        states = new Stack<State>();
    }

    public void setHighScore(int score){
        highscore.setScore(score);
    }

    public int getHighScore(){
        return highscore.getScore();
    }

    public void push(State s){
        states.push(s);
    }

    public void pop(){
        states.pop();
    }

    public void set(State s){
        states.pop();
        states.push(s);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
