package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class GoodMeat implements ImageTile {

    private Position position;

    public GoodMeat(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
