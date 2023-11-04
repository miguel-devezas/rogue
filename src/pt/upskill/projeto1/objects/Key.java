package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key implements ImageTile {

    private Position position;

    public Key(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
