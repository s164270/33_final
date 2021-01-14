package game;

import player.Player;

public interface Ownable
{
    public boolean isPawned();
    public boolean isPawnable();
    public void pawnOff();
    public void rebuy() ;
    public int getPrice();
    public int getRent();
    public int totalPrice();
    public void setOwner(Player p);
    public Player getOwner();

}
