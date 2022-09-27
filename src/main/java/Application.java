import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        try{
        Game jogo = new Game();
        jogo.run();
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }
}