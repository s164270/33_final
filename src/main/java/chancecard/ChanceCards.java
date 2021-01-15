package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;
import java.util.Random;

public class ChanceCards
{
    private GameBoard board;
    private GUI gui;
    private Player[] player;
    private Chance[] chances= new Chance[35];
    public ChanceCards(GameBoard board, GUI gui, Player[] player)
    {
        this.board=board;
        this.gui=gui;
        this.player=player;
        createChance();
    }


    public void createChance()
    {
        chances[0] = new ChanceMove(board, gui, player,"Tag ind på Rådhuspladsen", new String[]{"Rådhuspladsen"});
        chances[1] = new ChanceJail(board, gui, player, "Gå i fængsel, hvis du passerer start modtager du ikke 4000 kr.");
        chances[2] = new ChanceMove(board, gui, player,"Du tager Mols-Linien, hvis du passerer start, modtag 4000 kr.", new String[]{"Mols-Linien"});
        chances[3] = new ChanceMove(board, gui, player,"Ryk frem til Grønningen", new String[]{"Grønningen"});
        chances[4] = new ChancePayDouble(board, gui, player,"Ryk brikken til den nærmeste dampskibselskab og betal ejeren to gange den leje ejeren er berretiget til, hvis den ikke ejes af nogen må du købe den", new String[]{"Rederiet Lindinger","Mols-Linien","Grenaa-Hundested","Skandinavisk Linietrafik"});
        chances[5] = new ChanceMove(board, gui, player,"Ryk frem til Vimmelskaftet, hvis de passerer start indkasser da 4000 kr.", new String[]{"Vimmelskaftet"});
        chances[6] = new ChanceMove(board, gui, player,"Ryk frem til Frederiksberg Allé. Hvis du passerer START, indkasser da 4000 kr.", new String[]{"Frederiksberg Alle"});
        chances[7] = new ChanceMove(board, gui, player,"Ryk frem til Strandvejen. Hvis du passerer START, indkasser da 4000 kr.", new String[]{"Strandvejen"});
        chances[8] = new ChanceMove(board, gui, player,"Tag med den nærmeste færge, hvis du passerer start indkasser da  4000 kr.", new String[]{"Rederiet Lindinger","Mols-Linien","Grenaa-Hundested","Skandinavisk Linietrafik"});
        chances[9] = new ChanceMove(board, gui, player,"Ryk frem til Start.", new String[]{"Start"});
        chances[10] = new ChanceMoveNumber(board, gui, player,"Ryk 3 felter tilbage", -3);
        chances[11] = new ChanceMoveNumber(board, gui, player,"Ryk 3 felter frem", 3);
        chances[12] = new ChanceGetPaid(board, gui, player,"Det er din fødselsdag! Alle giver dig 200 kr.\n Tillykke med fødselsdagen!",200);
        chances[13] = new ChanceGetPaid(board, gui, player,"Du har lagt penge ud til et sammenskudsgilde. Mærkværdigvis betaler alle straks.\n Modtag fra hver medspiller 500 kr.", 500);
        chances[14] = new ChanceGetPaid(board, gui, player,"Du skal holde familiefest og får et tilskud fra hver medspiller på 500 kr.",500);
        chances[15] = new ChancePayOrGet(board, gui, player,"Du har vundet i klasselotteriet. Modtag 500 kr.",500);
        chances[16] = new ChancePayOrGet(board, gui, player,"Du modtager dit aktieudbytte. Modtag 1000 kr. af banken",1000);
        chances[17] = new ChancePayOrGet(board, gui, player,"Kommunen har eftergivet et kvartals skat. Hæv i banken 3000 kr.", 3000);
        chances[18] = new ChancePayOrGet(board, gui, player,"Du have en række med elleve rigtige i tipning, modtag 1000 kr.",1000);
        chances[19] = new ChancePayOrGet(board, gui, player,"Grundet dyrtiden har du fået gageforhøjelse, modtag 1000 kr.",1000);
        chances[20] = new ChancePayOrGet(board, gui, player,"Din præmieobligation er udtrykket. Du modtager 1000 kr. af banken.",1000);
        chances[21] = new ChancePayOrGet(board, gui, player,"Du har solg nogle gamle møbler på auktion. Modtag 1000 kr. af banken.",1000);
        chances[22] = new ChancePayOrGet(board, gui, player,"Værdien af egen avl fra nyttehaven udgør 200kr. som de modtager af banken",200);
        chances[23] = new ChancePayOrGet(board, gui, player,"Du har kørt frem for “fuldt stop”, Betal 1000 kr. i bøde",-1000);
        chances[24] = new ChancePayOrGet(board, gui, player,"Betal for vognvask og smøring 300 kr.",-300);
        chances[25] = new ChancePayOrGet(board, gui, player,"Betal 200 kr. for levering af 2 kasser øl",-200);
        chances[26] = new ChancePayOrGet(board, gui, player,"Betal 3000 kr. for reparation af din vogn",-3000);
        chances[27] = new ChancePayOrGet(board, gui, player,"Du har købt 4 nye dæk til din vogn, betal 1000 kr.",-1000);
        chances[28] = new ChancePayOrGet(board, gui, player,"Du har fået en parkeringsbøde, betal 200 kr. i bøde",-200);
        chances[29] = new ChancePayOrGet(board, gui, player,"Betal din bilforsikring, 1000 kr.", -1000);
        chances[30] = new ChancePayOrGet(board, gui, player,"Du har været udenlands og købt for mange smøger, betal 200 kr. i told.", -200);
        chances[31] = new ChancePayOrGet(board, gui, player,"Tandlægeregning, betal 2000 kr.",-2000);
        chances[32] = new ChanceFreeJail(board, gui, player,"Du løslades uden omkostninger\n Behold dette kort intil du får brug for det.");
        chances[33] = new ChancePayPropertyTax(board, gui, player,"Oliepiserne er steget og de skal betale kr 500 pr hus og kr 2000 pr hotel.", true);
        chances[34] = new ChancePayPropertyTax(board, gui, player,"Ejendomsskatten er steget. Ekstraudgifterne er: 800 kr pr hus, 2300 kr pr hotel.", false);
    }

    public Chance getRandomChance() {
        final Random random = new Random();
        int number;
        number = random.nextInt(35);
        return chances[number];
    }

}
