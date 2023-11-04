package pt.upskill.projeto1.objects;
import pt.upskill.projeto1.creatures.*;
import pt.upskill.projeto1.creatures.Rivals.*;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room implements ImageTile {

    private List<ImageTile> tiles;
    private String fileName;
    private Hero hero;

    public Room(List<ImageTile> tiles, String fileName, Hero hero) {
        this.tiles = tiles;
        this.fileName = fileName;
        this.hero = hero;
    }
    public List<ImageTile> getTiles() {
        return tiles;
    }
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    public Hero getHero() {
        return hero;
    }

    /**
     * Cria um room com os dados de um ficheiro.
     * @return uma lista de ImageTiles que representam o mapa do room. */
    public List<ImageTile> loadAndCreateRoomDataFromFile() {

        //Inicializa uma lista the tiles
        tiles = new ArrayList<>();

        //Cria um Floor para o room (dimensão de 10x10)
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        int j = 0;
        try {
            Scanner scanner = new Scanner(new File(getFileName())); //filename
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                // Ignora as linhas que começam com '#'
                if(row.startsWith("#")){
                    continue;
                }
                // Processa cada character da linha
                for (int i = 0; i < row.length(); i++) {
                    char room = row.charAt(i);
                // Cria o tile correspondente ao character
                    if (room == 'W') {
                        tiles.add(new Wall(new Position(i, j)));
                    }
                    if (room == '0') {
                        tiles.add(new DoorOpen(new Position(i, j)));
                    }
                    if (room == '1') {
                        tiles.add(new DoorClosed(new Position(i, j)));
                    }
                    if (room == '2') {
                        tiles.add(new DoorWay(new Position(i, j)));
                    }
                    if (room == 'k') {
                        tiles.add(new Key(new Position(i, j)));
                    }
                    if (room == 'm') {
                        tiles.add(new GoodMeat(new Position(i, j)));
                    }
                    if (room == 'h') {
                        tiles.add(new Hammer(new Position(i, j)));
                    }
                    if (room == 's') {
                        tiles.add(new Sword(new Position(i, j)));
                    } else if (room == 'S') {
                        tiles.add(new Skeleton(new Position(i, j), tiles, 50, 5, this));
                    } else if (room == 'G') {
                        BadGuy badGuy = new BadGuy(new Position(i, j), tiles, 40, 5, this);
                        tiles.add(badGuy);
                    } else if (room == 'T') {
                        Thief thief = new Thief(new Position(i, j), tiles, 30, 5, this);
                        tiles.add(thief);
                    } else if (room == 'B') {
                        Bat bat = new Bat(new Position(i, j), tiles, 10, 5, this);
                        tiles.add(bat);
                    }else if (room == 'H') {
                        this.hero = new Hero(new Position(i, j), tiles, 60, 5, this);
                        tiles.add(hero);
                    }
                }
                j++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found Mr.Devezas!!");
        }
        return tiles;
        }
}
