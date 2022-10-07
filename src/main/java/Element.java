import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    public Position position;

    Element(Position position){
        this.position = position;
    }

    Position getPosition() {
        return position;
    }

    void setPosition(Position position) {
        this.position = position;
    }

    abstract void draw(TextGraphics graphics);
}
