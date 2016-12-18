/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui;

import java.awt.Point;
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
import toleco.terrain.DefaultTerrain;
import static org.junit.Assert.*;
import toleco.terrain.Terrain;
import toleco.unit.DefaultUnit;
import toleco.unit.Unit;
import toleco.view.I_GameView;

/**
 *
 * @author Jon
 */
public class MapViewTest {

    private JFrame frame;
    private MapView view;
    private BufferedImage unitImage;
    private BufferedImage terrainImage;
    private BufferedImage selectImage;
    private BufferedImage highlightImage;
    private BufferedImage dest;
    private HashMap<String, BufferedImage> images;
    private Unit unit;
    private Terrain terrain;

    public MapViewTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        try
        {
            unitImage = ImageIO.read(new File("images/units/Spearman.gif"));
            terrainImage = ImageIO.read(new File("images/terrain/Mountains.gif"));
            selectImage = ImageIO.read(new File("images/Select.gif"));
            highlightImage = ImageIO.read(new File("images/Highlight.gif"));
            dest = ImageIO.read(new File("images/Highlight.gif"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        unit = new DefaultUnit();
        terrain = new DefaultTerrain(0,0);
        terrain.setUnit(unit);

        images = new HashMap<String, BufferedImage>();
        images.put(terrain.getType(), terrainImage);
        images.put(unit.getType(), unitImage);
        images.put("Select", selectImage);
        images.put("Highlight", highlightImage);

        frame = new JFrame("Test");
        view = new MapView(images);
        frame.getContentPane().add(view);
        view.setVisible(true);
        frame.pack();
        frame.setVisible(true);
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(1500);
        }
        catch ( Exception e )
        {

        }
    }
    @After
    public void tearDown() {
    }

    /**
     * Test of setGameView method, of class MapView.
     */
    @Test
    public void testSetGameView() {
        System.out.println("setGameView");
        SwingGameView gameView = new SwingGameView(new GameBoard());
        view.setGameView(gameView);
    }

    /**
     * Test of selectLocation method, of class MapView.
     */
    @Test
    public void testSelectLocation() {
        System.out.println("selectLocation");
        int xCoord = 3;
        int yCoord = 5;
        view.selectLocation(xCoord, yCoord);
        testDrawMap();
        sleep();
        //The cell in the 4th row and 6th column has a blue square around it
        System.out.println("click a cell");
        try
        {
            Thread.sleep(5000);
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }
    }

    /**
     * Test of deselectLocation method, of class MapView.
     */
    @Test
    public void testDeselectLocation() {
        view.selectLocation(3,5);
        System.out.println("deselectLocation");
        view.deselectLocation();
        testDrawMap();
        sleep();
        //The previously selected location is no longer selected.
    }

    /**
     * Test of addHighlights method, of class MapView.
     */
    @Test
    public void testAddHighlights() {
        System.out.println("addHighlights");
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(3, 2));
        points.add(new Point(4, 7));
        points.add(new Point(0, 0));
        view.addHighlights(points);
        testDrawMap();
        sleep();
        //The three cells defined by the above points should have a red square.
    }

    /**
     * Test of removeHighLights method, of class MapView.
     */
    @Test
    public void testRemoveHighLights() {
        System.out.println("removeHighLights");
        view.removeHighLights();
        testDrawMap();
        sleep();
        //The three red squares should be gone.
    }

    /**
     * Test of drawMap method, of class MapView.
     */
    @Test
    public void testDrawMap() {
        System.out.println("drawMap");
        Terrain[][] map = new Terrain[10][10];
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                map[i][j] = terrain;
            }
        }
        unit.useAttack();
        view.drawMap(map);
    }

    /**
     * Test of overlayImages method, of class MapView.
     */
    @Test
    public void testOverlayImages() {
        System.out.println("overlayImages");
        BufferedImage expResult = null;
        BufferedImage result = view.overlayImages(dest, new ArrayList<BufferedImage>(images.values()));
        images.put(unit.getType(), result);
        testDrawMap();
        sleep();
        //The new image should have a unit and red highlight.
    }

}