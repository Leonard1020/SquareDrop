package leonardj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FallingBox extends Box {

    private float fallSpeed;

    public FallingBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        setMaxTime(0);

        fallSpeed = 100;
    }

    public FallingBox(float fallSpeed, float x, float y, float width, float height) {
        super(x, y, width, height);
        setMaxTime(0);

        this.fallSpeed = fallSpeed;
    }

    public void update(float dt){
        y -= fallSpeed * dt;
    }

    public void render(SpriteBatch sb){
        sb.setColor(color[0], color[1], color[2], color[3]);
        sb.draw(texture, x - totalWidth / 2, y - totalHeight / 2, totalWidth, totalHeight);
    }
}
