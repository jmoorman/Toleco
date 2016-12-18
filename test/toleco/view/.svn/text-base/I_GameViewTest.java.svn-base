/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view;

import java.awt.image.BufferedImage;
import java.util.Observable;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import toleco.controller.Player;
import toleco.unit.Unit;

/**
 *
 * @author eirq
 */
public class I_GameViewTest {

    public I_GameViewTest() {
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
     * Test for defect #152, I_GameView does not have removeHighlights method.
     */
    @Test
    public void testDefect152() {
        try
        {
            assertNotNull(I_GameView.class.getMethod("removeHighlights", (Class<?>[])null));
        }
        catch (NoSuchMethodException ex)
        {
            fail();
        }
    }
   
}