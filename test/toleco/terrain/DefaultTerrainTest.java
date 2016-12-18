/**
 *
 * @author Jon Moorman
 */

package toleco.terrain;

import toleco.terrain.DefaultTerrain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DefaultTerrainTest {

    public DefaultTerrainTest() {
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

    @Test
    public void constructorTest() {
        System.out.println("constructor");
        DefaultTerrain terr = new DefaultTerrain(3, 5);
        assertEquals("Default", terr.getType());
        assertEquals(0, terr.getDefMod());
        assertEquals(3, terr.getX());
        assertEquals(5, terr.getY());
    }

}