package game;

import chancecard.ChanceCards;
import gui_main.GUI;
import player.Player;

public class ChanceField extends Field {
    private String name;
    private ChanceCards cards;
    public ChanceField(String name)
    {
        super(name);
    }
    public ChanceField(String name, ChanceCards cards)
    {
        this.name = name;
        this.cards = cards;
    }


    @Override
    public String landOnField(Player player) {
        cards.getRandomChance().executeChance(player);
        return player.getName() + " " + "landede på " + name;
    }
}
