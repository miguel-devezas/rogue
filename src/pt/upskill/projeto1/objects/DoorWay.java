package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorWay implements ImageTile {

    private Position position;

    public DoorWay(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DoorWay";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
