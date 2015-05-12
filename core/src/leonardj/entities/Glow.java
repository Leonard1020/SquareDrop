package leonardj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Glow extends Box{

    private final float speed = 150;

    private float[] color;

    private boolean remove;

    public Glow(float x, float y, float width, float height){
        super(x, y, width, height);
        this.width = width;
        this.height = height;
        color = new float[]{1, 1, 1, 1};
    }

    public boolean shouldRemove(){
        return remove;
    }

    public void setColor(float red, float blue, float yellow){
        color[0] = red;
        color[1] = blue;
        color[2] = yellow;
    }

    public void update(float dt){
        timer += dt;
        width += dt * speed;
        height += dt * speed;
        if (timer >= maxTime){
            remove = true;
        }
    }

    public void render(SpriteBatch sb){
        color[3] = 1 - timer / maxTime;
        sb.setColor(color[0], color[1], color[2], color[3]);
        sb.draw(texture, x - width / 2, y - height / 2, width, height);
        //sb.setColor(1, 1, 1, 1);
    }
}
