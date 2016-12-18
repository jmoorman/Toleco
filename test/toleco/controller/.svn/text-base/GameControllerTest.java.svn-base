package toleco.controller;

import toleco.logic.GameBoard;
import toleco.terrain.DefaultTerrain;
import toleco.terrain.Terrain;
import toleco.TolecoApp;
import toleco.unit.DefaultUnit;
import toleco.view.ConsoleGameView;
import toleco.view.I_GameView;
import java.awt.event.ActionEvent;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import toleco.view.gui.SwingGameView;

/**
 * Unit Test for GameController
 * @author adam
 */
public class GameControllerTest extends TestCase
{
    private GameBoard board;
    private GameController controller;
    private I_GameView console;
    private TolecoApp app;
    private String testString;

    private class QuitApp extends TolecoApp
    {
        public QuitApp()
        {
            super("test/toleco/logic/testMap.ocem");
        }

        @Override
        public void cleanUp()
        {
            throw new javax.xml.crypto.NoSuchMechanismException("Great Job!");
        }
    }

    public GameControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        board = new GameBoard();
        try
        {
            board.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
        }
        controller = new GameController(board);
        SwingGameView view = new SwingGameView(board);
        controller.setView(view);
        testString = new String();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setView method, of class GameController.
     */
    @Test
    public void testSetView() {
        System.out.println("setView");
        console = new ConsoleGameView();
        controller.setView(console);
        assertEquals(console, controller.getView());
    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        controller.setActivePlayer(Player.kPlayer1);
        controller.selectCell(0, 0);

        //board.selectTerrain(0, 0);
        assertEquals("Clown", board.getMap()[0][0].getUnit().getType());
        assertNull(board.getMap()[1][0].getUnit());

        controller.update(null, PlayerAction.kMoveDown.toString());

        assertEquals("Clown", board.getMap()[1][0].getUnit().getType());
        assertNull(board.getMap()[0][0].getUnit());

        controller.update(null, PlayerAction.kMoveUp.toString());

        assertEquals("Clown", board.getMap()[0][0].getUnit().getType());
        assertNull(board.getMap()[1][0].getUnit());

        controller.setActivePlayer(Player.kPlayer2);
        controller.selectCell(0, 1);

        assertEquals("Clown", board.getMap()[0][1].getUnit().getType());
        assertNull(board.getMap()[0][2].getUnit());

        controller.update(null, PlayerAction.kMoveRight.toString());

        assertEquals("Clown", board.getMap()[0][2].getUnit().getType());
        assertNull(board.getMap()[0][1].getUnit());

        controller.update(null, PlayerAction.kMoveLeft.toString());

        assertEquals("Clown", board.getMap()[0][1].getUnit().getType());
        assertNull(board.getMap()[0][2].getUnit());
    }

    /**
     * Test of quit method, of class GameController.
     */
    @Test
    public void testQuit() {
        System.out.println("quit");
        app = new QuitApp();

        controller.setApp(app);
        try
        {
            app.cleanUp();
            fail();
        }
        catch (javax.xml.crypto.NoSuchMechanismException ex)
        {
            assertTrue(true);
        }
    }

    /**
     * Test of save method, of class GameController.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        controller.setActivePlayer(Player.kPlayer1);
        try
        {
            board.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        board.setBackStory("Lets do this!");
        Terrain temp = new DefaultTerrain(1, 1);
        temp.setUnit(new DefaultUnit());
        board.getMap()[1][1] = temp;
        board.getMap()[0][0].removeUnit();

        controller.save("test/toleco/logic/myNewMap.ocem");

        try
        {
            board.loadMap("test/toleco/logic/testMap.ocem");
            board.loadMap("test/toleco/logic/myNewMap.ocem");
        }
        catch (Exception e)
        {
        }

        assertEquals("Lets do this!", board.getBackStory());
        assertEquals("Default", board.getMap()[0][0].getType());
        assertEquals("Default", board.getMap()[1][1].getUnit().getType());
        assertNull(board.getMap()[0][0].getUnit());
    }

    /**
     * Test of selectCell method, of class GameController.
     */
    @Test
    public void testSelectCell() {
        System.out.println("selectCell");
        controller.selectCell(0, 0);

        assertEquals(GameState.kTerrainSelected, controller.getState());
        assertEquals("Clown", board.getSelection().getUnit().getType());
    }

