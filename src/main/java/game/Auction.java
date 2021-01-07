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
        if(currentIndex == bidders.length)
        {
            currentIndex = 0;
        }

        return currentIndex;
    }

    public void startAuction(PropertyField property)
    {
        Player[] remainingParticipants = players.clone();
        int currentBid = 1;
        int bidderIndex = 0;


        boolean sold = false;
        while (remainingParticipants.length > 1)
        {
            String btnAnswer = gui.getUserButtonPressed("Det er " + remainingParticipants[bidderIndex].getName() + ". Vil du byde?", "Ja", "Nej");
            if(btnAnswer.equalsIgnoreCase("Ja")){
                //update currentBid
                currentBid = currentBid + 100;
                //next bidder
                bidderIndex = nextBidderIndex(bidderIndex, remainingParticipants);
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
            }
        }
    }

}
