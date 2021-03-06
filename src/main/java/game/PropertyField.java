package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Ownable;
import gui_fields.GUI_Street;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class PropertyField extends Field implements Ownable{
    private int cost;
    private int housePrice;
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

    public PropertyField(String name, int cost, int housePrice, int rent[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.housePrice = housePrice;
        this.rent = rent;
        this.num_houses = 0;
        this.hotelBuild = false;
        this.auction = auction;
    }

    public PropertyField(String name, int cost, int housePrice, int rent[], PropertyField neighbor[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.housePrice = housePrice;
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

    @Override
    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        this.name = guiField.getTitle();
        String description="Huspris  :  "+housePrice + " kr.<br>";
        description+= "husleje priser <br>";
        description += String.format("%d hus %d kr <br>",0,rent[0]);
        description += String.format("%d hus %d kr <br>",1,rent[1]);
        for (int i = 2; i < rent.length-1; i++)
        {
            description += String.format("%d huse %d kr <br>",i,rent[i]);
        }
        description += String.format("1 hotel %d kr <br>",rent[5]);
        guiField.setDescription(description);

    }
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        owner=player;
        if (owner!= null)
        {
            player.getOwnedFields().add(this);
            ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
            guiField.setSubText("kr. " + getRent());
        }
        else
        {
            ((GUI_Ownable)guiField).setBorder(null);
            guiField.setSubText("kr. " + getPrice());
            pawned=false;
        }
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
        if(paired)
            guiField.setSubText("kr. " + getRent());
    }

    public int getHousePrice() {
        return housePrice;
    }

    public boolean housesCanBeSold()
    {
        boolean hotelsInNeighborhood = false;
        for(int i = 0; i < neighbor.length; i++)
        {
            hotelsInNeighborhood = hotelsInNeighborhood || neighbor[i].isHotelBuild();
        }
        return ((num_houses > 0) && (!hotelsInNeighborhood));
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
            guiField.setSubText("kr. " + getRent());
        }
    }

    @Override
    public void rebuy() {
        owner.addPoints(- (int) ((cost/2)*1.1));
        pawned=false;
        guiField.setSubText("kr. " + getRent());
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
    public int totalPrice() {
        if (hotelBuild)
            return cost + 5 * housePrice;
        else
            return cost + num_houses * housePrice;
    }

    @Override
    public boolean isPawnable()
    {
        return (num_houses==0) && !pawned;
    }

    @Override
    public int getRent()
    {
        int price=0;
        if (!pawned)
        {
            if (hotelBuild)
            {
                price = rent[5];
            } else
            {
                price = rent[num_houses];
            }
            if (paired)
            {
                price *= 2;
            }
        }
        else
        {
            price=0;
        }
        return price;
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
        guiField.setSubText("kr. " + rent[this.num_houses]);
    }

    public boolean isHotelBuild() {
        return hotelBuild;
    }

    public void buyHotel()
    {
        getOwner().addPoints(-housePrice);
        num_houses = 0;
        hotelBuild = true;
        ((GUI_Street)guiField).setHotel(true);
        guiField.setSubText("kr. " + rent[5]);
    }

    public void sellHotel()
    {
        getOwner().addPoints(housePrice / 2);
        hotelBuild = false;
        ((GUI_Street)guiField).setHotel(false);
        setNum_houses(4);
    }

    public void buyHouse(int numberToBuy)
    {
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

    public void sellHouses(int numberToSell)
    {
        int neighborIndex = 0;
        int housesInGroup = 0;
        for(int i = 0; i < neighbor.length; i++) {
            housesInGroup += neighbor[i].getNum_houses();
            if (neighbor[i] == this) {
                neighborIndex = i;
            }
        }

        if(numberToSell == 0) {
            //go back
        }
        else if(numberToSell < 0 || numberToSell > 12) {
            gui.showMessage("Ugyldigt antal");
        }
        else if(housesInGroup - numberToSell < 0) {
            gui.showMessage("Så mange huse er der ikke");
        }
        else {
            this.getOwner().addPoints(numberToSell * housePrice / 2);

            // sale houses is distributed evenly among the properties, but the selected property is prioritized
            int housesLeftToSell = numberToSell;
            while (housesLeftToSell > 0)
            {
                int currentMax = neighbor[0].getNum_houses();
                int maxIndex = 0;
                for(int i = 0; i < neighbor.length; i++)
                {
                    if(neighbor[i].getNum_houses() >= currentMax)
                    {
                        currentMax = neighbor[i].getNum_houses();
                        maxIndex = i;
                    }
                }

                if(neighbor[neighborIndex].getNum_houses() == currentMax)
                {
                    neighbor[neighborIndex].setNum_houses(currentMax - 1);
                }
                else
                {
                    neighbor[maxIndex].setNum_houses(currentMax - 1);
                }
                housesLeftToSell--;
            }
        }
    }

    public void buyProperty(Player player, int price)
    {
        player.sendPoints(null, price);
        setOwner(player);

        boolean pair =true;
        for (int i = 0; i < neighbor.length; i++)
        {
            pair = pair && neighbor[i].getOwner() == this.getOwner();
        }
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setPaired(pair);
        }
    }

    @Override
    public void landOnField(Player player) {

        if(owner == null)
        {
            gui.showMessage(player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle());//player has to buy the property

            switch (gui.getUserButtonPressed("Vil du købe " + guiField.getTitle() + " for " + cost + "?", "JA", "NEJ"))
            {
                case "JA":
                    if(player.getPoints() >= cost)
                    {
                        buyProperty(player, cost);
                        gui.showMessage(player.getName() + " " +  "har købt ejendomsfeltet" + " " + guiField.getTitle());
                    }
                    else
                    {
                        gui.showMessage("Du har ikke nok penge på kontoen.");
                        gui.showMessage("Auktion for at købe" + " " + guiField.getTitle() + " " + "starter");
                        auction.startAuction(this);
                    }
                    break;
                case "NEJ":
                    gui.showMessage("Auktion for at købe" + " " + guiField.getTitle() + " " + "starter");
                    auction.startAuction(this);
                    break;
                default:
                    System.out.println("FEJL");
            }
        }
        else if (pawned && owner != player) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi ejendommen er pantsat");
        }
        else if( owner == player) {
            gui.showMessage(player.getName() + " landede på sit eget felt" + " " + guiField.getTitle());
        }

        else if (owner.isInPrison()) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi de er i fængsel");
        }

        else if(owner != player)
        {
            gui.showMessage(player.getName()+ " " + "landede på" + " " + getOwner().getName()  + "'s" + " " + " felt " + guiField.getTitle() + " " + "og skal betale kr." + " " + getRent() + " " + "i leje"   );
            player.sendPoints(owner, getRent());
        }


    }

    public int getTotalHouses()
    {
        int totalHouses = 0;
        for(int i = 0; i < neighbor.length; i++) {
            totalHouses += neighbor[i].getNum_houses();
        }
        return totalHouses;
    }

}
