package game;

import gui_fields.GUI_Ownable;
import gui_fields.GUI_Street;
import gui_main.GUI;
import player.Player;

public class PropertyField extends Field{
    private int cost;
    private int rent[];
    private int num_houses;
    private boolean hotelBuild;
    private boolean paired = false;

    private PropertyField neighbor[];
    private Player owner;
    private Auction auction;

    public PropertyField()
    {
        this.cost = 0;
    }

    public PropertyField(String name, int cost, int rent[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
        this.num_houses = 0;
        this.hotelBuild = false;
        this.auction = auction;
    }

    public PropertyField(String name, int cost, int rent[], PropertyField neighbor[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
        this.num_houses = 0;
        this.hotelBuild = false;
        this.auction = auction;
        PropertyField temp_neighbor[] = new PropertyField[neighbor.length+1];
        for (int i = 0; i < neighbor.length; i++)
        {
            temp_neighbor[i]=neighbor[i];
        }
        temp_neighbor[neighbor.length]=this;
        this.setNeighbors(temp_neighbor);
    }

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player player) {
        owner=player;
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public boolean isPaired() {
        return paired;
    }

    public void setNeighbor(PropertyField neighbor[]) {
        this.neighbor=neighbor;
    }
    public void setNeighbors(PropertyField neighbor[]) {
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setNeighbor(neighbor);
        }
    }

    public int getNum_houses() {
        return num_houses;
    }

    public void setNum_houses(int num_houses) {
        this.num_houses = num_houses;
        ((GUI_Street)guiField).setHouses(this.num_houses);
    }

    public boolean isHotelBuild() {
        return hotelBuild;
    }

    public void buyHouse(int numberToBuy)
    {
        // TODO make housePrice part of constructer so it is defined when the property is created
        int housePrice = 2000;
        int housesInGroup = 0;
        int hotelsInGroup = 0;
        int neighborIndex = 0;
        for(int i = 0; i < neighbor.length; i++) {
            housesInGroup += neighbor[i].getNum_houses();
            hotelsInGroup += (neighbor[i].isHotelBuild()) ? 1 : 0;
            if(neighbor[i] == this)
            {
                neighborIndex = i;
            }
        }

        if(numberToBuy == 0) {
            //go back
        }
        else if(numberToBuy < 0 || numberToBuy > 4 * neighbor.length) {
            gui.showMessage("Ugyldigt antal");
        }
        else if(housesInGroup + numberToBuy > 4 * neighbor.length) {
            gui.showMessage("Så mange er der ikke plads til");
        }
        else if(housePrice * numberToBuy > this.getOwner().getPoints()) {
            gui.showMessage("Det har du ikke råd til");
        }
        else {
            this.getOwner().addPoints(-housePrice * numberToBuy);

            // houses are distributed evenly, but the selected PropertyField is prioritized
            int housesLeftToBuy = numberToBuy;
            while (housesLeftToBuy > 0)
            {
                int currentMin = neighbor[0].getNum_houses();
                int minIndex = 0;
                for(int i = 0; i < neighbor.length; i++)
                {
                    if(neighbor[i].getNum_houses() <= currentMin)
                    {
                        currentMin = neighbor[i].getNum_houses();
                        minIndex = i;
                    }
                }

                if(neighbor[neighborIndex].getNum_houses() == currentMin)
                {
                    neighbor[neighborIndex].setNum_houses(currentMin + 1);
                }
                else
                {
                    neighbor[minIndex].setNum_houses(currentMin + 1);
                }
                housesLeftToBuy--;
            }

        }
    }

    public void buyProperty(Player player, int price)
    {
        player.addPoints(-price);
        setOwner(player);
        boolean pair =true;
        for (int i = 0; i < neighbor.length; i++)
        {
            pair = pair && neighbor[i].getOwner() == this.getOwner();
            neighbor[i].setPaired(true);
        }
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setPaired(pair);
        }
    }

    @Override
    public String landOnField(Player player) {
        if(owner == null) //player has to buy the property
        {
            switch (gui.getUserButtonPressed("Vil du købe " + guiField.getTitle() + " for " + cost + "?", "JA", "NEJ"))
            {
                case "JA":
                    buyProperty(player, cost);
                    break;
                case "NEJ":
                    auction.startAuction(this);
                    break;
                default:
                    System.out.println("FEJL");
            }
        }
        else if(owner != player)
        {
            if(hotelBuild) {
                player.sendPoints(owner, rent[5]);
            }
            else if(num_houses > 0) {
                player.sendPoints(owner, rent[num_houses]);
            }
            else if(paired) {
                player.sendPoints(owner, 2*rent[0]);
            }
            else {
                player.sendPoints(owner,rent[0]);
            }
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }
}
