package toleco.view.gui;


import toleco.terrain.Terrain;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.OverlayLayout;
import toleco.unit.Unit;
/**
* A StatusViewManual is responsible for creating and modifying the game's status
* panel. StatusViewManual is used by the SwingGameView class.
*
* @author Adam Armstrong - Javadocs
* @author Matt Tognetti - Implementation
* @version 1.0
*/
public class StatusView extends javax.swing.JPanel
{
    /**
    * The graphical component which displays the information about a Unit and
    * the Terrain it is on.
    */
    private JComponent unitSelected;
    
    /**
    * The graphical component which displays the information about a Terrain.
    */
    private JComponent terrainSelected;
    
    /**
    * The graphical component which displays the back story.
    */
    private JComponent backStory;
    
    /**
    * The graphical component which displays the battle summary.
    */
    private JComponent battleSummary;

    /**
     * A collection of all images used in Toleco.
     */
    private HashMap<String, BufferedImage> images;
    
    /** 
     * Creates new form StatusView.
     */
    public StatusView()
    {
        initComponents();

        //CALL setLayout with a new OverlayLayout
        this.setLayout(new OverlayLayout(this));

        //CALL initPanels
        initPanels();
    }

    /**
     * Stores a reference to all images needed by the StatusView.
     * @param imgs a collection of images including unit, terrain, and attack
     * types
     */
    public void init(HashMap<String, BufferedImage> imgs)
    {
        //STORE image collection
        images = imgs;
    }

    /**
     * Initialize all four pannels.
     */
    private void initPanels()
    {
        //INIT unitSelected
        unitSelected = new UnitSelected();
        //INIT terrainSelected
        terrainSelected = new TerrainSelected();
        //INIT battleSummary
        battleSummary = new BattleSummary();
        //INIT backStory
        backStory = new BackStory();

        //add unitSelected to this panel
        this.add(unitSelected);
        //add terrainSelected to this panel
        this.add(terrainSelected);
        //add batleSummary to this panel
        this.add(battleSummary);
        //add backStory to this panel
        this.add(backStory);

        //SET backStory to not enabled
        backStory.setEnabled(false);
        //SET backStory to not visible
        backStory.setVisible(false);

        //SET battleSummary to not enabled
        battleSummary.setEnabled(false);
        //SET battleSummary to not visible
        battleSummary.setVisible(false);

        //SET terrainSelected to not enabled
        terrainSelected.setEnabled(false);
        //SET terrainSelected to not visible
        terrainSelected.setVisible(false);

        //SET unitSelected to not enabled
        unitSelected.setEnabled(false);
        //SET unitSelected to not visible
        unitSelected.setVisible(false);
    }
    
    /**
    * Switch to the battle summary status. This is displayed after an attack,
    * and displays the information about the attack.
    *
    * @param attacker the image of the attacking unit
    * @param defender the image of the defending unit
    * @param summary the summary of what took place in the battle
    */
    public void switchToBattleSummary(BufferedImage attacker,
    BufferedImage defender, String summary)
    {
        //UPDATE battleSummary with correct text and images
        ((BattleSummary)battleSummary).setSummary(attacker, defender, summary);
        
        //SET backStory to not enabled
        backStory.setEnabled(false);
        //SET backStory to not visible
        backStory.setVisible(false);
        
        //SET battleSummary to enabled
        battleSummary.setEnabled(true);
        //SET battleSummary to visible
        battleSummary.setVisible(true);
        
        //SET terrainSelected to not enabled
        terrainSelected.setEnabled(false);
        //SET terrainSelected to not visible
        terrainSelected.setVisible(false);
        
        //SET unitSelected to not enabled
        unitSelected.setEnabled(false);
        //SET unitSelected to not visible
        unitSelected.setVisible(false);
        
        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Switch to the back story status. This is displayed when a map is loaded,
    * and whenever a player's turn ends.
    *
    * @param backstory the back story of the game
    */
    public void switchToBackStory(String backstory)
    {
        //UPDATE backStory with backstory text
        ((BackStory)backStory).updateText(backstory);

        //SET backStory to enabled
        backStory.setEnabled(true);
        //SET backStory to visible
        backStory.setVisible(true);

        //SET battleSummary to not enabled
        battleSummary.setEnabled(false);
        //SET battleSummary to not visible
        battleSummary.setVisible(false);

        //SET terrainSelected to not enabled
        terrainSelected.setEnabled(false);
        //SET terrainSelected to not visible
        terrainSelected.setVisible(false);

        //SET unitSelected to not enabled
        unitSelected.setEnabled(false);
        //SET unitSelected to not visible
        unitSelected.setVisible(false);

        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Switch to the terrain selected status. This is displayed whenever a cell
    * on the map is selected.
    *
    * @param terrain the selected terrain to display in the status
    */
    public void switchToTerrainSelected(Terrain terrain)
    {
        //SET backStory to not enabled
        backStory.setEnabled(false);
        //SET backStory to not visible
        backStory.setVisible(false);
        
        //SET battleSummary to not enabled
        battleSummary.setEnabled(false);
        //SET battleSummary to not visible
        battleSummary.setVisible(false);

        //GET terrain image
        BufferedImage terrainImage = images.get(terrain.getType());

        //IF terrain contains a Unit THEN
        if ( terrain.getUnit() != null )
        {
            //GET unit
            Unit unit = terrain.getUnit();
            //GET unit image
            BufferedImage unitImage = images.get(unit.getType());
            //GET attack type image
            BufferedImage attackImage =
                    images.get(unit.getAttackType().toString());
            //GET defense type image
            BufferedImage defenseImage =
                    images.get(unit.getArmorType().toString());
            //UPDATE unitSelected with terrain
            ((UnitSelected)unitSelected).updateInfo(terrain,
                    terrainImage, unitImage, attackImage, defenseImage);
            //SET terrainSelected to not enabled
            terrainSelected.setEnabled(false);
            //SET terrainSelected to not visible
            terrainSelected.setVisible(false);

            //SET unitSelected to enabled
            unitSelected.setEnabled(true);
            //SET unitSelected to visible
            unitSelected.setVisible(true);
        }
        //ELSE
        else
        {
            //UPDATE terrainSelected with terrain
            ((TerrainSelected)terrainSelected)
                    .setTerrain(terrain, terrainImage);
            //SET terrainSelected to enabled
            terrainSelected.setEnabled(true);
            //SET terrainSelected to visible
            terrainSelected.setVisible(true);

            //SET unitSelected to not enabled
            unitSelected.setEnabled(false);
            //SET unitSelected to not visible
            unitSelected.setVisible(false);
        }
        //END IF
        
        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }

    //CHECKSTYLE:OFF - Ignore generated code.
    // Authorized by Dr. Dalbey.
    
    /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAlignmentY(0.0F);
        setMaximumSize(new java.awt.Dimension(200, 300));
        setMinimumSize(new java.awt.Dimension(200, 300));
        setPreferredSize(new java.awt.Dimension(200, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    //CHECKSTYLE:ON
}
