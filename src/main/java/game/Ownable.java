package game;

public interface Ownable
{
    public boolean isPawned();
    public boolean isPawnable();
    public void pawnOff();
    public void rebuy() ;
    public int getPrice();

}
