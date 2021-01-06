package game;

import player.Player;
import chancecard.ChanceFreeJail;

public class JailField extends Field {
    private int jailCost;
    private String name;
    private boolean jailType;
    public JailField(String name, int jailCost, boolean jailType)
    {
        super(name);
        this.jailCost = jailCost;
        this.jailType = jailType;

    }


    @Override
    public String landOnField(Player player) {
        if(jailType)
        {
            player.setPosition(6);
            player.setInPrison(true);
            return player.getName() + " " + "landede på fængselsfeltet og blev sendt til fængslet";
        }
        else
        {
            return player.getName() + " " + "besøgte fængslet";
        }
    }
}
