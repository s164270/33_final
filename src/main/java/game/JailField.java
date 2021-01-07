package game;

import player.Player;

public class JailField extends Field {

    private int jailCost;
    private String name;
    private final boolean jailType;
    public JailField(String name, int jailCost, boolean jailType)
    {
        super(name, null);
        this.jailCost = jailCost;
        this.jailType = jailType;

    }


    @Override
    public String landOnField(Player player) {
        if(jailType)
        {
            player.setPosition(10);
            player.setInPrison(true);
            System.out.println(player.getName() + " blev sendt til fængsel");
            return player.getName() + " " + "landede på fængselsfeltet og blev sendt til fængslet";
        }
        else
        {
            return player.getName() + " " + "besøgte fængslet";
        }
    }
}