    /**
     * Test of actionPerformed method, of class GameController.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        app = new TolecoApp();
        controller.setApp(app);
        controller.setActivePlayer(Player.kPlayer1);
        controller.selectCell(0, 0);

        assertEquals("Clown", board.getMap()[0][0].getUnit().getType());
        assertNull(board.getMap()[1][0].getUnit());

        controller.update(null, PlayerAction.kMoveDown.toString());

        assertEquals("Clown", board.getMap()[1][0].getUnit().getType());
        assertNull(board.getMap()[0][0].getUnit());

        controller.update(null, PlayerAction.kMoveUp.toString());

        assertEquals("Clown", board.getMap()[0][0].getUnit().getType());
        assertNull(board.getMap()[1][0].getUnit());

        controller.update(null, PlayerAction.kEndTurn.toString());
        controller.selectCell(0, 1);

        assertEquals("Clown", board.getMap()[0][1].getUnit().getType());
        assertNull(board.getMap()[0][2].getUnit());

        controller.update(null, PlayerAction.kMoveRight.toString());

        assertEquals("Clown", board.getMap()[0][2].getUnit().getType());
        assertNull(board.getMap()[0][1].getUnit());

        controller.update(null, PlayerAction.kMoveLeft.toString());

        assertEquals("Clown", board.getMap()[0][1].getUnit().getType());
        assertNull(board.getMap()[0][2].getUnit());

        controller.update(null, PlayerAction.kAttack.toString());

        assertEquals(controller.getState(), GameState.kAttackMode);

        controller.update(null, PlayerAction.kCancelAttack.toString());

        controller.update(null, PlayerAction.kSelect.toString() + " 0 1");

        controller.update(null, PlayerAction.kAttack.toString());

        assertEquals(controller.getState(), GameState.kAttackMode);

        controller.update(null, PlayerAction.kSelect.toString() + " 0 0");
    }

    /**
     * Test of setApp method, of class GameController.
     */
    @Test
    public void testSetApp() {
        System.out.println("setApp");
        app = new TolecoApp("test/toleco/logic/testMap.ocem");
        controller.setApp(app);
    }

    /**
     * Test of setBoard method, of class GameController.
     */
    @Test
    public void testSetBoard() {
        System.out.println("setBoard");

        controller.setBoard(board);
    }

    /**
     * Test of setState method, of class GameController.
     */
    @Test
    public void testSetState() {
        System.out.println("setBoard");

        controller.setState(GameState.kTerrainSelected);

        assertEquals(controller.getState(), GameState.kTerrainSelected);
    }

    /**
     * Test of setActivePlayer method, of class GameController.
     */
    @Test
    public void testSetActivePlayer() {
        System.out.println("setBoard");

        controller.setActivePlayer(Player.kPlayer2);
    }

    /**
     * Test of getState method, of class GameController.
     */
    @Test
    public void testGetState() {
        System.out.println("setBoard");

        controller.setState(GameState.kGameOver);

        assertEquals(controller.getState(), GameState.kGameOver);
    }

    /**
     * Test of getView method, of class GameController.
     */
    @Test
    public void testGetView() {
        System.out.println("setBoard");

        ConsoleGameView view = new ConsoleGameView();

        controller.setView(view);

        assertEquals(controller.getView(), view);
    }

