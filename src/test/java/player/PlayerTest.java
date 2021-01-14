package player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static org.hamcrest.CoreMatchers.instanceOf;
//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {

    Player player1;
    Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player("B1", null);
        player2 = new Player("B2", null);
    }

    @Test
    void getName() {
        assertEquals(player1.getName(), "B1");
    }

    @Test
    void getPoints() {
        assertEquals(player1.getPoints(), 20);
    }

    @Test
    void addPoints() {
        player1.addPoints(100);
        assertEquals(player1.getPoints(), 120);
        player1.addPoints(-30);
        assertEquals(player1.getPoints(), 90);
        assertFalse(player1.isBroke());
        player1.addPoints(-100);
        assertEquals(player1.getPoints(), 0);
        assertTrue(player1.isBroke());
    }

    @Test
    void sendPoints() {
        assertEquals(player1.getPoints(), 20);
        assertEquals(player2.getPoints(), 20);
        //send 2 points from player1 to player2
        player1.sendPoints(player2, 2);
        assertEquals(player1.getPoints(), 18);
        assertEquals(player2.getPoints(), 22);
        //sending more than available
        assertFalse(player1.isBroke());
        player1.sendPoints(player2, 100);
        assertEquals(player1.getPoints(), 0);
        assertEquals(player2.getPoints(), 40);
        assertTrue(player1.isBroke());

        //sending negative amount
        assertThrows(IllegalArgumentException.class, () -> {
            player2.sendPoints(player1, -10);
        });
        assertEquals(player1.getPoints(), 0);
        assertEquals(player2.getPoints(), 40);
        //sending to self
    }

    @Test
    void move() {
        assertEquals(player1.getPosition(), 0);
        player1.move(1);
        assertEquals(player1.getPosition(), 1);
        player1.move(20);
        assertEquals(player1.getPosition(), 21);
        player1.move(3);
        assertEquals(player1.getPosition(), 0);
        player1.move(3);
        assertEquals(player1.getPosition(), 3);
        player1.move(24);
        assertEquals(player1.getPosition(), 3);


        assertThrows(IllegalArgumentException.class, () -> {
            player1.move(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player1.move(25);
        });

    }


    // @Test
    /*void getGuiPlayer()
    {
        assertThat(player.getGuiPlayer(), instanceOf(GUI_Player.class));
    }
    */

}