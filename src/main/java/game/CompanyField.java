package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class CompanyField extends Field implements Ownable{

    private final String name;
    private final int cost;

    protected final GUI gui;

    private Player owner;
    private Auction auction;
    private boolean pawned;
    public int wealth;

    public CompanyField(String name, GUI gui, Auction auction, int cost) {
        this.name = name;
        this.gui = gui;
        this.cost = cost;
        this.auction = auction;
        this.pawned=false;

    }
    private void buyProperty(Player player) {
        player.addPoints(-cost);
        setOwner(player);

    }
    public void buyProperty(Player player, int auctionPrice) {
        player.addPoints(-auctionPrice);
        setOwner(player);
    }

    public void setOwner(Player player) {
        owner = player;
        owner.getCompany().add(this);
        owner.getOwnedFields().add(this);
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }


    @Override
    public String landOnField(Player player) {
        if (owner == null) {

            String option = gui.getUserButtonPressed("Vil du købe " + name + " for " + cost + "?", "Ja", "Nej");

            if (option.toLowerCase().equals("ja")) {
                if (player.getPoints() >= cost) {
                    buyProperty(player);
                    return player.getName() + " har købt " + name + " for " + cost + " kr.";
                } else {
                    return player.getName() + " har ikke råd til at købe " + name;
                }
            } else {
                auction.startAuction(this);
            }

        }

        else if (owner != player) {
            int rent = player.getDiceSum() * (owner.getCompany().size() == 2 ? 200 : 100);
            player.sendPoints(owner, rent);
            return player.getName() + " skulle betale " + owner.getName() + " "  + rent + " kr.";
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }

    @Override
    public void pawnOff() {
        if(!pawned )
        {
            owner.addPoints(cost/2);
            pawned=true;
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
    public int totalPrice() {
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
        return !pawned;
    }

}
