package leonardj.entities;

public class Shape {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public boolean contain(float x, float y){
        return (x > this.x - width / 2) && (x < this.x + width / 2) &&
                (y > this.y - height / 2) && (y < this.y + height / 2);
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
