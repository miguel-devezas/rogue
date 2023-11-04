package pt.upskill.projeto1.game;

import pt.upskill.projeto1.creatures.Rivals.Rivals;
import pt.upskill.projeto1.creatures.Hero;
import pt.upskill.projeto1.creatures.Rivals.Thief;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Hammer;
import pt.upskill.projeto1.objects.Room;
import pt.upskill.projeto1.objects.Sword;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.statusbar.Black;
import pt.upskill.projeto1.statusbar.Green;
import pt.upskill.projeto1.statusbar.Red;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private List<ImageTile> tiles;
    private Hero hero;
    private Room room;
    ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    boolean hasHammer = false;
    boolean hasSword = false;

    public boolean isHasHammer() {
        return hasHammer;
    }

    public void setHasHammer(boolean hasHammer) {
        this.hasHammer = hasHammer;
    }

    public boolean isHasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public void init(){

        ArrayList<String> files = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filePath = String.format("rooms/room%d.txt", i);
            files.add(filePath);
        }

        Rivals.setEngine(this);
        Hero.setEngine(this);

        this.room = new Room(this.tiles, files.get(0), hero);
        this.tiles = room.loadAndCreateRoomDataFromFile();

        this.hero = room.getHero();
        setStatusBar(room.getHero().getHealthPoints(),hasHammer, hasSword);

        gui.setEngine(this);
        gui.newImages(tiles);
        gui.go();

        gui.setStatus("Let the game begin! Muahahahah!!");

        while (true){
            gui.update();
        }
    }
    public void notify(int keyPressed){
        room.getHero().moveHero(keyPressed);
        moveRivalsRoom();
        hero.grabItem(hero, tiles);
    }

    /** Define a status bar baseado nos HealthPoints do Hero.
     *  Itera sobre as posiçães da status bar e
     *  adiciona as imagens corretas.
     *  @param healthPoints os pontos de vida atuais do Hero.*/
    public void setStatusBar(int healthPoints, boolean hasHammer, boolean hasSword) {
        for (int i = 0; i < 10; i++) {
            if (i >= 3 && i < 7) {
                if (healthPoints > 45) {
                    gui.addStatusImage(new Green(new Position(i, 0)));
                } else if (healthPoints > 30) {
                    if (i < 6) {
                        gui.addStatusImage(new Green(new Position(i, 0)));
                    } else {
                        gui.addStatusImage(new Red(new Position(i, 0)));
                    }
                } else if (healthPoints > 15) {
                    if (i < 5) {
                        gui.addStatusImage(new Green(new Position(i, 0)));
                    } else {
                        gui.addStatusImage(new Red(new Position(i, 0)));
                    }
                } else if (healthPoints > 0) {
                    if (i < 4) {
                        gui.addStatusImage(new Green(new Position(i, 0)));
                    } else {
                        gui.addStatusImage(new Red(new Position(i, 0)));
                    }
                } else {
                    gui.addStatusImage(new Red(new Position(i, 0)));
                }
            } else if (i == 9) {
                if (hasHammer) {
                    // Add hammer image if hero has a hammer
                    gui.addStatusImage(new Hammer(new Position(i, 0)));
                } else {
                    gui.addStatusImage(new Black(new Position(i, 0)));
                }
            }else if (i == 7) {
                    if (hasSword) {
                        // Add hammer image if hero has a hammer
                        gui.addStatusImage(new Sword(new Position(i, 0)));
                    } else {
                        gui.addStatusImage(new Black(new Position(i, 0)));
                    }
            } else {
                gui.addStatusImage(new Black(new Position(i, 0)));
            }
        }
    }

    /** O moveRivalsRoom é responsável pelo movimento dos inimigos no room.
     * Itera sobre os tiles e chama o método moveRivals() de cada Rivals,
     * exceto o Thief.
     * O Thief é movido separadamente pelo método moveThief(). */
    public void moveRivalsRoom() {
        for (ImageTile tile : tiles) {
            if ((tile instanceof Rivals && !(tile instanceof Thief))) {
                ((Rivals) tile).moveRivals();
            } else if (tile instanceof Thief){
                ((Rivals) tile).moveThief();
            }
        }
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}
