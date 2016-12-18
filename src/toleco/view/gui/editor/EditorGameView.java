package toleco.view.gui.editor;

import java.awt.Component;
import toleco.logic.GameBoard;
import toleco.unit.Unit;
import toleco.view.gui.MapView;
import toleco.view.I_GameView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import toleco.controller.Player;
import toleco.terrain.Terrain;

/**
* An EditorGameView represents the GUI of the map editor. The map editor allows
* user to build and customize their own maps for a game of Toleco. The GUI
* is composed of three main components: a map to be edited, a tool pane for
* selecting which terrain or unit to place on the map, and a set of buttons for
* saving, loading, and quitting.
*
* @author Matt Tognetti
* @author Jon Moorman
* @version 1.0
*/
public class EditorGameView extends Observable implements I_GameView
{
    
    /**
     * The folder that all unit images are to be held in.
     */
    private static final String kUnitImagePath = "images/units/";

    /**
     * The folder that all terrain images are to be held in.
     */
    private static final String kTerrainImagePath = "images/terrain/";

    /**
     * The folder that all terrain images are to be held in.
     */
    private static final String kPlayerOneOverlayPath = "images/OverlayPlayer1.gif";

    /**
     * The folder that all terrain images are to be held in.
     */
    private static final String kPlayerTwoOverlayPath = "images/OverlayPlayer2.gif";

    /**
     * The root of the folder that all images are to be held in.
     */
    private static final String kImagePath = "images/";

    /**
    * The path to the image for the default Unit.
    */
    private static final String kDefaultImagePath = "images/Default.gif";

    /**
     * The extension for all images.
     */
    private static final String kImageExtension = ".gif";

    /**
    * A collection of all images used within the map editor. The key to an
    * image is the name of the unit/terrain/selector it represents.
    */
    private MyHash images;
    
    /**
    * The playing area that units and terrain occupy.
    * This is the Model of the MVC architecture
    */
    private GameBoard board;
    
    /**
    * The buttons for saving a map, loading a map, and quitting the editor.
    */
    private EditorButtonView buttons;
    
    /**
    * The tools for selecting which unit or terrain type to place on the
    * map.
    */
    private ToolView tools;
    
    /**
    * The game area where units and terrain are placed to create a custom map.
    */
    private MapView map;

    /**
     * A JPanel representing the SwingGameView.
     */
    private JPanel panel;
     
    /**
     * An inner JPanel representing the SwingGameView.
     */
    private JPanel innerPanel;

    //Fixed defect #197.
    /***
     * Constructs a new EditorGameView
     * @param board the GameBoard being used to store the map data
     */
    public EditorGameView(GameBoard board)
    {
        //Set board to the passed in GameBoard.
        this.board = board;


        //Initialize images to be an empty HashMap<String, BufferedImage>.
        images = new MyHash();

        //CALL loadImages.
        loadImages();

        //Initialize map to a new MapView.
        map = new MapView(images);

        //CALL map.setGameView with this.
        map.setGameView(this);

        //Initialize tools to be a new ToolView.
        tools = new ToolView();

        //Call ToolView.init with the list of Unit names, Terrain names, and
        // images.
        tools.init(board.getTerrainNames(), board.getUnitNames(), images);

        //Call tools.setGameView with this.
        tools.setGameView(this);

        //Initialize buttons to be a new GameButtonView.
        buttons = new EditorButtonView();

        //CALL buttons.setGameView with this.
        buttons.setGameView(this);

        //Initialize panel to be a new JPanel.
        panel = new JPanel();

        //Initialize innerPanel to be a new JPanel.
        innerPanel = new JPanel();

        //CALL initComponents.
        initComponents();
    }

    /**
     * Constructor, stores references to needed values and inits GUI components
     * @param board the current GameBoard
     * @param map the GUI representation of the map
     * @param tools toolView for selecting unit and terrain to place
     * @param buttons GUI component that holds the editor buttons
     */
    public EditorGameView(GameBoard board, MapView map, ToolView tools,
        EditorButtonView buttons)
    {
        //Initialize images to be an empty HashMap<String, BufferedImage>.
        images = new MyHash();

        //Set this.board to the passed in GameBoard.
        this.board = board;

        //Set this.map to the passed in MapView.
        this.map = map;

        //CALL map.setGameView with this.
        map.setGameView(this);

        //Set this.status to the passed in StatusView.
        this.tools = tools;

        //Set this.buttons to the passed in GameButtonView.
        this.buttons = buttons;

        //CALL buttons.setGameView with this.
        buttons.setGameView(this);

        //Initialize panel to be a new JPanel.
        panel = new JPanel();

        //Initialize innerPanel to be a new JPanel.
        innerPanel = new JPanel();

        //Call initComponents.
        initComponents();

        //CALL loadImages
        loadImages();
    }

