package pt.upskill.projeto1.statusbar;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Red implements ImageTile {

    private Position position;

    public Red(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Red";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
