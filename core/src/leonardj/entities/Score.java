package leonardj.entities;

public class Score {

    private int score;

    public Score(){
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
