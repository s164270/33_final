package game;

import chancecard.ChanceCards;
import player.Player;

public class ChanceField extends Field {

    private String name;
    private ChanceCards cards;
    public ChanceField(String name)
    {
        super(name, null);
    }
    public ChanceField(String name, ChanceCards cards)
    {
        this.name = name;
        this.cards = cards;
    }


    @Override
    public String landOnField(Player player) {
        //gui.showMessage(player.getName() + " " + "landede på et chancefelt");
        cards.getRandomChance().executeChance(player);
        return "";
    }

}
