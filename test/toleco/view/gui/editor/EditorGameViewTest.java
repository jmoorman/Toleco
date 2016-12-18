/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import toleco.controller.Player;
import toleco.logic.GameBoard;
import toleco.terrain.Terrain;
import toleco.unit.Unit;
import toleco.view.gui.MapView;
import toleco.view.gui.StatusView;

/**
 *
 * @author Jon
 */
public class EditorGameViewTest {

    private EditorGameView view;

    public EditorGameViewTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of drawMap method, of class EditorGameView.
     */
    @Test
    public void testDrawMap() {
        System.out.println("drawMap");

        MapView map = new TestRemove();
        GameBoard board = new GameBoard();
        EditorButtonView buttons = new EditorButtonView();
        ToolView tool = new ToolView();

        view = new EditorGameView(board, map, tool, buttons);

        try
        {
            view.drawMap();
            fail();
        }
        catch (javax.print.attribute.UnmodifiableSetException ex)
        {
            assertTrue(true);
        }
    }

    /**
     * Test of getImage method, of class EditorGameView.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        GameBoard board = new GameBoard();
        view = new EditorGameView(board);

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
     * Test of update method, of class EditorGameView.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        MapView map = new TestRemove();
        GameBoard board = new GameBoard();
        EditorButtonView buttons = new EditorButtonView();
        ToolView tool = new ToolView();

        view = new EditorGameView(board, map, tool, buttons);

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
        catch (javax.print.attribute.UnmodifiableSetException ex)
        {
            assertTrue(true);
        }
    }

    /**
     * Test the displayGameOver method.
     */
    @Test
    public void testDisplayGameOver()
    {
        System.out.println("drawMap");

        GameBoard board = new GameBoard();
        view = new EditorGameView(board);

        view.displayGameOver("Winner");
    }

    private class TestRemove extends MapView
    {
        public TestRemove()
        {
            super(null);
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
    }
}