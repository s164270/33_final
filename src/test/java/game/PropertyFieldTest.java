package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Shipping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class PropertyFieldTest {

    private PropertyField f1;
    private PropertyField f2;
    private GUI_Field g1;
    private GUI_Field g2;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        f1 = new PropertyField("f1", 2);
        f2 = new PropertyField("f2", 2, f1);
        g1 = new GUI_Shipping();
        g2 = new GUI_Shipping();
        f1.setGuiField(g1);
        f2.setGuiField(g2);
        p1 = new Player("P1");
        p2 = new Player("P2");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getOwner() {
        assertNull(f1.getOwner());
        f1.landOnField(p1);
        assertSame(f1.getOwner(), p1);
    }

    @Test
    void landOnField() {
        int tempCash1;
        int tempCash2;

        //Buying property
        tempCash1 = p1.getPoints();
        f1.landOnField(p1);
        assertEquals(p1.getPoints(), tempCash1 - 2);

        //Paying rent
        tempCash1 = p1.getPoints();
        tempCash2 = p2.getPoints();
        f1.landOnField(p2);
        assertEquals(p1.getPoints(), tempCash1 + 2);
        assertEquals(p2.getPoints(), tempCash2 - 2);

        //Paying double rent
        f2.landOnField(p1);
        tempCash1 = p1.getPoints();
        tempCash2 = p2.getPoints();
        f2.landOnField(p2);
        assertEquals(p1.getPoints(), tempCash1 + 2*2);
        assertEquals(p2.getPoints(), tempCash2 - 2*2);
    }
}