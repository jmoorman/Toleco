package toleco;

import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eriq Augustine
 */
public class TolecoAppTest {

    public TolecoAppTest() {
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
     * Test of actionPerformed method, of class TolecoApp.
     * Also Tests Defect #191.
     */
    @Test
    public void testActionPerformed()
    {
        System.out.println("Defect #191");
        System.out.println("actionPerformed");

        try
        {
            System.out.println("A window with \"Ocelot\", \"New Game\", \"Load Game\", \"Map Editor\", \"Help\", and \"Quit\" should pop up.");
            TolecoApp app = new TolecoApp();
            System.out.println("Please wait a moment.");

            Thread.sleep(1000);
            assertTrue(true);

            System.out.println("A filechooser should pop up, please choose a .ocem file.");

            ActionEvent ev = new ActionEvent(app, 1, "newgame");
            app.actionPerformed(ev);

            Thread.sleep(1500);
            assertTrue(true);

            app.cleanUp();

            System.out.println("A filechooser should pop up, please choose a .osm file.");
            
            ev = new ActionEvent(app, 1, "loadgame");
            app.actionPerformed(ev);

            Thread.sleep(1500);
            assertTrue(true);

            app.cleanUp();

            System.out.println("A filechooser should pop up, please choose a .ocem file.");

            ev = new ActionEvent(app, 1, "editor");
            app.actionPerformed(ev);

            Thread.sleep(1500);
            assertTrue(true);

            app.cleanUp();

            System.out.println("A help dialog should pop up, please click \"Ok\".");

            ev = new ActionEvent(app, 1, "help");
            app.actionPerformed(ev);
            
            Thread.sleep(500);
            assertTrue(true);

            System.out.println("An about dialog should pop up, please click \"Ok\".");

            ev = new ActionEvent(app, 1, "about");
            app.actionPerformed(ev);

            Thread.sleep(500);
            assertTrue(true);
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    @Test
    public void testConsoleLaunch()
    {
        try
        {
            TolecoApp test = new TolecoApp("FAKE/maps/BasicMap.ocem");
            assertTrue(true);
        }
        catch (Exception ex)
        {
            fail();
        }
    }
}