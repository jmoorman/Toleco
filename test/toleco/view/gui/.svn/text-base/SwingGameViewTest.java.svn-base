/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import toleco.controller.Player;
import toleco.controller.PlayerAction;
import static org.junit.Assert.*;
import toleco.logic.GameBoard;
import toleco.terrain.Terrain;
import toleco.unit.DefaultUnit;

/**
 *
 * @author eirq
 */
public class SwingGameViewTest
{
    private SwingGameView view;

    public SwingGameViewTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() 
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test the getPanel method of SwingGameView.
     * A window should pop-up for three seconds.
     * The windows should contain a 10 x 10 grid to the left, a text box
     * on the right, and some buttons below the text box.
     *
     */
    @Test
    public void testGetPanel()
    {
        System.out.println("getPanel");

        GameBoard board = new GameBoard();
        JFrame frame = new JFrame();

        view = new SwingGameView(board);

        frame.add(view.getPanel());

        frame.pack();
        frame.setVisible(true);

        try
        {
            Thread.sleep(500);
        }
        catch (Exception ex)
        {
        }

        assertTrue(true);
    }

    /**
     * Test of drawMap method, of class SwingGameView.
     */
    @Test
    public void testDrawMap()
    {
        System.out.println("drawMap");

        MapView map = new TestRemove();
        GameBoard board = new GameBoard();
        GameButtonView buttons = new GameButtonView();
        StatusView stat = new StatusView();

        view = new SwingGameView(board, map, stat, buttons);

        try
        {
            view.drawMap();
            fail();
        }
        catch (javax.print.attribute.UnmodifiableSetException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view = new SwingGameView(board);
        view.drawMap();
    }

    /**
     * Test of getImage method, of class SwingGameView.
     */
    @Test
    public void testGetImage()
    {
        System.out.println("getImage");
        GameBoard board = new GameBoard();
        view = new SwingGameView(board);

        BufferedImage test = null;

        try
        {
            test = ImageIO.read(new File("images/units/Brute.gif"));
        }
        catch (Exception ex)
        {
            fail();
        }

        assertEquals(test.getSources(), view.getImage("Brute").getSources());
    }

    /**
     * Test setBoard
     */
    @Test
    public void testSetBoard()
    {
        System.out.println("setBoard");

        GameBoard board = new GameBoard();
        TestGameView view2 = new TestGameView(board);

        GameBoard board2 = new GameBoard();
        view2.setBoard(board2);

        assertEquals(board2, view2.getBoard());
    }

    /**
     * Test of update method, of class SwingGameView.
     */
    @Test
    public void testUpdate()
    {
        System.out.println("update");

        MapView map = new TestRemove();
        StatusView status = new BrokenStatus();
        GameButtonView buttons = new GameButtonView();
        GameBoard board = new GameBoard();

        view = new SwingGameView(board, map, status, buttons);

        try
        {
            view.update(board, null);
            fail();
        }
        catch (javax.print.attribute.UnmodifiableSetException ex)
        {
            assertTrue(true);
        }

        try
        {
            view.update(null, new ArrayList());
            fail();
        }
        catch (java.lang.ArrayStoreException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view = new SwingGameView(board);
        view.update(board, Player.kPlayer1);

        view = new SwingGameView(board);
        view.update(board, Player.kPlayer2);

        ArrayList<Point> pts = new ArrayList<Point>();
        pts.add(new Point(0, 0));
        pts.add(new Point(9, 9));

        view.update(board, pts);

        board.selectTerrain(0, 0);
        view.update(board, null);
    }

    /**
     * Test to make sure that the keys were set up correctly.
     */
    @Test
    public void testSetUpKeys()
    {
        GameBoard board = new GameBoard();
        TestGameView testView = new TestGameView(board);

        try
        {

        }
        catch (java.lang.reflect.UndeclaredThrowableException ex)
        {

        }
    }

    /**
     * Test of displayBattleSummary method, of class SwingGameView.
     */
    @Test
    public void testDisplayBattleSummary()
    {
        System.out.println("displayBattleSummary");

        StatusView status = new BrokenStatus();
        GameBoard board = new GameBoard();


        MapView map = new TestRemove();
        GameButtonView buttons = new GameButtonView();

        view = new SwingGameView(board, map, status, buttons);

        try
        {
            view.displayBattleSummary(new DefaultUnit(), new DefaultUnit(), "");
            fail();
        }
        catch (java.lang.IllegalStateException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view = new SwingGameView(board);
        view.displayBattleSummary(new DefaultUnit(), new DefaultUnit(), "Hello there");
    }

    /**
     * Test of displayBackStory method, of class SwingGameView.
     */
    @Test
    public void testDisplayBackStory()
    {
        System.out.println("displayBackStory");

        StatusView status = new BrokenStatus();
        GameBoard board = new GameBoard();

        MapView map = new TestRemove();
        GameButtonView buttons = new GameButtonView();

        view = new SwingGameView(board, map, status, buttons);

        try
        {
            view.displayBackStory("Funny Story!");
            fail();
        }
        catch (NegativeArraySizeException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view = new SwingGameView(board);
        view.displayBackStory("A story");
    }

    /**
     * Test of displayBackStory method, of class SwingGameView.
     */
    @Test
    public void testDisplayTerrainSelected() throws java.io.FileNotFoundException
    {
        System.out.println("displayTerrainSelected");

        StatusView status = new BrokenStatus();
        GameBoard board = new GameBoard();

        MapView map = new TestRemove();
        GameButtonView buttons = new GameButtonView();

        view = new SwingGameView(board, map, status, buttons);

        try
        {
            view.displayTerrainSelected(Player.kPlayer1);
            fail();
        }
        catch (java.util.ConcurrentModificationException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        board.loadMap("maps/BasicMap.ocem");

        view = new SwingGameView(board);

        board.selectTerrain(0, 0);
        view.displayTerrainSelected(Player.kPlayer1);

        board.selectTerrain(9, 9);
        view.displayTerrainSelected(Player.kPlayer1);
    }

    /**
     * Test of removeHighlights method, of class SwingGameView.
     */
    @Test
    public void testRemoveHighlights()
    {
        System.out.println("removeHighlights");
        MapView map = new TestRemove();
        GameBoard board = new GameBoard();

        GameButtonView buttons = new GameButtonView();
        StatusView stat = new StatusView();

        view = new SwingGameView(board, map, stat, buttons);

        try
        {
            view.removeHighlights();
            fail();
        }
        catch (javax.xml.crypto.NoSuchMechanismException ex)
        {
            assertTrue(true);
        }

        board = new GameBoard();
        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view = new SwingGameView(board);
        view.removeHighlights();
    }

    /**
     * Test the displayGameOver method.
     * Defect #219
     */
    @Test
    public void testDisplayGameOver()
    {
        System.out.println("displayGameOver");

        GameBoard board = new GameBoard();
        view = new SwingGameView(board);

        view.displayGameOver("Winner");
    }

    /**
     * Test acceptAction
     */
    @Test
    public void testAcceptAction()
    {
        System.out.println("acceptAction");

        GameBoard board = new GameBoard();
        view = new SwingGameView(board);

        view.acceptAction(PlayerAction.kCancelAttack.toString());
    }

    /**
     * Test for defect #184: no default image given if the desired image is not found.
     */
    @Test
    public void testDefect184()
    {
        System.out.println("Defect #184");

        GameBoard board = new GameBoard();
        view = new SwingGameView(board);
        
        BufferedImage img = view.getImage("TheReIs_NO_iMagE with this Namme!");

        assertNotNull(img);
    }

    /**
     * Test for defect #211. A null pointer thrown if there is no image for a unit.
     */
    @Test
    public void testDefect211()
    {
        System.out.println("Defect #211");

        GameBoard board = new GameBoard();
        view = new SwingGameView(board);

        try
        {
            view.getImage("n0N_existant un17");
            assertTrue(true);
        }
        catch (NullPointerException ex)
        {
            fail();
        }
    }

    /**
     * Test for defect #222: slection overlay is not removed from unit on endturn.
     */
    @Test
    public void testDefect222()
    {
        System.out.println("Defect #222");

        MapView map = new TestRemove();
        GameBoard board = new GameBoard();

        GameButtonView buttons = new GameButtonView();
        StatusView stat = new StatusView();

        view = new SwingGameView(board, map, stat, buttons);

        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        view.acceptAction((PlayerAction.kSelect.toString() + " 0 0"));

        try
        {
            view.update(board, Player.kPlayer2);
            fail();
        }
        catch (FunException fex)
        {
            assertEquals("deselectLocation", fex.getMessage());
        }


        view = new SwingGameView(board);
        view.acceptAction((PlayerAction.kSelect.toString() + " 0 0"));
        view.removeHighlights();
    }

    /**
     * Test for defect #226: moving with no selection causes a null pointer.
     */
    @Test
    public void testDefect226()
    {
        System.out.println("Defect #226");

        GameBoard board = new GameBoard();
        view = new SwingGameView(board);

        try
        {
            board.loadMap("maps/BasicMap.ocem");
        }
        catch (java.io.IOException ex)
        {
            fail();
        }

        try
        {
            view.acceptAction((PlayerAction.kMoveDown.toString()));
            assertTrue(true);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    private class TestRemove extends MapView
    {
        public TestRemove()
        {
            super(null);
        }

        @Override
        public void removeHighLights()
        {
            throw new javax.xml.crypto.NoSuchMechanismException("Great Job!");
        }

        @Override
        public void drawMap(Terrain[][] test)
        {
            throw new javax.print.attribute.UnmodifiableSetException("Great Job!");
        }

        @Override
        public void addHighlights(ArrayList points)
        {
            throw new java.lang.ArrayStoreException("Great Job!");
        }

        @Override
        public void deselectLocation()
        {
            throw new FunException("deselectLocation");
        }
    }

    private class FunException extends RuntimeException
    {
        public FunException(String msg)
        {
            super(msg);
        }
    }

    private class BrokenStatus extends StatusView
    {
        @Override
        public void switchToBattleSummary(BufferedImage attacker,
    BufferedImage defender, String summary)
        {
            throw new java.lang.IllegalStateException("Great Job!");
        }

        @Override
        public void switchToBackStory(String story)
        {
            if (story.length() > 0)
            {
                throw new NegativeArraySizeException("Great job");
            }
        }

        @Override
        public void switchToTerrainSelected(Terrain terrain)
        {
            throw new java.util.ConcurrentModificationException("Great job");
        }
    }

    private class TestGameView extends SwingGameView
    {
        private GameBoard brd;

        public TestGameView(GameBoard brd)
        {
            super(brd);
            this.brd = brd;
        }

        public GameBoard getBoard()
        {
            return brd;
        }

        @Override
        public void setBoard(GameBoard brd)
        {
            this.brd = brd;
            super.setBoard(brd);
        }

    }
}