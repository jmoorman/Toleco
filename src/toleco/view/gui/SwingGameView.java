package toleco.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import toleco.view.I_GameView;
import toleco.logic.GameBoard;
import toleco.unit.Unit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import toleco.controller.Player;
import toleco.controller.PlayerAction;
import toleco.terrain.Terrain;


/**
* A SwingGameView is the main controller for the GUI.  The SwingGameView
* maintains the MapView, the StatusView, and the GameButtonView. The HashMap
* is created using unit names from the UnitFactory and with the images loaded
* with the same name as the units.
*
* This is the view in the MVC architecture.
*
* @author Adam Armstrong (Javadocs)
* @author Eriq Augustine (Implementation)
* @version 1.0
*/
public class SwingGameView extends Observable implements I_GameView
{
    /**
    * The folder that all unit images are to be held in.
    */
    private static final String kUnitImagePath = new String("images/units/");
    
    /**
    * The folder that all terrain images are to be held in.
    */
    private static final String kTerrainImagePath = 
        new String("images/terrain/");
    
    /**
    * The path to the overlay image for the first player.
    */
    private static final String kPlayerOneOverlayPath =
        new String("images/OverlayPlayer1.gif");
    
    /**
    * The path to the overlay image for the first player.
    */
    private static final String kPlayerTwoOverlayPath =
        new String("images/OverlayPlayer2.gif");
    
    /**
    * The path to the image for the default Unit.
    */
    private static final String kDefaultImagePath = 
        new String("images/Default.gif");
    
    /**
    * The root of the folder that all images are to be held in.
    */
    private static final String kImagePath = new String("images/");
    
    /**
    * The extension for all images.
    */
    private static final String kImageExtension = new String(".gif");
    
    /**
    * The color of the background for when the first player is active.
    */
    private static final Color kPlayerOneColor = new Color(66, 173, 66);
    
    /**
    * The color of the background for when the second player is active.
    */
    private static final Color kPlayerTwoColor = new Color(199, 21, 133);
    
    /**
    * The image representing a selected location.
    */
    private static final String kSelectImage = new String("images/Select.gif");
    
    /**
    * The image representing a crush attack type.
    */
    private static final String kCrushImage = new String("images/Crush.gif");
    
    /**
    * The image representing a crush attack type.
    */
    private static final String kPierceImage = new String("images/Pierce.gif");
    
    /**
    * The image representing a maul attack type.
    */
    private static final String kMaulImage = new String("images/Maul.gif");
    
    /**
    * The image representing a hide armor type.
    */
    private static final String kHideImage = new String("images/Hide.gif");
    
    /**
    * The image representing a padded armor type.
    */
    private static final String kPaddedImage = new String("images/Padded.gif");
    
    /**
    * The image representing a bone armor type.
    */
    private static final String kBoneImage = new String("images/Bone.gif");
    
    /**
    * The image representing a highlighted location.
    */
    private static final String kHighlightImage = 
        new String("images/Highlight.gif");
    
    /**
     * The width of the map area (in pixels).
     */
    private static final int kMapWidth = 610;

    /**
     * The height of the map area (in pixels).
     */
    private static final int kMapHeight = 415;

    /**
     * The width of the status area (in pixels).
     */
    private static final int kStatusWidth = 200;

    /**
    * Holds the game images used by the GUI.
    */
    private MyHash images;
    
    /**
    * A JPanel representing the SwingGameView.
    */
    private JPanel panel;
    
    /**
    * A JPanel representing the SwingGameView.
    */
    private JPanel innerPanel;
    
    /**
    * Board holds the the game logic.
    */
    private GameBoard board;
    
    /**
    * Buttons is the game buttons the GUI will display.
    */
    private GameButtonView buttons;
    
    /**
    * Map is the game map the GUI will display.
    */
    private MapView map;
    
    /**
    * Status is the game status the GUI will display.
    */
    private StatusView status;
    
    /**
     * Creates new form SwingGameView.
     * @param board the GameBoard to use
     */
    public SwingGameView(GameBoard board)
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
        
        //Initialize status to be a new StatusView.
        status = new StatusView();
        
