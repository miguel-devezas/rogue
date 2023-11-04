package pt.upskill.projeto1.creatures.Rivals;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Room;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public class BadGuy extends Rivals {

    public BadGuy(Position position, List<ImageTile> tiles, int healthPoints, int attack, Room room) {
        super(position, tiles, healthPoints, attack, room);
    }

    @Override
    public String getName() {
        return "BadGuy";
    }
}
