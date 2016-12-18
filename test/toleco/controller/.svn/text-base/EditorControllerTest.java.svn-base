package toleco.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import toleco.TolecoApp;
import toleco.logic.GameBoard;
import toleco.terrain.DefaultTerrain;
import toleco.terrain.Terrain;
import toleco.unit.DefaultUnit;
import toleco.view.gui.SwingGameView;

/**
 *
 * @author adam
 */
public class EditorControllerTest {

    private GameBoard board;
    private EditorController controller;

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

    public EditorControllerTest() {

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
        controller = new EditorController(board);
        SwingGameView view = new SwingGameView(board);
        controller.setView(view);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class EditorController.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        controller.setChosenPlayer(Player.kPlayer1);
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
     * Test of quit method, of class EditorController.
     */
    @Test
    public void testQuit() {
        System.out.println("quit");
        TolecoApp app = new QuitApp();

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
     * Test of selectCell method, of class EditorController.
     */
    @Test
    public void testSelectCell() {
        System.out.println("selectCell");
        controller.selectCell(0, 0);

        assertEquals("Clown", board.getSelection().getUnit().getType());
    }

    /**
     * Test of update method, of class EditorController.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        TolecoApp app = new TolecoApp();

        NoQuitController cont = new NoQuitController(board);

        cont.setApp(app);
        cont.setView(new SwingGameView(board));

        cont.update(null, "kSelectCell 0 0");

        assertEquals("Clown", board.getMap()[0][1].getUnit().getType());

        cont.update(null, "kChooseTerrain Plains");

        cont.update(null, "kSelectCell 0 0");

        cont.update(null, "kChooseUnit Spearman");

        cont.update(null, "kSelectCell 0 0");

        cont.update(null, "kLoad test/toleco/logic/testAttackMap.ocem");

        cont.update(null, "kSave test/toleco/logic/testAttackMap.ocem");

        cont.update(null, "kQuit");
    }

    /**
     * Test of loadMap method, of class EditorController.
     */
    @Test
    public void testLoadMap() {
        System.out.println("loadMap");
        controller.setChosenPlayer(Player.kPlayer1);

        try
        {
            controller.loadMap("test/toleco/logic/testMap.ocem");
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
            controller.loadMap("test/toleco/logic/testMap.ocem");
            controller.loadMap("test/toleco/logic/myNewMap.ocem");
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
     * Test of setApp method, of class EditorController.
     */
    @Test
    public void testSetApp() {
        System.out.println("setApp");

        TolecoApp app = new TolecoApp();

        controller.setApp(app);
    }

    /**
     * Test of setBoard method, of class EditorController.
     */
    @Test
    public void testSetBoard() {
        System.out.println("setBoard");

        controller.setBoard(board);
    }

    /**
     * Test of setView method, of class EditorController.
     */
    @Test
    public void testSetView() {
        System.out.println("setView");

        SwingGameView myView = new SwingGameView(board);

        controller.setView(myView);
    }

    /**
     * Test for defect #230 (saving a file with spaces).
     */
    public void testDefect230()
    {
        board = new NoSaveBoard();
        controller = new EditorController(board);

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

    private class NoQuitController extends EditorController
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