package game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class ParkingFieldTest {

    Player p1;
    ParkingField parking;

    @BeforeEach
    void setUp() {
        parking = new ParkingField();
        p1 = new Player("P1");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void landOnField() {
        assertEquals(parking.landOnField(p1), "P1 parkerede gratis");
    }
}