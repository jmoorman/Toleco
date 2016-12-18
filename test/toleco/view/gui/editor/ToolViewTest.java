/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import toleco.logic.GameBoard;
import toleco.view.gui.editor.EditorGameView;
import toleco.view.gui.editor.ToolView;
import static org.junit.Assert.*;

/**
 *
 * @author eralston
 */
public class ToolViewTest {
    private JFrame frame;
    private ToolView tView;
    private EditorGameView eView;
    public ToolViewTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        tView = new ToolView();
        frame = new JFrame("Test");
        GameBoard board = new GameBoard();
        eView = new EditorGameView(board);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void initTest()
    {
        try
        {
            ArrayList<String> units = new ArrayList<String>();
            units.add("Clown");
            units.add("Brute");
            units.add("Spearman");
            ArrayList<String> terrain = new ArrayList<String>();
            terrain.add("Mountains");
            terrain.add("Plains");
            HashMap<String, BufferedImage> images = new HashMap<String,
                    BufferedImage>();
            images.put("Clown", ImageIO.read(new File(
                    "images/units/Clown.gif")));
            images.put("Brute", ImageIO.read(new File(
                    "images/units/Brute.gif")));
            images.put("Spearman", ImageIO.read(new File(
                    "images/units/Spearman.gif")));
            images.put("Mountains", ImageIO.read(new File(
                    "images/terrain/Mountains.gif")));
            images.put("Plains", ImageIO.read(new File(
                    "images/terrain/Plains.gif")));
            tView.setGameView(eView);
            tView.init(terrain, units, images);
            frame.getContentPane().add(tView);
            frame.setVisible(true);
            frame.pack();
            frame.setVisible(true);
            /*
             * Expected Outcome: A 200x300 window should be displayed. The top
             * of the window should display the text Map Editor. Underneath that
             * should be 2 tabs (Units and Terrain) with the unit tab selected.
             * The list underneath that should contain the Clown, Brute and
             * Spearman, each one showing it's sprite on the right and name on
             * the left. When the tester clicks on the Terrain tab the list
             * should now display the sprite and name for Mountains and Plains.
             * Underneath the list area should be 2 radio buttons labeled
             * Leaftron and Cromagmar with Leaftron's button initially selected.
             * When the tester selects one of the buttons that button should
             * become selected and the other button should be deselected if it
             * was selected prior to the click.
             */
            try
            {
                Thread.sleep(1500);
            }
            catch ( Exception e )
            {
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void actionPerformedTest()
    {
    }
}