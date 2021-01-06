package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class JailFieldTest {

    JailField jail;
    JailField visiting;
    Player p1;

    @BeforeEach
    void setUp() {
        jail = new JailField("Fængsel", 1, true);
        visiting = new JailField("På besøg", 0, false);

        p1 = new Player("P1");
    }

    @Test
    void landOnField() {
        assertEquals(visiting.landOnField(p1), "P1 besøgte fængslet");
        assertFalse(p1.isInPrison());

        assertEquals(jail.landOnField(p1), "P1 landede på fængselsfeltet og blev sendt til fængslet");
        assertTrue(p1.isInPrison());
    }
}