        //CALL SatusView.init with the image hashmap.
        status.init(images);
        
        //Initialize buttons to be a new GameButtonView.
        buttons = new GameButtonView();
        
        //CALL buttons.setGameView with this.
        buttons.setGameView(this);
        
        //Initialize panel to be a new JPanel.
        panel = new JPanel();
        
        //Initialize innerPanel to be a new JPanel.
        innerPanel = new JPanel();
        
        //CALL initComponents.
        initComponents();
        
        //CALL setupKeys.
        setupKeys();
    }
    
    /**
    * Creates new form SwingGameView.
    *
    * @param board the GameBoard to use
    * @param map the MapView to use
    * @param status the StatusView to use
    * @param buttons the GameButtonView to use
    */
    public SwingGameView(GameBoard board, MapView map, StatusView status,
    GameButtonView buttons)
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
        this.status = status;
        
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
    * Initialize all GUI components and place them properly.
    */
    private void initComponents()
    {
        //Set panel's maximum size to kMapWidth x kMapHeight.
        panel.setMaximumSize(new java.awt.Dimension(kMapWidth, kMapHeight));
        //Set panel's minimum size to kMapWidth x kMapHeight.
        panel.setMinimumSize(new java.awt.Dimension(kMapWidth, kMapHeight));
        //Set panel's prefered size to kMapWidth x kMapHeight.
        panel.setPreferredSize(new java.awt.Dimension(kMapWidth, kMapHeight));
        
        //Create a new BoxLayout within panel using BoxLayout.X_AXIS.
        BoxLayout layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        
        //Set panel's layout to be the newly created layout.
        panel.setLayout(layout);
        
        //Add map to panel.
        panel.add(map);
        
        //Set innerPanel's maximum size to kStatusWidth x kMapHeight.
        innerPanel.setMaximumSize(new java.awt.Dimension(kStatusWidth, kMapHeight));
        //Set innerPanel's minimum size to kStatusWidth x kMapHeight.
        innerPanel.setMinimumSize(new java.awt.Dimension(kStatusWidth, kMapHeight));
        //Set innerPanel's prefered size to kStatusWidth x kMapHeight.
        innerPanel.setPreferredSize(new java.awt.Dimension(kStatusWidth, kMapHeight));
        
        //Create a new BoxLayout within innerPanel using BoxLayout.Y_AXIS.
        BoxLayout innerLayout = new BoxLayout(innerPanel, BoxLayout.Y_AXIS);
        
        //Set panel's layout to be the newly created layout.
        innerPanel.setLayout(innerLayout);
        
        //Switch status to backStory mode with an empty String.
        status.switchToBackStory("");
        
        //Set status to have a Y-allignment of Component.TOP_ALIGNMENT
        status.setAlignmentY(Component.TOP_ALIGNMENT);

        //Set buttons to have a Y-allignment of Component.BOTTOM_ALIGNMENT
        buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        
        //Add status to innerPanel.
        innerPanel.add(status);
        //Add buttons to innerPanel.
        innerPanel.add(buttons);
        
        //Set buttons to visible.
        buttons.setVisible(true);
        //Set status to visible.
        status.setVisible(true);
        
        //Add innerPanel to panel.
        panel.add(innerPanel);
        
        //Set panel to visible.
        panel.setVisible(true);
        //Set innerPanel to visible.
        innerPanel.setVisible(true);
        
        //Set map to visible.
        map.setVisible(true);
    }
    
    /**
    * Sets the GameBoard.
    *
    * @param board the Gameboard to use
    */
    public void setBoard(GameBoard board)
    {
        //Set this.board to be the passed in board.
        this.board = board;
        
        //CALL loadImages.
        loadImages();
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
                BufferedImage temp = ImageIO.read(new File(unitImage));
                
                //Add the new BufferedImage to the hash of images using the unit's
                // name as the key and image as the value.
                images.put(unit, temp);
            }
            //CATCH IOException
            catch (Exception ex)
            {
                //Print an error about being unable to locate the image file.
                System.err.println("Unable to locate image file for '" + unit +
                    "' unit.");
            }
            //END
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
        
        //FOR all terrain names.
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
                System.err.println("Unable to locate image file for '" +
                    tName + "' terrian.");
            }
            //END
        }
    }
    
    /**
    * Load the selection images into images
    */
    private void loadSelectImages()
    {
        //TRY
        try
        {
            //Using ImageIO.read, get a buffered image from the select image path.
            BufferedImage temp = ImageIO.read(new File(kSelectImage));
            
            //Add the new BufferedImage to the hash of images using "select"
            // as the key and image as the value.
            images.put("Select", temp);
            
            //Using ImageIO.read, get a buffered image from the highlight image path.
            temp = ImageIO.read(new File(kHighlightImage));
            
            //Add the new BufferedImage to the hash of images using "highlight"
            // as the key and image as the value.
            images.put("Highlight", temp);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about being unable to locate the image file.
            System.err.println("Unable to locate select or highlight image files.");
        }
        //END
    }
    
    /**
    * Loads the overlay images into images
    */
    private void loadOverlayImages()
    {
        //TRY
        try
        {
            //Using ImageIO.read, get a buffered image from the
            // player one overlay image path.
            BufferedImage temp =
                ImageIO.read(new File(kPlayerOneOverlayPath));
            
            //Add the new BufferedImage to the hash of images using "OverlayPlayer1"
            // as the key and image as the value.
            images.put("OverlayPlayer1", temp);
            
            //Using ImageIO.read, get a buffered image from the player
            // two overlay image path.
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
        //END
    }
    
    /**
    * Loads the attack type images into images
    */
    private void loadAttackTypeImages()
    {
        //TRY
        try
        {
            //Using ImageIO.read, get a buffered image from the Crush image path.
            BufferedImage temp = ImageIO.read(new File(kCrushImage));
            
            //Add the new BufferedImage to the hash of images using Crush
            // as the key and image as the value.
            images.put("kCrush", temp);
            
            //Using ImageIO.read, get a buffered image from the Pierce image path.
            temp = ImageIO.read(new File(kPierceImage));
            
            //Add the new BufferedImage to the hash of images using Pierce
            // as the key and image as the value.
            images.put("kPierce", temp);
            
            //Using ImageIO.read, get a buffered image from the Maul image path.
            temp = ImageIO.read(new File(kMaulImage));
            
            //Add the new BufferedImage to the hash of images using Maul
            // as the key and image as the value.
            images.put("kMaul", temp);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about being unable to locate the image file.
            System.err.println("Unable to locate attack type image files.");
        }
        //END
    }
    
    /**
    * Loads the armor type images into images
    */
    private void loadArmorTypeImages()
    {
        //TRY
        try
        {
            //Using ImageIO.read, get a buffered image from the Hide image path.
            BufferedImage temp = ImageIO.read(new File(kHideImage));
            
            //Add the new BufferedImage to the hash of images using Hide
            // as the key and image as the value.
            images.put("kHide", temp);
            
            //Using ImageIO.read, get a buffered image from the Padded image path.
            temp = ImageIO.read(new File(kPaddedImage));
            
            //Add the new BufferedImage to the hash of images using Padded
            // as the key and image as the value.
            images.put("kPadded", temp);
            
            //Using ImageIO.read, get a buffered image from the Bone image path.
            temp = ImageIO.read(new File(kBoneImage));
            
            //Add the new BufferedImage to the hash of images using Bone
            // as the key and image as the value.
            images.put("kBone", temp);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about being unable to locate the image file.
            System.err.println("Unable to locate armor type image files.");
        }
        //END
    }
    
    
    /**
    * @pre board is not null
    */
    private void loadImages()
    {
        //CALL loadDefaultImage
        loadDefaultImage();
        
        //CALL loadUnitImages.
        loadUnitImages();
        
        //CALL loadTerrainImages.
        loadTerrainImages();
        
        //CALL loadSelectImages.
        loadSelectImages();
        
        //CALL loadAttackTypeImages
        loadAttackTypeImages();
        
        //CALL loadArmorTypeImages
        loadArmorTypeImages();
        
        //Call loadOverlayImages
        loadOverlayImages();
    }
    
    /**
     * {@inheritDoc}
     * @pre board is not null
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
    * {@inheritDoc}
    *
    * @pre board is not null
    * @pre map is not null
    * @pre buttons is not null
    */
    public void update(Observable obs, Object obj)
    {
        //IF obj is an ArrayList
        if (obj instanceof ArrayList)
        {
            //Create a new ArrayList<Point>.
            ArrayList<Point> enemies = new ArrayList<Point>();

            //Cast obj to an ArrayList (note that this is not a generic ArrayList).
            ArrayList temp = ((ArrayList)(obj));

            //FOR all elements in temp.
            for (Object element : temp)
            {
                //Cast that element to a Point and add it to enemies.
                enemies.add(((Point)(element)));
            }
            //ENDFOR
            
            //CALL MapView.addHighlights with the ArrayList<Point>
            map.addHighlights(enemies);
        }
        //ELSEIF obj is a Player
        else if (obj instanceof Player)
        {
            //Switch the StatusGameView to display the backstory
            status.switchToBackStory(board.getBackStory());
            
            //Cast obj to a Player
            Player newPlayer = (Player)(obj);
            
            //Call switchPlayer
            switchPlayer(newPlayer);

            //Fixes defect #222
            //CALL map.deselectLocation
            map.deselectLocation();
        }
        //END
        
        //IF the board has a Terrain selected which has a Unit that can attack.
        if(((GameBoard)obs).getSelection() != null
            && ((GameBoard)obs).getSelection().getUnit() != null
            && ((GameBoard)obs).getSelection().getUnit().canAttack())
        {
            //CALL buttons.switchToEnabledAttack.
            buttons.switchToEnabledAttack();
        }
        //ELSE
        else
        {
            //CALL buttons.switchToDisabledAttack.
            buttons.switchToDisabledAttack();
        }
        //ENDIF
        
        //CALL drawMap on the the MapView
        map.drawMap(board.getMap());
        
        //IF the board has a selected Terrain.
        if (board.getSelection() != null)
        {
            //CALL map.selectLocation with the coordinates of the selected Terrain.
            map.selectLocation(board.getSelection().getX(), board.getSelection().getY());
        }
        //ENDIF
    }
    
    /**
    * {@inheritDoc}
    * @pre status is not null
    */
    public void displayBattleSummary(Unit attacker, Unit defender, String summary)
    {
        //GET image for attacker
        BufferedImage attackerImg = images.get(attacker.getType());
        
        //GET image for defender
        BufferedImage defenderImg = images.get(defender.getType());
        
        //CALL switchToBattleSummary
        status.switchToBattleSummary(attackerImg, defenderImg, summary);
    }
    
    /**
    * {@inheritDoc}
    * @pre map is not null
    */
    public void removeHighlights()
    {
        //Call MapView.removeHighlights().
        map.removeHighLights();
    }
    
    /**
    * {@inheritDoc}
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
    * @pre board has a terrain selected.
    */
    public void displayTerrainSelected(Player currentPlayer)
    {
        //CALL tatus.switchToTerrainSelected with the selection from board.
        status.switchToTerrainSelected(board.getSelection());
        
        //IF the selected Terrain has a Unit owned by the passed in player.
        if (board.getSelection().getUnit() != null &&
            board.getSelection().getUnit().getOwner() == currentPlayer &&
            //Fixes Defect #194.
            board.getSelection().getUnit().canAttack())
        {
            //CALL buttons.switchToEnabledAttack.
            buttons.switchToEnabledAttack();
        }
        //ELSE
        else
        {
            //CALL buttons.switchToDisabledAttack.
            buttons.switchToDisabledAttack();
        }
        //ENDIF
    }
    
    /**
    * {@inheritDoc}
    */
    public JPanel getPanel()
    {
        //Return panel.
        return panel;
    }
    
    /**
    * Switch any graphical representation of the the currently active Player.
    *
    * @param newPlayer the currently active Player
    */
    private void switchPlayer(Player newPlayer)
    {
        //If the passed in Player if the first player.
        if (newPlayer == Player.kPlayer1)
        {
            //CALL buttons.setBackground with the color of the first palyer
            // (kPlayerOneColor)
            buttons.setBackground(kPlayerOneColor);
        }
        //ELSE If the passed in Player if the first player.
        else if (newPlayer == Player.kPlayer2)
        {
            //CALL buttons.setBackground with the color of the second palyer
            // (kPlayerTwoColor)
            buttons.setBackground(kPlayerTwoColor);
        }
        //END
    }
    
    /**
    * {@inheritDoc}
    */
    public void displayBackStory(String story)
    {
        //CALL status.switchToBackStory with the given String.
        status.switchToBackStory(story);
    }

    /**
     * {@inheritDoc}
     */
    public void displayGameOver(String winner)
    {
        //Compose a message to the winner.
        String winMessage = "Congratulation " + winner + " you have overcome " +
            "incredible odds and emerged victorious in the field of battle!";

        //DISPLAY about info dialog box
        JOptionPane.showMessageDialog(panel, winMessage, "Congratulation!", 1);
    }
    
    /**
    * Setup the keys for the game.
    */
    private void setupKeys()
    {
        //Create a new AbstractAction called upAction.
        Action upAction = new AbstractAction()
        {
            //Override actionPerformed in the newly created AbstractAction.
            public void actionPerformed(ActionEvent e)
            {
                //Call setChanged.
                setChanged();
                
                //Call notifyObservers with PlayerAction.kMoveUp.toString().
                notifyObservers(PlayerAction.kMoveUp.toString());
            }
        };
        
        //Create a new AbstractAction called downAction.
        Action downAction = new AbstractAction()
        {
            //Override actionPerformed in the newly created AbstractAction.
            public void actionPerformed(ActionEvent e)
            {
                //Call setChanged.
                setChanged();
                
                //Call notifyObservers with PlayerAction.kMoveDown.toString().
                notifyObservers(PlayerAction.kMoveDown.toString());
            }
        };
        
        //Create a new AbstractAction called leftAction.
        Action leftAction = new AbstractAction()
        {
            //Override actionPerformed in the newly created AbstractAction.
            public void actionPerformed(ActionEvent e)
            {
                //Call setChanged.
                setChanged();
                
                //Call notifyObservers with PlayerAction.kMoveLeft.toString().
                notifyObservers(PlayerAction.kMoveLeft.toString());
            }
        };
        
        //Create a new AbstractAction called rightAction.
        Action rightAction = new AbstractAction()
        {
            //Override actionPerformed in the newly created AbstractAction.
            public void actionPerformed(ActionEvent e)
            {
                //Call setChanged.
                setChanged();
                
                //Call notifyObservers with PlayerAction.kMoveRight.toString().
                notifyObservers(PlayerAction.kMoveRight.toString());
            }
        };
        
        //Put into panel's WHEN_IN_FOCUSED_WINDOW InputMap the KeyStroke
        // "released UP" and the String "upAction"
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("released UP"), "upAction");
        
        //Put into panel's ActionMap the String "upAction" and the
        // AbstractAction upAction.
        panel.getActionMap().put("upAction", upAction);
        
        //Put into panel's WHEN_IN_FOCUSED_WINDOW InputMap the KeyStroke
        // "released DOWN" and the String "downAction"
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("released DOWN"), "downAction");
        
        //Put into panel's ActionMap the String "downAction" and
        // the AbstractAction downAction.
        panel.getActionMap().put("downAction", downAction);
        
        //Put into panel's WHEN_IN_FOCUSED_WINDOW InputMap the
        // KeyStroke "released LEFT" and the String "leftAction"
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("released LEFT"), "leftAction");
        
        //Put into panel's ActionMap the String "leftAction"
        // and the AbstractAction leftAction.
        panel.getActionMap().put("leftAction", leftAction);
        
        //Put into panel's WHEN_IN_FOCUSED_WINDOW InputMap the
        // KeyStroke "released RIGHT" and the String "rightAction"
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("released RIGHT"), "rightAction");
        
        //Put into panel's ActionMap the String "rightAction"
        // and the AbstractAction rightAction.
        panel.getActionMap().put("rightAction", rightAction);
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