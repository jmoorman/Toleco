/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.junit.Test;
import toleco.controller.Player;
import toleco.terrain.DefaultTerrain;
import toleco.terrain.Terrain;
import toleco.unit.ArmorType;
import toleco.unit.AttackType;
import toleco.unit.DefaultUnit;
import toleco.unit.Unit;

/**
 *
 * @author mtognett
 */
public class StatusViewTest {

    private JFrame frame;
    private StatusView view;
    private BufferedImage unitImage;
    private BufferedImage terrainImage;
    private BufferedImage attackImage;
    private BufferedImage defenseImage;
    private HashMap<String, BufferedImage> images;
    private Unit unit;
    private Terrain terrain;

    public StatusViewTest()
    {

    }

    private void setUp()
    {
        try
        {
            unitImage = ImageIO.read(new File("images/units/Spearman.gif"));
            terrainImage = ImageIO.read(new File("images/terrain/Mountains.gif"));
            attackImage = ImageIO.read(new File("images/Pierce.gif"));
            defenseImage = ImageIO.read(new File("images/Hide.gif"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        unit = new DefaultUnit();
        terrain = new DefaultTerrain(0,0);
        terrain.setUnit(unit);

        images = new HashMap<String, BufferedImage>();
        images.put(unit.getType(), unitImage);
        images.put(terrain.getType(), terrainImage);
        images.put("kHide", defenseImage);
        images.put("kCrush", attackImage);

        frame = new JFrame("Test");
        view = new StatusView();
        view.init(images);
        frame.getContentPane().add(view);
        view.setVisible(true);
        frame.pack();
        frame.setVisible(true);
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch ( Exception e )
        {
            
        }
    }

    @Test
    public void backStoryTest()
    {
        setUp();
        view.switchToBackStory("this is a backstory");
        sleep();
        //A text backstory should be displayed
    }

    @Test
    public void unitSelectTest()
    {
        setUp();
        view.switchToTerrainSelected(terrain);
        sleep();
        //A unit selection should be displayed

    }

    @Test
    public void terrainSelectTest()
    {
        setUp();
        terrain.removeUnit();
        view.switchToTerrainSelected(terrain);
        sleep();
        /*
         * A terrain selection should be displayed
         */
    }

    @Test
    public void battleSummaryTest()
    {
        setUp();
        view.switchToBattleSummary(unitImage, unitImage, "'oh hohoho' said Santa Claus");
        sleep();
        //a battle summary should be displayed, with two unit images and a
        //test summary
    }

    /**
     * Test for defect #182
     * Description: The first pannel to pop up should show a unnit with one move point.
     * The second panel to pop-up should show that same unit, but with one less movement.
     */
    @Test
    public void testDefect182()
    {
        setUp();

        unit = new Unit("TestUnit", Player.kPlayer2, 1, 1, 1, 1,
            AttackType.kCrush, 1, 1, ArmorType.kHide, 1, true);
        terrain = new Terrain("TestTerrain", 1, 1, 0, 0);
        terrain.setUnit(unit);

        images.put(unit.getType(), unitImage);
        images.put(terrain.getType(), terrainImage);

        view.switchToTerrainSelected(terrain);
        sleep();

        unit.decrementMove(1);

        view.switchToTerrainSelected(terrain);
        sleep();
    }
}
