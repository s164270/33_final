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
        assertEquals(player1.getPoints(), 30000);
    }

    @Test
    void addPoints() {
        player1.addPoints(100);
        assertEquals(player1.getPoints(), 30100);
        player1.addPoints(-200);
        assertEquals(player1.getPoints(), 29900);
    }

    @Test
    void move() {
        assertEquals(player1.getPosition(), 0);

        player1.move(1);
        assertEquals(player1.getPosition(), 1);

        player1.move(38);
        assertEquals(player1.getPosition(), 39);

        player1.move(1);
        System.out.println(player1.getPosition());
        assertEquals(player1.getPosition(), 0);

        assertThrows(IllegalArgumentException.class, () -> {
            player1.move(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player1.move(41);
        });

    }

}