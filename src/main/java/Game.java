import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;



import java.io.IOException;

public class Game {

    private int x = 10;
    private int y = 10;

    Screen screen;
    boolean gameRunning = true;

    Game() throws IOException {

            TerminalSize terminalSize = new TerminalSize(40,20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);

            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
    }

    public void run() throws IOException {

        while (gameRunning) {
            draw();
            processKey(readInput());
        }
    }

    private void draw() throws IOException {
        screen.clear();
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }

    private KeyStroke readInput() throws IOException {
        KeyStroke Key = screen.readInput();
    return Key;
    }
    private void processKey(KeyStroke key){

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            //code to close the game
            gameRunning = false;
            return;
        }
        if (key.getKeyType() == KeyType.EOF){
            gameRunning = false;
            return;
        }

        switch (key.getKeyType()){ //eixo dos Ys é invertido!
            case ArrowUp: y--; break;
            case ArrowDown: y++; break;
            case ArrowRight: x++; break;
            case ArrowLeft: x--; break;
        }

    }
}
