package game;

import gui_main.GUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class AuctionTest {

    public static void main(String[] args) {
        GUI gui = new GUI();
        Player p0 = new Player("p0");
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        PropertyField field = new PropertyField("prop1", 2000, new int[]{ 550,2600,7800,18000,22000,25000}, null);
        Player[] remPlayers = {p0, p1, p2, p3, p4};
        Auction auctioneer = new Auction(gui, remPlayers);
        auctioneer.startAuction(field);

    }

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