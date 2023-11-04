package pt.upskill.projeto1.creatures;

import pt.upskill.projeto1.creatures.Rivals.Rivals;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.GoodMeat;
import pt.upskill.projeto1.objects.Hammer;
import pt.upskill.projeto1.objects.Room;
import pt.upskill.projeto1.objects.Sword;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import java.awt.event.KeyEvent;
import java.util.List;

public class Hero extends Creatures {

    private Room room;
    private static Engine engine;


    public Hero(Position position, List<ImageTile> tiles, int healthPoints, int attack, Room room) {
        super(position, tiles, healthPoints, attack);
        this.room = room;
    }

    @Override
    public String getName() {
        return "Hero";
    }


    public static void setEngine(Engine engine) {
        Hero.engine = engine;
    }

    /** Gere o movimento do Hero de acordo com as teclas pressionadas.
     * @param keyPressed de acordo com a tecla pressionada. */
    public void moveHero(int keyPressed) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        try {

            if (keyPressed == KeyEvent.VK_DOWN) {
                Position newPosition = getPosition().plus(Direction.DOWN.asVector());
                if(checkCollision(newPosition)) {
                    setPosition(newPosition);
                    gui.setStatus("Hero is moving down!");
                }
                for (ImageTile tile : room.getTiles()) {
                    if (tile instanceof Rivals && tile.getPosition().equals(newPosition)) {
                        attackRivals((Rivals) tile);
                        gui.setStatus(((Rivals) tile).getName() + " has now " +((Rivals) tile).getHealthPoints() + " Health Points");
                        deathOfRivals((Rivals) tile, room.getTiles());
                        break;
                    }
                }
            }
            else if (keyPressed == KeyEvent.VK_UP) {
                Position newPosition = getPosition().plus(Direction.UP.asVector());
                if(checkCollision(newPosition)) {
                    setPosition(newPosition);
                    gui.setStatus("Hero is moving up!");
                }
                for (ImageTile tile : room.getTiles()) {
                    if (tile instanceof Rivals && tile.getPosition().equals(newPosition)) {
                        attackRivals((Rivals) tile);
                        gui.setStatus(((Rivals) tile).getName() + " has now " +((Rivals) tile).getHealthPoints() + " Health Points");
                        deathOfRivals((Rivals) tile, room.getTiles());
                        break;
                    }
                }
            }
            else if (keyPressed == KeyEvent.VK_LEFT) {
                Position newPosition = getPosition().plus(Direction.LEFT.asVector());
                if(checkCollision(newPosition)) {
                    setPosition(newPosition);
                    gui.setStatus("Hero is moving left!");
                }
                for (ImageTile tile : room.getTiles()) {
                    if (tile instanceof Rivals && tile.getPosition().equals(newPosition)) {
                        attackRivals((Rivals) tile);
                        gui.setStatus(((Rivals) tile).getName() + " has now " +((Rivals) tile).getHealthPoints() + " Health Points");
                        deathOfRivals((Rivals) tile, room.getTiles());
                        break;
                    }
                }
            }
            else if (keyPressed == KeyEvent.VK_RIGHT) {
                Position newPosition = getPosition().plus(Direction.RIGHT.asVector());
                if (checkCollision(newPosition)) {
                    setPosition(newPosition);
                    gui.setStatus("Hero is moving right!"); // Sem colisão
                }
                for (ImageTile tile : room.getTiles()) {
                    if (tile instanceof Rivals && tile.getPosition().equals(newPosition)) {
                        attackRivals((Rivals) tile);
                        gui.setStatus(((Rivals) tile).getName() + " has now " + ((Rivals) tile).getHealthPoints() + " Health Points");
                        deathOfRivals((Rivals) tile, room.getTiles());
                        break;
                    }
                }
            } else {
                throw new IllegalArgumentException("Attention: " + keyPressed);
            }
        } catch (Exception e) {
            gui.setStatus("Invalid Key!" + e.getMessage());
        }
    }

    /** Ataca o Rivals e reduz-lhe a vida de acordo com o ataque do Hero
     *  Update da vida dos Rivals através do método 'setHealthPoints'.
     * @param enemy >> Rivals a ser atacado. */
    public void attackRivals(Creatures enemy) {
        int newHealthPoints = enemy.getHealthPoints() - getAttack();
        enemy.setHealthPoints(newHealthPoints);
    }

    /** Verifica a morte do Rivals.
     * Se a vida do Rivals for menor que 0, remove o inimigo da lista, remove-lhe a imagem
     * e faz um update da mensagem do GUI. */
    public void deathOfRivals(Creatures enemy, List<ImageTile> tiles) {

        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        tiles = room.getTiles();
        if (enemy.getHealthPoints() <= 0) {
            tiles.remove(enemy);
            gui.removeImage(enemy);
            gui.setStatus(enemy.getName() + " is dead");
        }
    }

    /** Agarrar itens do room (Neste caso GoodMeat e Hammer)
     *  GoodMeat aumenta a vida do Hero e o Hammer a força do seu ataque. */
    public void grabItem(Hero hero, List<ImageTile> tiles) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        int heroHealth = hero.getHealthPoints();
        for (ImageTile item : tiles) {
            if (item instanceof GoodMeat && hero.getPosition().equals(item.getPosition())) {
                gui.removeImage(item);
                tiles.remove(item);
                heroHealth = heroHealth + 10;
                hero.setHealthPoints(heroHealth);
                engine.setStatusBar(heroHealth,false,false);

                gui.setStatus("HERO has now " + heroHealth + " health points");
                break;
            }
            if (item instanceof Hammer && hero.getPosition().equals(item.getPosition())) {
                int heroAttack = hero.getAttack();
                gui.removeImage(item);
                tiles.remove(item);
                gui.addStatusImage(new Hammer(new Position(9,0)));
                gui.setStatus("HERO has now " + item.getName() + "!");
                engine.setHasHammer(true);
                heroAttack  = heroAttack + 10;
                hero.setAttack(heroAttack);
                gui.setStatus("HERO has now " + item.getName() + "!");
                break;
            }
            if (item instanceof Sword && hero.getPosition().equals(item.getPosition())) {
                int heroAttack = hero.getAttack();
                gui.removeImage(item);
                tiles.remove(item);
                gui.addStatusImage(new Sword(new Position(7,0)));
                gui.setStatus("HERO has now " + item.getName() + "!");
                engine.setHasSword(true);
                heroAttack  = heroAttack + 5;
                hero.setAttack(heroAttack);
                gui.setStatus("HERO has now " + item.getName() + "!");
                break;
            }
        }
    }
}