    /**
    * {@inheritDoc}
    */
    public void drawMap()
    {
        //GET terrain map from game board
        Terrain[][] temp = board.getMap();

        //CALL drawMap on the map
        map.drawMap(temp);
    }
    
    /**
    * {@inheritDoc}
    */
    public BufferedImage getImage(String name)
    {
        //GET and RETURN image from list of images
        return images.get(name);
    }

    /**
     * Returns the entire map of names to images used in the application
     * @return the map of names to images
     */
    public HashMap<String, BufferedImage> getImages()
    {
        return images;
    }
    
    /**
    * {@inheritDoc}
    */
    public void update(Observable obs, Object obj)
    {
        //CALL drawMap
        map.drawMap(board.getMap());
    }
    
    /**
    * Not used in editor mode.
    *
    * @param attacker not used
    * @param defender not used
    * @param summary not used
    */
    public void displayBattleSummary(Unit attacker, Unit defender, String summary)
    {
        //Not implemented
    }

    /**
    * {@inheritdoc}
    */
    public void removeHighlights()
    {
        //Not implemented
    }

    /**
     * Returns the JPanel that represents the SwingGameView.
     *
     * @return the JPanel that represents the SwingGameView
     */
    public JPanel getPanel()
    {
        //RETURN panel
        return panel;
    }

        /**
     * Accept a specially formatted String that will used in an update.
     *
     * @param action the String to pass in the update.
     */
    public void acceptAction(String action)
    {
        //Call setChanged();
        setChanged();

        //Call notifyObservers with the passed in String.
        notifyObservers(action);
    }

    /**
     * {@inheritDoc}
     */
    public void displayBackStory(String story)
    {
        //Not Supported
    }

    /**
     * {@inheritDoc}
     */
    public void displayTerrainSelected(Player currentPLayer)
    {
        //Not Supported
    }

    /**
     * @pre board is not null
     */
    private void loadImages()
    {
        //CALL loadDefaultImage.
        loadDefaultImage();
                
        //CALL loadUnitImages.
        loadUnitImages();

        //CALL loadTerrainImages.
        loadTerrainImages();

        //Call loadOverlayImages
        loadOverlayImages();
    }

    /**
    * Stores all default images into images
    */
    private void loadDefaultImage()
    {
        //TRY
        try
        {
            //Load the image at kdefaultImagePath into a BufferedImage.
            BufferedImage temp = ImageIO.read(new File(kDefaultImagePath));

            //Call images.setDefaultImage with temp.
            images.setDefaultImage(temp);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about being unable to locate the image file.
            System.err.println("Unable to load the default image.");
        }
        //END
    }

    //CHECKSTYLE:OFF - Ignore generated code.
    // Authorized by Dr. Dalbey.

    private void initComponents() {
        panel.setMaximumSize(new java.awt.Dimension(610, 415));
        panel.setMinimumSize(new java.awt.Dimension(610, 415));
        panel.setPreferredSize(new java.awt.Dimension(610, 415));

        BoxLayout layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layout);

        panel.add(map);

        innerPanel.setMaximumSize(new java.awt.Dimension(200, 415));
        innerPanel.setMinimumSize(new java.awt.Dimension(200, 415));
        innerPanel.setPreferredSize(new java.awt.Dimension(200, 415));

        BoxLayout innerLayout = new BoxLayout(innerPanel, BoxLayout.Y_AXIS);
        innerPanel.setLayout(innerLayout);

        tools.setAlignmentY(Component.TOP_ALIGNMENT);
        buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        innerPanel.add(tools);
        innerPanel.add(buttons);

        buttons.setVisible(true);
        tools.setVisible(true);
        panel.add(innerPanel);

        panel.setVisible(true);
        innerPanel.setVisible(true);

