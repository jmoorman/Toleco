/**
 *
 * @author Jon Moorman
 */

package toleco.terrain;

import toleco.terrain.TerrainFactory;
import toleco.terrain.Terrain;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TerrainFactoryTest {

    static Terrain testTerrain1;
    static Terrain testTerrain2;

    public TerrainFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
        testTerrain1 = new Terrain("TestTerrain1", 0, 0, 0, 0);


        testTerrain2 = new Terrain("TestTerrain2", 1, 2, 3, 4);
        
        Properties terrainProps = new Properties();

        File terrainDir = new File("terrains");
      
        if(!terrainDir.exists())
        {
            terrainDir.mkdir();
        }
        File testFile = new File("terrains/testTerrain.properties");
        if(testFile.exists())
        {
            testFile.delete();
        }
        FileWriter out = new FileWriter(testFile);
        terrainProps.setProperty("type",testTerrain1.getType());
        terrainProps.setProperty("defMod", testTerrain1.getDefMod()+"");
        terrainProps.setProperty("moveCost", testTerrain1.getMoveCost()+"");

        File testFile2 = new File("terrains/testTerrainTwo.properties");
        if(testFile2.exists())
        {
            testFile2.delete();
        }

        terrainProps.store(out, "");
        out.close();
        Properties terrainProps2 = new Properties();
        FileWriter out2 = new FileWriter(testFile2);
        terrainProps2.setProperty("type",testTerrain2.getType());
        terrainProps2.setProperty("defMod", testTerrain2.getDefMod()+"");
        terrainProps2.setProperty("moveCost", testTerrain2.getMoveCost()+"");

        terrainProps2.store(out2, "");
        out2.close();
    }

    @AfterClass
    public static void tearDownClass() throws Exception 
    {
        File testFile = new File("terrains/testTerrain.properties");
        testFile.delete();
        File testFile2 = new File("terrains/testTerrainTwo.properties");
        testFile2.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTerrainNames method, of class TerrainFactory.
     */
    @Test
    public void testGetTerrainNames() {
        System.out.println("getTerrainNames");
        TerrainFactory factory = new TerrainFactory();
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add("TestTerrain1");
        expResult.add("TestTerrain2");

        ArrayList<String> result = factory.getTerrainNames();
        
        assertTrue(result.containsAll(expResult));
    }

    /**
     * Test of build method, of class TerrainFactory.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        Terrain expResult = new Terrain("TestTerrain2", 1, 2, 3, 4);

        TerrainFactory factory = new TerrainFactory();
        Terrain result = factory.build("TestTerrain2", 3, 4);
        assertEquals(expResult.getType(), result.getType());
        assertEquals(expResult.getDefMod(), result.getDefMod());
        assertEquals(expResult.getMoveCost(), result.getMoveCost());
        assertEquals(expResult.getX(), result.getX());
        assertEquals(expResult.getY(), result.getY());
    }

}