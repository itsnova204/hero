import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Game {

    private int height = 20;
    private int width = 40;
    private Screen screen;
    public static boolean gameRunning = true;
    private Arena arena;
    private Hero hero;
    private Position position;

    Game() throws IOException {

            //Terminal Stuff

            TerminalSize terminalSize = new TerminalSize(width,height);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);

            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary

            //Graphic stuff
            TextGraphics graphics = screen.newTextGraphics();

            //Arena stuff
            arena = new Arena(width,height);

            //Hero Stuff
            position = new Position(10,10);
            hero = new Hero(position);
    }

    public void run() throws IOException {

        while (gameRunning) {
            draw();

            processKey(readInput());
            arena.verifyMonsterCollisions();

            arena.moveMonsters();
            arena.verifyMonsterCollisions();

            arena.retrieveCoins();
        }

        screen.close();
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private KeyStroke readInput() throws IOException {
        KeyStroke Key = screen.readInput();
    return Key;
    }

    private void processKey(KeyStroke key){
        arena.processKey(key);
    }
}
