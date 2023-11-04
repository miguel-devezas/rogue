package pt.upskill.projeto1.creatures;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.List;

public abstract class Creatures implements ImageTile {

    private Position position;
    private List<ImageTile> tiles;
    private int healthPoints;
    private int attack;

    public Creatures(Position position, List<ImageTile> tiles, int healthPoints, int attack) {
        this.position = position;
        this.tiles = tiles;
        this.healthPoints = healthPoints;
        this.attack = attack;
    }

    @Override
    public abstract String getName();

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    /** Verifica colisães.
     * Itera sobre cada tile e compara a posição dela com a posição dada.
     * Se o tile for encontrada na posição dada e não for uma instância de Floor, GoodMeat, Hammer, Sword ou DoorWay,
     * significa houve colisão e o método retorna false.
     * Se nenhum tile for encontrado na posição dada, o método retorna true para indicar que não há colisão.
     * A tile colisão é armazenada em collision (se houver colisão). */
    public boolean checkCollision(Position pos) {
        ImageTile collision;
        for (ImageTile tile : tiles) {
            if (tile.getPosition().equals(pos) && !(tile instanceof Floor)
                    && !(tile instanceof GoodMeat) && !(tile instanceof Hammer) && !(tile instanceof Sword)&& !(tile instanceof DoorWay)) {
                collision = tile;
                return false;
            }
        }
        collision = null;
        return true;
    }
}
