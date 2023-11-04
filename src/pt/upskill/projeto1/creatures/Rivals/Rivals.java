package pt.upskill.projeto1.creatures.Rivals;

import pt.upskill.projeto1.creatures.Creatures;
import pt.upskill.projeto1.creatures.Hero;
import pt.upskill.projeto1.game.Engine;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Room;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

import java.util.List;
import java.util.Random;

public abstract class Rivals extends Creatures {

    private final Random random = new Random();
    private Hero hero;
    private static Engine engine;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private Room room;

    public Rivals(Position position, List<ImageTile> tiles, int healthPoints, int attack, Room room) {
        super(position, tiles, healthPoints, attack);
        this.room = room;
    }
    @Override
    public String getName() {
        return null;
    }

    public static void setEngine(Engine engine) {
        Rivals.engine = engine;
    }

    /** Retorna direções aleatórias dos Rivals
     * @return uma direção aleatória */
    public Vector2D generateRandomRivalDirection() {
        // Criar um array para guardar as possíveis direções dos Rivals como vetores
        Vector2D[] vectorArray = new Vector2D[4];

        // Representar as direçães como vetores
        vectorArray[0] = Direction.DOWN.asVector();
        vectorArray[1] = Direction.UP.asVector();
        vectorArray[2] = Direction.LEFT.asVector();
        vectorArray[3] = Direction.RIGHT.asVector();

        // Devolve uma direção aleatória da array
        return vectorArray[random.nextInt(vectorArray.length)];
    }

    /** Devolve movimentos aleatórios para o Thieve
     *  usando as direçães diagonais
     * @return uma direção aleatória */
    public Vector2D generateRandomThieveDirection() {
        // Criar um array para guardar as possíveis direções do Thieve como vetores
        Vector2D[] vectorArrayThief = new Vector2D[4];
        vectorArrayThief[0] = new Vector2D(1, 1); // Down-Right
        vectorArrayThief[1] = new Vector2D(-1, 1); // Up-Right
        vectorArrayThief[2] = new Vector2D(-1, -1); // Up-Left
        vectorArrayThief[3] = new Vector2D(1, -1); // Down-Left

        return vectorArrayThief[random.nextInt(vectorArrayThief.length)];
    }

    /** Define o movimento dos Rivals no jogo.
     *  O comportamento do movimento depende da distância entre Hero e Rivals.
     *  Se a distância for >= 3, os Rivals movimentam-se aleatoriamente.
     *  Se a distância for <3, os Rivals movem-se na direção do Herói, evitando colisões.
     *  Rivals atacam o Hero se estiverem numa posição adjacente. */
    public void moveRivals() {
        this.hero = room.getHero();
        Position rivalPosition = getPosition();

        if (hero.getPosition().calculateEuclideanDistance(rivalPosition) >= 3) {
            // Gera uma nova posição aleatória
            Position newPosition = getPosition().plus(generateRandomRivalDirection());
            // Verifica se a nova posição é válida e não colide com os outros tiles
            if (checkCollision(newPosition)) {
                setPosition(newPosition);
                attackRivalsOnHero();
            }
        } else {
            // Define as possíveis direções para os Rivals
            Vector2D[] possibleMoves = {Direction.UP.asVector(), Direction.DOWN.asVector(),
                    Direction.LEFT.asVector(), Direction.RIGHT.asVector()};

            //Inicializa a melhor posição como a posição atual do Rival
            // e define a melhor distância (o maior valor possível)
            // para ser utilizado no loop.
            Position bestPosition = rivalPosition;
            double bestDistance = Double.MAX_VALUE;

            // Itera sobre as possíveis direções para os Rivals e encontra a melhor posição
            // que minimiza a distância
            for (Vector2D move : possibleMoves) {
                Position newPosition = rivalPosition.plus(move);
                double distance = hero.getPosition().calculateEuclideanDistance(newPosition);
                if (distance < bestDistance && checkCollision(newPosition)) {
                    bestPosition = newPosition;
                    bestDistance = distance;
                }
            }
            // Mover o Rival para a melhor posição encontrada
            setPosition(bestPosition);
            attackRivalsOnHero();
        }
    }

    /** Igual ao método anterior, mas apenas para o Thieve. */
    public void moveThief () {
        this.hero = room.getHero();
        Position rivalPosition = getPosition();

        if (hero.getPosition().calculateEuclideanDistance(rivalPosition) >= 3) {
            Position newPosition = getPosition().plus(generateRandomThieveDirection());
            if (checkCollision(newPosition)) {
                setPosition(newPosition);
                attackRivalsOnHero();
            }
        } else {
            Vector2D[] possibleMoves = {Direction.UP.asVector(), Direction.DOWN.asVector(), Direction.LEFT.asVector(),
                    Direction.RIGHT.asVector()};

            Position bestPosition = rivalPosition;
            double bestDistance = Double.MAX_VALUE;

            for (Vector2D move : possibleMoves) {
                Position newPosition = rivalPosition.plus(move);
                double distance = hero.getPosition().calculateEuclideanDistance(newPosition);
                if (distance < bestDistance && checkCollision(newPosition)) {
                    bestPosition = newPosition;
                    bestDistance = distance;
                }
            }
            setPosition(bestPosition);
            attackRivalsOnHero();
        }
    }

    /** Método responsável em gerir o ataque dos Rivals ao Hero.
     * Calcula a distância entre o Rival e o Hero e, se for igual a 1,
     * reduz os Health Points do Hero na proporção do ataque dos Rivals.
     * Update da status bar do Hero e verifica se morreu. */
    public void attackRivalsOnHero() {
        // Info do Hero e do Rival
        this.hero = room.getHero();
        int heroLife = hero.getHealthPoints();
        Position heroPosition = hero.getPosition();
        Position rivalPosition = this.getPosition();

        // Verifica se Hero e Rivals estão em posições adjacentes.
        if(heroPosition.calculateEuclideanDistance(rivalPosition) == 1 ) {

            gui.setStatus("Hero HealthPoints: " + heroLife);
            // Reduzir os HealthPoints do Hero na proporção do ataque dos Rivals.
            heroLife -= this.getAttack();
            hero.setHealthPoints(heroLife);
            gui.setStatus("Hero HealthPoints: " + heroLife);
        }
            // Update da status bar com os HealthPoints do Hero e Rivals.
            engine.setStatusBar(heroLife, engine.isHasHammer(), engine.isHasSword());
            // Verifica se morreu.
            handleHeroDeath(this.hero, room.getTiles());
    }

    /** Lidar com a morte do Heroi e com o fim do jogo. */
    public void handleHeroDeath(Hero hero, List<ImageTile> tiles) {
        // update dos tiles com os tiles do room
        tiles = room.getTiles();
        // getHero do room
        hero = room.getHero();
        // Hero morre se HealthPoints <= 0
        if (hero.getHealthPoints() <= 0) {
           // Mensagem de Game Over e fechar a janela do GUI.
            gui.showMessage("GAME OVER", "Hero is Dead! Press OK to exit");
            gui.dispose();
        }
    }
}
