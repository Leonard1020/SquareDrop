package leonardj.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import leonardj.ColorDrop;

public class TextImage extends Shape {

    private String text;
    private int size;

    private boolean scaling_i = false;

    public TextImage(String text, float x, float y){
        this.text = text;
        this.x = x;
        this.y = y;

        size = 75;
        setText(text);
    }

    public void setText(String text){
        this.text = text;
        width = size * text.length();
        height = size;
    }

    public void setSize(int size){
        this.size = size;
        setText(text);
    }

    public void render(SpriteBatch sb){
        for (int i = 0; i < text.length(); i++){
            TextureRegion character = findChar(text.charAt(i));
            float dx = x - width / 2 + size * i;
            float dy = y - height / 2;
            if (scaling_i){
                sb.draw(character, dx + 12, dy, size / 2, size);
                scaling_i = false;
            } else {
                sb.draw(character, dx, dy, size, size);
            }
        }
    }

    private TextureRegion findChar(char c){
        if (c == 'a'){
            return ColorDrop.res.getAtlas("pack").findRegion("a");
        } else if (c == 'b') {
            return ColorDrop.res.getAtlas("pack").findRegion("b");
        } else if (c == 'c') {
            return ColorDrop.res.getAtlas("pack").findRegion("c");
        } else if (c == 'd') {
            return ColorDrop.res.getAtlas("pack").findRegion("d");
        } else if (c == 'e') {
            return ColorDrop.res.getAtlas("pack").findRegion("e");
        } else if (c == 'f') {
            return ColorDrop.res.getAtlas("pack").findRegion("f");
        } else if (c == 'g') {
            return ColorDrop.res.getAtlas("pack").findRegion("g");
        } else if (c == 'h') {
            return ColorDrop.res.getAtlas("pack").findRegion("h");
        } else if (c == 'i') {
            scaling_i = true;
            return ColorDrop.res.getAtlas("pack").findRegion("i");
        } else if (c == 'j') {
            return ColorDrop.res.getAtlas("pack").findRegion("j");
        } else if (c == 'k') {
            return ColorDrop.res.getAtlas("pack").findRegion("k");
        } else if (c == 'l') {
            return ColorDrop.res.getAtlas("pack").findRegion("l");
        } else if (c == 'm') {
            return ColorDrop.res.getAtlas("pack").findRegion("m");
        } else if (c == 'n') {
            return ColorDrop.res.getAtlas("pack").findRegion("n");
        } else if (c == 'o') {
            return ColorDrop.res.getAtlas("pack").findRegion("o");
        } else if (c == 'p') {
            return ColorDrop.res.getAtlas("pack").findRegion("p");
        } else if (c == 'q') {
            return ColorDrop.res.getAtlas("pack").findRegion("q");
        } else if (c == 'r') {
            return ColorDrop.res.getAtlas("pack").findRegion("r");
        } else if (c == 's') {
            return ColorDrop.res.getAtlas("pack").findRegion("s");
        } else if (c == 't') {
            return ColorDrop.res.getAtlas("pack").findRegion("t");
        } else if (c == 'u') {
            return ColorDrop.res.getAtlas("pack").findRegion("u");
        } else if (c == 'v') {
            return ColorDrop.res.getAtlas("pack").findRegion("v");
        } else if (c == 'w') {
            return ColorDrop.res.getAtlas("pack").findRegion("w");
        } else if (c == 'x') {
            return ColorDrop.res.getAtlas("pack").findRegion("x");
        } else if (c == 'y') {
            return ColorDrop.res.getAtlas("pack").findRegion("y");
        } else if (c == 'z') {
            return ColorDrop.res.getAtlas("pack").findRegion("z");
        } else if (c == '0') {
            return ColorDrop.res.getAtlas("pack").findRegion("0");
        } else if (c == '1') {
            scaling_i = true;
            return ColorDrop.res.getAtlas("pack").findRegion("1");
        } else if (c == '2') {
            return ColorDrop.res.getAtlas("pack").findRegion("2");
        } else if (c == '3') {
            return ColorDrop.res.getAtlas("pack").findRegion("3");
        } else if (c == '4') {
            return ColorDrop.res.getAtlas("pack").findRegion("4");
        } else if (c == '5') {
            return ColorDrop.res.getAtlas("pack").findRegion("5");
        } else if (c == '6') {
            return ColorDrop.res.getAtlas("pack").findRegion("6");
        } else if (c == '7') {
            return ColorDrop.res.getAtlas("pack").findRegion("7");
        } else if (c == '8') {
            return ColorDrop.res.getAtlas("pack").findRegion("8");
        } else if (c == '9') {
            return ColorDrop.res.getAtlas("pack").findRegion("9");
        } else if (c == ' ') {
            return ColorDrop.res.getAtlas("pack").findRegion("space");
        } else if (c == ':') {
            return ColorDrop.res.getAtlas("pack").findRegion("colon");
        } else {
            return ColorDrop.res.getAtlas("pack").findRegion("space");
        }
    }
}
