package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
import gui_main.GUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    /*
    public static void main(String[] args) {

        GUI_Field[] testGuiFields = new GUI_Field[1];
        testGuiFields[0] = new GUI_Street();
        GUI gui = new GUI(testGuiFields);

        Player p0 = new Player("p0");
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        Player[] remPlayers = {p0, p1, p2, p3, p4};
        Auction auctioneer = new Auction(gui, remPlayers);
        PropertyField field = new PropertyField("prop1", 2000, new int[]{ 550,2600,7800,18000,22000,25000}, gui, auctioneer);
        field.setNeighbor(new PropertyField[]{field});
        field.setGuiField(testGuiFields[0]);
        auctioneer.startAuction(field);

    }
     */

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void startAuction() {
    }
}