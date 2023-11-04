package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer implements ImageTile {

    private Position position;

    public Hammer(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
