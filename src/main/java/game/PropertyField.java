package game;

import gui_fields.GUI_Ownable;
import gui_fields.GUI_Street;
import gui_main.GUI;
import player.Player;

public class PropertyField extends Field implements Ownable{
    private int cost;
    private int rent[];
    private int num_houses;
    private boolean hotelBuild;
    private boolean paired = false;
    private boolean pawned = false;

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
        player.getOwnedFields().add(this);
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public boolean isReadyForHotel()
    {
        boolean neighborsReady = true;
        for(int i = 0; i < neighbor.length; i++)
        {
            neighborsReady = neighborsReady && (neighbor[i].hotelBuild || neighbor[i].getNum_houses() == 4);
        }
        return neighborsReady && !hotelBuild;
    }

    @Override
    public void pawnOff() {
        if(!pawned && num_houses==0)
        {
            owner.addPoints(cost/2);
            pawned=true;
            System.out.println(pawned);
        }
    }

    @Override
    public void rebuy() {
        owner.addPoints(- (int) ((cost/2)*1.1));
        pawned=false;
    }

    @Override
    public int getPrice()
    {
        return cost;
    }

    @Override
    public boolean isPawned()
    {
        return pawned;
    }

    @Override
    public boolean isPawnable()
    {
        return (num_houses==0) && !pawned;
    }

    public int getNumHouses() {
        return num_houses;
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

    public int getHotelPrice()
    {
        return 2000;
    }

    public void buyHotel()
    {
        getOwner().addPoints(-getHotelPrice());
        num_houses = 0;
        hotelBuild = true;
        ((GUI_Street)guiField).setHotel(true);
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
        else if(numberToBuy < 0 || numberToBuy > 12) {
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
        else if(owner != player && !pawned)
        {
            if(hotelBuild) {
                player.sendPoints(owner, rent[5]);
            }
            else if(num_houses > 0) {
                player.sendPoints(owner, rent[num_houses]);
            }
            else if (paired) {
                player.sendPoints(owner, 2 * rent[num_houses]);
            }
            else {
            player.sendPoints(owner, rent[num_houses]);
            }
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }
}
