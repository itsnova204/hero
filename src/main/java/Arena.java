import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Arena {
    private int height,width;
    Hero hero;

    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    Position position;

    Arena(int width, int height){
        this.width = width;
        this.height = height;

        position = new Position(10,10);
        hero = new Hero(position);
        walls = createWalls();
        coins = createCoins();
        monsters = createMonsters();
    }

    public boolean canMove(Position position){
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }

        return true;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
        //TODO make coins not overlap with themselves of the player
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
        //TODO make monsters not overlap with themselves of the player
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {

            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    public void draw(TextGraphics graphics){


        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        for (Wall wall : walls)
            wall.draw(graphics);

        for (Coin coin : coins)
            coin.draw(graphics);

        for (Monster monster : monsters)
            monster.draw(graphics);

        hero.draw(graphics);
    }
    private void moveHero(Position position) {
        if (canMove(position)) hero.setPosition(position);
    }

    public void processKey(KeyStroke key){
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            //code to close the game
            Game.gameRunning = false;
            return;
        }
        if (key.getKeyType() == KeyType.EOF){
            Game.gameRunning = false;
            return;
        }

        switch (key.getKeyType()){ //eixo dos Ys é invertido!
            case ArrowUp: moveHero(hero.moveUp()); break;
            case ArrowDown: moveHero(hero.moveDown()); break;
            case ArrowLeft: moveHero(hero.moveLeft()); break;
            case ArrowRight: moveHero(hero.moveRight()); break;
        }
    }

    public void retrieveCoins(){

        List<Coin> retrievedCoins = new ArrayList<>();

        for (Coin coin : coins){
            if (hero.position.equals(coin.position))
                retrievedCoins.add(coin);
        }
        coins.removeAll(retrievedCoins);
    }

    private void setMonsterPos(Position position, Monster monster){
        if (canMove(position))
            monster.setPosition(position);
    }

    public void moveMonsters(){
        Random random = new Random();
        for(Monster monster : monsters){
            switch (random.nextInt(4)){
                case 0:
                    setMonsterPos(monster.moveDown(), monster);
                    break;
                case 1:
                    setMonsterPos(monster.moveUp(), monster);
                    break;
                case 2:
                    setMonsterPos(monster.moveRight(), monster);
                    break;
                case 3:
                    setMonsterPos(monster.moveLeft(), monster);
                    break;
            }
        }
    }

    public boolean verifyMonsterCollisions() {
        for (Monster monster : monsters)
            if (monster.position.equals(hero.position)){
                System.out.println("GAME OVER");
                Game.gameRunning = false;
                return true;
            }

        return false;
    }
}