    /**
     * Test that defect #226 (exception thrown when nothing is selected) is fixed.
     */
    @Test
    public void testDefect266()
    {
        System.out.println("Defect #266");

        try
        {
            controller.update(board, PlayerAction.kMoveDown.toString());
            assertTrue(true);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    /**
     * Test that gameover is being called properly.
     */
    @Test
    public void testGameOver()
    {
        System.out.println("GameOver");

        board = new GameBoard();
        try
        {
            board.loadMap("maps/TestGameOver.ocem");
        }
        catch (Exception e)
        {
        }
        controller = new NoQuitController(board);
        SwingGameView view = new GameOverTest(board);
        controller.setView(view);
        testString = "NO";

        controller.update(board, (PlayerAction.kSelect.toString() + " 0 0"));
        controller.update(board, (PlayerAction.kMoveRight.toString()));
        controller.update(board, (PlayerAction.kAttack.toString()));
        controller.update(board, (PlayerAction.kSelect.toString() + " 1 1"));

        assertEquals("Leaftron", testString);


        board = new GameBoard();
        try
        {
            board.loadMap("maps/TestGameOver.ocem");
        }
        catch (Exception e)
        {
        }
        controller = new NoQuitController(board);
        view = new GameOverTest(board);
        controller.setView(view);
        testString = "NO";

        controller.update(board, (PlayerAction.kSelect.toString() + " 0 0"));
        controller.update(board, (PlayerAction.kMoveRight.toString()));
        controller.update(board, (PlayerAction.kEndTurn.toString()));
        controller.update(board, (PlayerAction.kSelect.toString() + " 1 1"));
        controller.update(board, (PlayerAction.kAttack.toString()));
        controller.update(board, (PlayerAction.kSelect.toString() + " 0 1"));

        assertEquals("Leaftron", testString);
    }
    /**
     * Test for defect #209 (Units remain selected after ending your turn)
     */
    @Test
    public void testDefect209()
    {
        System.out.println("Defect #209");

        controller.update(null, (PlayerAction.kSelect.toString()+ " 0 0"));
        assertNotNull(board.getSelection());
        controller.update(null, PlayerAction.kEndTurn.toString());
        assertNull(board.getSelection());
    }

    /**
     * Test for defect #230 (saving a file with spaces).
     */
    @Test
    public void testDefect230()
    {
        System.out.println("Defect #230");

        board = new NoSaveBoard();
        controller = new GameController(board);

        try
        {
            controller.update(board, (PlayerAction.kSave.toString() + " I have spaces"));
            fail();
        }
        catch (java.util.InputMismatchException ex)
        {
            assertTrue(true);
        }

        try
        {
            controller.update(board, (PlayerAction.kSave.toString() + " Idonthavespaces"));
            fail();
        }
        catch (java.util.InputMismatchException ex)
        {
            assertTrue(true);
        }
    }

    /**
     * Tests for defect #161, setting the backstory instead of displaying the battle summary on attack.
     */
    @Test
    public void testDefect161()
    {
        System.out.println("Defect #161");

        app = new TolecoApp();
        controller.setApp(app);
        controller.setActivePlayer(Player.kPlayer1);

        controller.update(board, (PlayerAction.kSelect.toString() + " 0 0"));
        controller.update(board, (PlayerAction.kMoveRight.toString()));

        String backStory = board.getBackStory();

        controller.update(board, (PlayerAction.kAttack.toString()));
        assertEquals(backStory, board.getBackStory());
    }

    private class NoSaveBoard extends GameBoard
    {
        public NoSaveBoard()
        {
        }

        @Override
        public void saveMap(String name, Player pl)
        {
            if (!name.trim().contains(" "))
            {
                throw new java.util.InputMismatchException();
            }
        }
    }

    private class GameOverTest extends SwingGameView
    {
        public GameOverTest(GameBoard board)
        {
            super(board);
        }

        public void displayGameOver(String winner)
        {
            testString = winner;
        }
    }

    private class NoQuitController extends GameController
    {
        public NoQuitController(GameBoard board)
        {
            super(board);
        }

        @Override
        public void quit()
        {
        }
    }
}