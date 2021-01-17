package game;

import game.Game;
import player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class GameTest
{
    Game game;
    @BeforeEach
    public void beforeEachTestMethod() {
        game = new Game("player1","player2", "player3");
    }

    @AfterEach
    public void afterEachTestMethod() {
        game.close();
    }

    @Test
    void rollDice()
    {
        game.rollDice();
        assertEquals("","");
    }


    @Test
    void getCurrentPlayer()
    {
        Player p1 = game.getCurrentPlayer();
        game.changePlayer();
        Player p2 = game.getCurrentPlayer();
        game.changePlayer();
        Player p3 = game.getCurrentPlayer();
        game.changePlayer();
        assertTrue(p1== game.getCurrentPlayer());
        game.changePlayer();
        assertTrue(p2== game.getCurrentPlayer());
        game.changePlayer();
        assertTrue(p3== game.getCurrentPlayer());
    }

    @Test
    void changePlayer()
    {
        Player p1 = game.getCurrentPlayer();
        game.changePlayer();
        Player p2 = game.getCurrentPlayer();
        game.changePlayer();
        Player p3 = game.getCurrentPlayer();
        game.changePlayer();
        assertTrue(p1== game.getCurrentPlayer());
        game.changePlayer();
        assertTrue(p2== game.getCurrentPlayer());
        game.changePlayer();
        assertTrue(p3== game.getCurrentPlayer());
    }

}