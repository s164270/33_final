package game;

import gui_main.GUI;
import player.Player;

public class Auction {

    private GUI gui;
    private Player[] players;

    public Auction(GUI gui, Player[] players) {
        this.gui = gui;
        this.players = players;
    }


    private int nextBidderIndex(int currentIndex, Player[] bidders) {
        currentIndex++;
        if (currentIndex >= bidders.length) {
            currentIndex = 0;
        }
        return currentIndex;
    }

    private int getBid(Player bidder, int currentMaxBid) {
        int bid = 0;
        boolean validBid = false;
        while (!validBid) {
            bid = gui.getUserInteger("Byd 0 for at droppe ud. Nuværende bud er på " + currentMaxBid);

            if (bid == 0) {
                validBid = true;
            } else if (bid > bidder.getPoints()) {
                gui.showMessage("Det har du ikke råd til");
            } else if (bid <= currentMaxBid) {
                gui.showMessage("Dit bud er for lavt");
            } else {
                validBid = true;
            }
        }
        return bid;
    }

    public void startAuction(Ownable property) {
        int currentBid = 0;
        int bidderIndex = 0;
        int newBid = 0;
        boolean sold = false;

        //Make a copy of the player array
        Player[] allPlayers = players.clone();
        //create an array without broke players
        int tempLength = 0;
        for (int i = 0; i < allPlayers.length; i++) {
            if(!allPlayers[i].isBroke())
                tempLength++;
        }
        Player[] remainingParticipants = new Player[tempLength];
        for (int i = 0, j = 0; i < allPlayers.length; i++) {
            if(!allPlayers[i].isBroke()) {
                remainingParticipants[j] = allPlayers[i];
                j++;
            }
        }

        while (remainingParticipants.length > 1) {

            String btnAnswer = gui.getUserButtonPressed("Højeste bud er på " + currentBid + "kr. Det er " + remainingParticipants[bidderIndex].getName() + " til at byde.", "Byd", "Drop ud");
            if (btnAnswer.equalsIgnoreCase("Byd")) {
                //update currentBid
                newBid = getBid(remainingParticipants[bidderIndex], currentBid);
            } else {
                newBid = 0;
            }

            if (newBid > currentBid) {
                currentBid = newBid;
            } else {
                //remove player from the remaining participants
                Player[] tempArray = new Player[remainingParticipants.length - 1];
                int j = 0;
                for (int i = 0; i < remainingParticipants.length; i++) {
                    if (i != bidderIndex) {
                        tempArray[j] = remainingParticipants[i];
                        j++;
                    }
                }
                remainingParticipants = tempArray;
                bidderIndex--;
            }
            //next bidder
            bidderIndex = nextBidderIndex(bidderIndex, remainingParticipants);
        }
        gui.showMessage(remainingParticipants[0].getName() + " købte grunden for " + currentBid);
        property.buyProperty(remainingParticipants[0], currentBid);
    }
}