        map.setVisible(true);
    }

    //CHECKSTYLE:ON

    /**
     * Stores all unit images into images
     */
    private void loadUnitImages()
    {
        //Get an ArrayList of Unit names from the new board.
        ArrayList<String> unitList = board.getUnitNames();
        //FOR all Unit names
        for(String unit : unitList)
        {
            //Concat the image path, unit name, and image extension.
            String unitImage = kUnitImagePath + unit + kImageExtension;

            //TRY
            try
            {
                //Using ImageIO.read, get a buffered image from the image path.
                //System.err.println(new File("/build.xml"));
                BufferedImage temp = ImageIO.read(new File(unitImage));

                //Add the new BufferedImage to the hash of images using the unit's
                // name as the key and image as the value.
                images.put(unit, temp);
            }
            //CATCH IOException
            catch (Exception ex)
            {
                //Print an error about being unable to locate the image file.
                System.err.println("Unable to locate image file for " + unit +
                        " unit.");
            }
        }
        //END FOR
    }

    /**
     * Store all location images in images
     */
    private void loadTerrainImages()
    {
        //Get an ArrayList of Terrain names from the new board.
        ArrayList<String> terrains = board.getTerrainNames();
        //FOR all Terrain names.
        for (String tName : terrains)
        {
            //Concat the image path, terrain name, and image extension.
            String terrainImage = kTerrainImagePath + tName + kImageExtension;

            //TRY
            try
            {
                //Using ImageIO.read, get a buffered image from the image path.
                BufferedImage temp = ImageIO.read(new File(terrainImage));

                //Add the new BufferedImage to the hash of images using the terrain's
                // name as the key and image as the value.
                images.put(tName, temp);
            }
            //CATCH IOException
            catch (Exception ex)
            {
                //Print an error about being unable to locate the image file.
                System.err.println("Unable to locate image file for " +
                        tName + " terrian.");
            }
        }
    }
    /**
     * Loads the overlay images into images
     */
    private void loadOverlayImages()
    {
        //TRY
        try
        {
            //Using ImageIO.read, get a buffered image from the player one
            //overlay image path.
            BufferedImage temp = ImageIO.read(new File(kPlayerOneOverlayPath));

            //Add the new BufferedImage to the hash of images using "OverlayPlayer1"
            // as the key and image as the value.
            images.put("OverlayPlayer1", temp);

            //Using ImageIO.read, get a buffered image from the player two
            //overlay image path.
            temp = ImageIO.read(new File(kPlayerTwoOverlayPath));

            //Add the new BufferedImage to the hash of images using "OverlayPlayer2"
            // as the key and image as the value.
            images.put("OverlayPlayer2", temp);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about being unable to locate the image file.
            System.err.println("Unable to locate overlay image files.");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void displayGameOver(String winner)
    {
        //Not supported.
    }

    /**
    * A private class to provide extra functionality to a HashMap.
    * A MyHash provides all teh same functionality as a HashMap except that
    * if a key is not found, than get() returns a default value set on construction.
    */
    private class MyHash extends HashMap<String, BufferedImage>
    {
        /**
        * A default value to return if a key is not found.
        */
        private BufferedImage defaultImage;

        /**
        * Constructs a new MyHash using null as the 'default image'.
        */
        public MyHash()
        {
            //CALL super
            super();

            //Set defaultImage to be the passed in BufferedImage.
            this.defaultImage = null;
        }

        /**
        * Sets the default image to be the passed in BufferedImage.
        *
        * @param img the new default image.
        */
        public void setDefaultImage(BufferedImage img)
        {
            //Set defaultImage to be the passed in BufferedImage.
            defaultImage = img;
        }

        /**
        * Returns the value repesented by key in the hashmap.
        *
        * @param key the key linked to the value being searched for
        * @return the BufferedImage reresented by key, or a default image if the key was
        * not found.
        */
        @Override
        public BufferedImage get(Object key)
        {
            //Create a new BufferedImage and give it the result
            // of a call to super.get with the given key.
            BufferedImage rtn = super.get((String)key);

            //IF the newly created BufferedImage is null.
            if (rtn == null)
            {
                //Set the BufferedImage to the defaultImage.
                rtn = defaultImage;
            }
            //ENDIF

            //RETURN the created BufferedImage.
            return rtn;
        }
    }
}
