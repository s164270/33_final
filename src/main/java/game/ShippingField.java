package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class ShippingField extends Field {

    private final String name;
    private final int cost;
    private final int[] rent;

    protected final GUI gui;

    private Player owner;
    private Auction auction;

    ShippingField(String name, int cost, GUI gui, Auction auction,  int... rent) {
        this.name = name;
        this.cost = cost;
        this.gui = gui;
        this.rent = rent;
        this.auction = auction;
    }
    private void buyProperty(Player player) {
        player.addPoints(-cost);
        setOwner(player);

    }
    public void setOwner(Player player) {
        owner=player;
        owner.getRedderier().add(this);
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }
    public void buyProperty(Player player, int auctionPrice) {
        player.addPoints(-auctionPrice);
        setOwner(player);
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
            player.sendPoints(owner, rent[owner.getRedderier().size() - 1]);
            return player.getName() + " landede på " + owner.getName() + "'s felt: " + name + " og skal betale " + rent[owner.getRedderier().size() - 1] + " kr";
        }

        return player.getName() + " landede på feltet" + " " + guiField.getTitle();
    }


    @Override
    public String getName() {
        return name;
    }

}
