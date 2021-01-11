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
    private Chance[] chances= new Chance[33];
    public ChanceCards(GameBoard board, GUI gui, Player[] player)
    {
        this.board=board;
        this.gui=gui;
        this.player=player;
        createChance();
    }


    public void createChance()
    {
        chances[0] = new ChanceMove(board, gui, player,"Tag ind på Rådhuspladsen", Color.BLUE,Color.ORANGE, new String[]{"Rådhuspladsen"});
        chances[1] = new ChanceJail(board, gui, player, "Gå i fængsel, Hvis du passerer start mødtager du ikke 4000kr", Color.BLUE,Color.ORANGE);
        chances[2] = new ChanceMove(board, gui, player,"Du tager Mols-Linien, hvis du passerer start, mødtag 4000kr", Color.BLUE,Color.ORANGE, new String[]{"Mols-Linien"});
        chances[3] = new ChanceMove(board, gui, player,"Ryk frem til Grønningen", Color.BLUE,Color.ORANGE, new String[]{"Grønningen"});
        chances[4] = new ChanceMove(board, gui, player,"Ryk Brikken til den nærmeste dampskibselskab og betal ejeren to gange den leje ejeren er beretet til\n hvis den ikke ejes af nogen må du købe den", Color.BLUE,Color.ORANGE, new String[]{"Rederiet Lindinger","Mols-Linien","Grenaa-Hundested","Skandinavisk Linietrafik"});
        chances[5] = new ChanceMove(board, gui, player,"Ryk frem til Vimmelskaftet, hvis de passerer start indkasser da kr 4000", Color.BLUE,Color.ORANGE, new String[]{"Vimmelskaftet"});
        chances[6] = new ChanceMove(board, gui, player,"Ryk frem til Frederiksberg Allé. Hvis De passere START, indkasser da 4000 kr.", Color.BLUE,Color.ORANGE, new String[]{"Frederiksberg Alle"});
        chances[7] = new ChanceMove(board, gui, player,"Ryk frem til Strandvejen. Hvis De passere START, indkasser da 4000 kr.", Color.BLUE,Color.ORANGE, new String[]{"Strandvejen"});
        chances[8] = new ChanceMove(board, gui, player,"Tag med den nærmeste færge, hvis de passerer start indkasser da kr 4000.", Color.BLUE,Color.ORANGE, new String[]{"Rederiet Lindinger","Mols-Linien","Grenaa-Hundested","Skandinavisk Linietrafik"});
        chances[9] = new ChanceMove(board, gui, player,"Ryk frem til Start.", Color.BLUE,Color.ORANGE, new String[]{"Start"});
        chances[10] = new ChanceMoveNumber(board, gui, player,"Ryk 3 felter tilbage", Color.BLUE,Color.ORANGE, -3);
        chances[11] = new ChanceMoveNumber(board, gui, player,"Ryk 3 felter frem", Color.BLUE,Color.ORANGE, 3);
        chances[12] = new ChanceGetPaid(board, gui, player,"Det er din fødselsdag! Alle giver dig 200kr\n Tillykke med fødselsdagen!", Color.BLUE,Color.ORANGE,200);
        chances[13] = new ChanceGetPaid(board, gui, player,"De har lagt penge ud til et sammenskudsgilde. Mærkværdigvis betaler alle straks.\n Modtag fra hver medspiller 500 kr.", Color.BLUE,Color.ORANGE,500);
        chances[14] = new ChanceGetPaid(board, gui, player,"De skal holde familiefest og får et tilskud fra hver medspiller på 500 kr.", Color.BLUE,Color.ORANGE, 500);
        chances[15] = new ChancePayOrGet(board, gui, player,"De har vundet i klasselotteriet. Modtag 500 kr.", Color.BLUE,Color.ORANGE, 500);
        chances[16] = new ChancePayOrGet(board, gui, player,"De modtager Deres aktieudbytte. Modtag kr 1000 af banken", Color.BLUE,Color.ORANGE, 1000);
        chances[17] = new ChancePayOrGet(board, gui, player,"Kommunen har eftergivet et kvartals skat. Hæv i banken 3000 kr.", Color.BLUE,Color.ORANGE, 3000);
        chances[18] = new ChancePayOrGet(board, gui, player,"De have en række med elleve rigtige i tipning, modtag kl 1000", Color.BLUE,Color.ORANGE, 1000);
        chances[19] = new ChancePayOrGet(board, gui, player,"Grundet dyrtiden har De fået gageforhøjelse, modtag kr 1000.", Color.BLUE,Color.ORANGE, 1000);
        chances[20] = new ChancePayOrGet(board, gui, player,"Deres præmieobligation er udtrykket. De modtager 1000 kr af banken.", Color.BLUE,Color.ORANGE, 1000);
        chances[21] = new ChancePayOrGet(board, gui, player,"De har solg nogle gamle møbler på auktion. Modtag 1000 kr af banken.", Color.BLUE,Color.ORANGE, 1000);
        chances[22] = new ChancePayOrGet(board, gui, player,"Værdien af egen avl fra nyttehaven udgør 200 som de modtager af banken", Color.BLUE,Color.ORANGE, 200);
        chances[23] = new ChancePayOrGet(board, gui, player,"De har kørt frem for “fuldt stop”, Betal 1000 kroner i bøde", Color.BLUE,Color.ORANGE, -1000);
        chances[24] = new ChancePayOrGet(board, gui, player,"Betal for vognvask og smøring kr 300", Color.BLUE,Color.ORANGE, -300);
        chances[25] = new ChancePayOrGet(board, gui, player,"Betal kr 200 for levering af 2 kasser øl", Color.BLUE,Color.ORANGE, -200);
        chances[26] = new ChancePayOrGet(board, gui, player,"Betal 3000 for reparation af deres vogn", Color.BLUE,Color.ORANGE, -3000);
        chances[27] = new ChancePayOrGet(board, gui, player,"De har købt 4 nye dæk til Deres vogn, betal kr 1000", Color.BLUE,Color.ORANGE, -1000);
        chances[28] = new ChancePayOrGet(board, gui, player,"De har fået en parkeringsbøde, betal kr 200 i bøde", Color.BLUE,Color.ORANGE, -200);
        chances[29] = new ChancePayOrGet(board, gui, player,"Betal deres bilforsikring, kr 1000", Color.BLUE,Color.ORANGE, -1000);
        chances[30] = new ChancePayOrGet(board, gui, player,"De har været udenlands og købt for mange smøger, betal kr 200 i told.", Color.BLUE,Color.ORANGE, -200);
        chances[31] = new ChancePayOrGet(board, gui, player,"Tandlægeregning, betal kr 2000.", Color.BLUE,Color.ORANGE, -2000);
        chances[32] = new ChanceFreeJail(board, gui, player,"Du løslades uden omkostninger\n Behold dette kort intil du får brug for det.", Color.BLUE,Color.ORANGE);
        for (int i = 15; i < chances.length; i++)
        {
           int j = i % player.length;
            chances[i] = new ChanceMovePlayer(board, gui, player,"Giv dette kort til "+ player[j].getName(), Color.BLUE,Color.ORANGE, player[j]);

        }
    }

    public Chance getRandomChance() {
        final Random random = new Random();
        int number;
        number = random.nextInt(33);
        return chances[number];
    }

}
