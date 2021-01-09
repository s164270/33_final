package game;

import gui_main.GUI;
import player.Player;

public class Auction {

    private GUI gui;
    private Player[] players;

    public Auction(GUI gui, Player[] players)
    {
        this.gui = gui;
        this.players = players;
    }


    private int nextBidderIndex(int currentIndex, Object[] bidders)
    {
        currentIndex++;
        if(currentIndex >= bidders.length)
        {
            currentIndex = 0;
        }
        return currentIndex;
    }

    private int getBid(Player bidder, int currentMaxBid)
    {
        int bid = 0;
        boolean validBid = false;
        while(!validBid)
        {
            bid = gui.getUserInteger("Byd 0 for at droppe ud. Nuværende bud er på " + currentMaxBid);

            if(bid == 0)
            {
                validBid = true;
            }
            else if(bid > bidder.getPoints())
            {
                gui.showMessage("Det har du ikke råd til");
            }
            else if(bid <= currentMaxBid)
            {
                gui.showMessage("Dit bud er for lavt");
            }
            else
            {
                validBid = true;
            }
        }
        return bid;
    }

    public void startAuction(PropertyField property)
    {
        Player[] remainingParticipants = players.clone();
        int currentBid = 0;
        int bidderIndex = 0;
        int newBid = 0;

        boolean sold = false;
        while (remainingParticipants.length > 1)
        {
            String btnAnswer = gui.getUserButtonPressed("Højeste bud er på " + currentBid + "kr. Det er " + remainingParticipants[bidderIndex].getName() + " til at byde.", "Byd", "Drop ud");
            if(btnAnswer.equalsIgnoreCase("Byd")){
                //update currentBid
                newBid = getBid(remainingParticipants[bidderIndex], currentBid);
            }
            else
            {
                newBid = 0;
            }

            if(newBid > currentBid)
            {
                currentBid = newBid;
            }
            else
            {
                //remove player from the remaining participants
                Player[] tempArray = new Player[remainingParticipants.length - 1];
                int j = 0;
                for(int i = 0; i < remainingParticipants.length; i++)
                {
                    if(i != bidderIndex)
                    {
                        tempArray[j] = remainingParticipants[i];
                        j++;
                    }
                }
                remainingParticipants = tempArray;
                bidderIndex--;
            }
            //next bidder
            System.out.println("Index: " + bidderIndex + "   |   arraylength: " + remainingParticipants.length);
            bidderIndex = nextBidderIndex(bidderIndex, remainingParticipants);
            System.out.println("Index after nextBidderIndex: " + bidderIndex);
            System.out.println("------------------------");
        }
        gui.showMessage(remainingParticipants[0].getName() + " købte grunden for " + currentBid);
        //property.buyProperty(remainingParticipants[0], currentBid);
    }

}
