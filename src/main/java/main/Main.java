package main;
import game.Game;

public class Main {

    public static void main(String[] args) {
        boolean DEBUG=false;
        if (DEBUG)
        {
            Game game = new Game("player1", "player2");
            game.testFunction();
            game.endGame();
        }
        else{
            Game game = new Game();
            while(!game.isGameOver())
            {
                game.turn(game.getCurrentPlayer());
            }
            game.endGame();
        }

    }

}
