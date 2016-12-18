package toleco.terrain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Properties;

/**
* This singleton class is responsible for instantiating and setting the values inside
* of a new Terrain object. It also keeps a list of all of the possible Terrains.
* It does not keep a list of all of the Terrains it has built, only the ones it can
* build in the future.
*
* @author Andrew Barton
* @version 1.0
*/
public class TerrainFactory
{
    /**
    * A lookup table mapping a terrain name to an instance of the terrain.
    */
    private HashMap<String, Terrain> terrainMap;

    /**
     * The path for the directory that terrains' properties files are located.
     */
    private static final String kTerrainDirectory = "terrains";

    /**
    * Although it takes no parameters, the constructor must read in all of the files
    * from the Terrain folder and attempt to create an instance of each terrain. 
    * The TerrainFactory can only get new Terrains from the Terrain Folder, with
    * the exception of DefaultTerrain.
    */
    public TerrainFactory()
    {
        //Create a File.
        File terrainDir = null;

        //TRY
        try
        {
            //Create a new File with the proper directory.
            terrainDir =
                new File(kTerrainDirectory);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about not being able to find the terrain directory.
            System.err.println("Unable to load terrain directory.");
        }

        terrainMap = new HashMap<String, Terrain>();
        try
        {
            //if the unit directory exists and is a dir
            if(terrainDir.isDirectory())
            {
                File[] terrainFiles = terrainDir.listFiles();
                //FOR every file in the directory
                for(File terrain:terrainFiles)
                {
                    //IF the file is a properties file
                    if(terrain.isFile() &&
                        terrain.getCanonicalPath().toLowerCase().contains(".properties"))
                    {
                        //Call TerrainFromFile with the filename
                        terrainFromFile(terrain.getPath());
                    //END IF
                    }
                }//END FOR
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
    * Get a list of Strings of all of the different Terrain types that this
    * TerrainFactory can build.
    * @return an ArrayList of Strings of all of the different Terrain types that this
    * TerrainFactory can build.
    */
    public ArrayList<String> getTerrainNames()
    {
        //Return an ArrayList<String> names with every key
        return new ArrayList<String>(terrainMap.keySet());
    }
    
    /**
    * Given a name (unique to the type of Terrain, not the individual Terrain),
    * and a x and y that this Terrain will think it is located at, construct a
    * terrain of type <name> from the list of Terrains that TerrainFactory has.
    * TerrainFactory will put all of the values into the Terrain except for the
    * Unit, and will return DefaultTerrain if it cannot find a Terrain by the
    * given name.
    *
    * @param type the type of Terrain to be built
    * @param x the x-coordinate this Terrain will be located at
    * @param y the y-coordinate this Terrain will be located at
    * @return the constructed Terrain or defaultTerrain if
    *    the given type cannot be found
    */
    public Terrain build(String type, int x, int y)
    {
        //Create a new Terrain ter
        Terrain ter;
        //IF type is in terrainMap
        if (terrainMap.containsKey(type))
        {
            //Get the template for the given type
            Terrain temp = terrainMap.get(type);
            //Set ter to a new terrain using the parameters set by the archtype
            //with the exception of the x,y location in the parameters
            ter = new Terrain(type, temp.getDefMod(), temp.getMoveCost(), x, y);
        }
        //ELSE
        else
        {
            //Set ter to a default terrain
            ter = new DefaultTerrain(x, y);
            //END IF
        }
        //Return ter
        return ter;
    }
    
    /**
    * Given a name of a file to look in, gets the data about a particular Terrain
    * and adds it to the terrainMap.
    * @pre file with name fileName exists, and is in the proper directory
    * @param fileName the name of the file from which to read the Terrain data
    */
    private void terrainFromFile(String fileName)
    {
        //Create a new Properties variable props
        Properties props = new Properties();
        //Try
        try
        {
            //Extract the terrain properties from the .properties file into props
            props.load(new FileInputStream(fileName));
            //Create a terrain using the attributes specified in props
            Terrain archetype = new Terrain(props.getProperty("type"),
                Integer.valueOf(props.getProperty("defMod")),
                Integer.valueOf(props.getProperty("moveCost")),
                //Fixes defect #136: don't check properties files for coordinates
                -1,
                -1);
            //Add the Terrain to the hash map
            terrainMap.put(archetype.getType(), archetype);
        }
        //Catch IOException
        catch (IOException err)
        {
            err.printStackTrace();
        }
    }
}
