package pt.upskill.projeto1.statusbar;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Black implements ImageTile {

    private String name;
    private Position position;

    public Black(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Black";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
