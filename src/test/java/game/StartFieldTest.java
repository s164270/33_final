package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class StartFieldTest {

    private StartField start;
    private Player p1;

    @BeforeEach
    void setUp() {
        start = new StartField(start.gui);
        p1 = new Player("P1", null);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void visitField() {
        int beforePassing = p1.getPoints();
        start.visitField(p1);
        assertEquals(p1.getPoints(), beforePassing + 2);
    }

    /*@Test
    void landOnField() {
        assertEquals(start.landOnField(p1), "P1 landede på start");
    }*/
}