package leonardj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import leonardj.ColorDrop;

public class Box extends Shape {

    protected TextureRegion texture;
    protected float[] color;

    protected float totalWidth;
    protected float totalHeight;

    protected float timer;
    protected float maxTime = 0.5f;

    public Box(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        totalWidth = width;
        totalHeight = height;

        texture = ColorDrop.res.getAtlas("pack").findRegion("shape");
        color = new float[]{1, 1, 1, 1};
    }

    public void setColor(float r, float g, float b, float a){
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }

    public void setTimer(float t){
        timer = t;
    }

    public void setMaxTime(float t){
        maxTime = t;
    }

    public float[] getColor(){
        return color;
    }

    public void update(float dt){
        if (timer < 0){
            width = height = 0;
        }
        if (width < totalWidth && height < totalHeight){
            timer += dt;
            width = (timer / maxTime) * totalWidth;
            height = (timer / maxTime) * totalHeight;
            if (width < 0){
                width = 0;
            }
            if (height < 0){
                height = 0;
            }
            if (width > totalWidth){
                width = totalWidth;
            }
            if (height > totalHeight){
                height = totalHeight;
            }
        }
    }

    public void render(SpriteBatch sb){
        sb.setColor(color[0], color[1], color[2], color[3]);
        sb.draw(texture, x - width / 2, y - height / 2, width, height);
    }
}
