package game;

import player.Player;

public class ParkingField extends Field
{
    public ParkingField(String name){
        super(name);
    }

    public ParkingField()
    {
        super("Gratis parkering");
    }

    @Override
    public String landOnField(Player player) {
        return player.getName() + " parkerede gratis";
    }
}
