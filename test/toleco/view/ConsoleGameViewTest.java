/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import toleco.logic.GameBoard;
import toleco.unit.DefaultUnit;
import toleco.unit.Unit;
import org.junit.Test;

/**
 *
 * @author matt
 */
public class ConsoleGameViewTest
{

    private ConsoleGameView testView;
    private GameBoard dummyBoard;

    public ConsoleGameViewTest()
    {
    }
    
    private void setUp()
    {
        dummyBoard = new GameBoard();
        try
        {
            dummyBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
        }
        //set the view's reference to dummyBoard
        testView = new ConsoleGameView();
        testView.update(dummyBoard, null);
    }

    //Defect #223 covered here
    @Test
    public void parsingTest()
    {
        /* Because of the way parsing is handled this needs to be tested manually.
         * Start the game in console mode by giving it the "-c" argument followed
         * by a map file.
         * Type H to see an explanation of all available commands and how to use them.
         */

        System.out.println("\nParsing Test");

        String commands = "H\nS\nS 0,0\nM\nM d\nM u\nE\nS 1,1\nM r\n" +
                "A 0,0\nA xy\nM l\nO\nF\n";
        try
        {
            InputStream in = new ByteArrayInputStream(commands.getBytes("UTF-8"));
            System.setIn(in);
        }
        catch (Exception e)
        {

        }

        setUp();
        testView.start();

    }

    @Test
    public void updateSelectTest()
    {
        System.out.println("\nSelect Test");

        String commands = "S 0,0\n";
        try
        {
            InputStream in = new ByteArrayInputStream(commands.getBytes("UTF-8"));
            System.setIn(in);
        }
        catch (Exception e)
        {

        }

        setUp();
        testView.start();
        //Check selection
        testView.update(null, null);
    }

    @Test
    public void updateMoveTest()
    {
        System.out.println("\nMove Test");
        String commands = "S 0,0\nM d\n";
        try
        {
            InputStream in = new ByteArrayInputStream(commands.getBytes("UTF-8"));
            System.setIn(in);
        }
        catch (Exception e)
        {

        }

        setUp();
        testView.start();
        //Check selection
        testView.update(null, null);
        testView.update(null, null);
    }

    //done
    @Test
    public void displayBattleSummaryTest()
    {
        System.out.println("\nDisplay Battle Summary Test");
        Unit attacker = new DefaultUnit();
        Unit defender = new DefaultUnit();
        String battleSummary = "Doing damage!";

        setUp();
        //Should print out a battle summary including the name of the 
        //two units battling and a summary of the battle
        testView.displayBattleSummary(attacker, defender, battleSummary);

    }

    //done
    @Test
    public void printStatusTest()
    {
        System.out.println("\nPrint Status Test");

        setUp();

        dummyBoard.selectTerrain(1, 0);
        //Should print out information about the terrain at (0,0);
        //Should describe the terrain
        testView.printStatus();

        //Should print a description of the terrain and of the unit on the
        //terrain
        dummyBoard.selectTerrain(0, 0);
        testView.printStatus();
    }

    @Test
    public void emptyMethodTest()
    {
        setUp();
        testView.setController(null);
        testView.getPanel();
        testView.removeHighlights();
    }
}
