package toleco.view.gui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import toleco.controller.EditorAction;

/**
* A ToolViewManual is the graphical interface for the map editor toolset.
* It includes buttons for switching between unit and terrain selection, a
* scrollable list for selecting which unit or terrain type should be placed on
* the map, and, if selecting a unit type, an interface for selecting which
* player team a unit belongs to.
*
* @author Matt Tognetti (Javadocs)
* @author Evan Ralston (Implementation)
* @version 1.0
*/
public class ToolView extends JPanel implements ActionListener
{
    /**
    * A collection of all terrain types available in the editor.
    */
    private ArrayList<String> terrainTypes;

    /**
    * A collection of all unit types available in the editor.
    */
    private ArrayList<String> unitTypes;

    /**
    * Stores the BufferedImage's the map needs to display. The key used for
    * looking up values in this HashMap is the String for the Unit or Terrain
    * name.
    */
    private HashMap<String, BufferedImage> images;

    /**
     * Container for the Unit Pane.
     */
    private JList unitList;

    /**
     * Container for the Terrain Pane.
     */
    private JList terrainList;

    /**
     * A list of all the units that can be selected.
     */
    private ArrayList<JLabel> unitOptions;

    /**
     * A list of all the terrains that can be selected.
     */    
    private ArrayList<JLabel> terrainOptions;

    /**
     * A reference to the EditorGameView which contains this ToolView.
     */
    private EditorGameView view;

    /**
     * A button group containing the player buttons.
     */
    private javax.swing.ButtonGroup buttonGroup1;

    /**
     * A radio button representing the first player.
     */
    private javax.swing.JRadioButton player1Button;

    /**
     * A radio button representing the first player.
     */
    private javax.swing.JRadioButton player2Button;

    /**
     * A representation of all the available Terrains.
     */
    private javax.swing.JScrollPane terrainPane;

    /**
     * The header for the ToolView.
     */
    private javax.swing.JLabel topStringHolder;

    /**
     * A representation of all the available Units.
     */
    private javax.swing.JScrollPane unitPane;

    /**
     * A tab to select between Terrains and Units.
     */
    private javax.swing.JTabbedPane unitTerrainTabs;
    
    /** 
     * Creates new form ToolView.
     */
    public ToolView()
    {
        //CALL initComponents
        initComponents();
        //SET the unitPanes HorizontalScrollBarPolicy to be as needed
        unitPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants
                .HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
    
    /**
    * Class constructor specifying the unit and terrain types available to be
    * used in the map editor.
    *
    * @param tTypes a list of terrain types available in the editor
    * @param uTypes a list of unit types available in the editor
    * @param pictures a collection of all images needed for the application
    */
    public void init(ArrayList<String> tTypes, ArrayList<String> uTypes,
            HashMap<String, BufferedImage> pictures)
    {
        //SET member data to corresponding parameters
        terrainTypes = tTypes;
        unitTypes = uTypes;
        images = pictures;

        //CALL initUnitPane
        initUnitPane();

        //CALL initTerrainPane
        initTerrainPane();

        //SET player1Button's action command to be player1
        player1Button.setActionCommand("player1");

        //SET player1Button to be selected
        player1Button.setSelected(true);

        //SET player2Button's action command to be player2
        player2Button.setActionCommand("player2");

        //Add this as an ActionListener to player1Button.
        player1Button.addActionListener(this);

        //Add this as an ActionListener to player2Button.
        player2Button.addActionListener(this);
    }

    /**
     * Sets the game view.
     * @param editorView the EditorGameView to set view to
     */
    public void setGameView(EditorGameView editorView)
    {
        //SET view to editorView
        view = editorView;
    }

    /**
     * Initializes the terrain tab.
     */
    private void initTerrainPane()
    {
        terrainOptions = new ArrayList<JLabel>();
        //IF terrainTypes is not null
        if(terrainTypes != null)
        {
            //FOR every string terrain in terrainTypes
            for(String terrain : terrainTypes)
            {
                //CREATE a new JLabel temp with an image icon generated using
                //the image that terrain maps to in images
                JLabel temp = new JLabel(terrain, new ImageIcon(images.get(terrain)),
                        SwingConstants.CENTER);
                //SET temp's tool tip string to be terrain
                temp.setToolTipText(terrain);
                //SET a boarder around temp
                temp.setBorder(new javax.swing.border.LineBorder(new
                        java.awt.Color(0, 0, 0), 1, true));
                //ADD temp to terrainOptions
                terrainOptions.add(temp);
            }
            //END FOR
            
            //INITIALIZE terrainList as a JList with terrain options
            terrainList = new JList(terrainOptions.toArray());
            //SET terrainList's cell renderer to be a instance of MyCellRenderer
            terrainList.setCellRenderer(new MyCellRenderer());
            //SET terrainPane's viewport view to be terrainList
            terrainPane.setViewportView(terrainList);
            //ADD a new MyTerrainListSelectionListener as a list selection
            //listener to terrainLists selection model
            terrainList.getSelectionModel().addListSelectionListener(new
                    MyTerrainListSelectionListener());
        }
        //END IF
    }

    /**
     * Initializes the unit tab.
     */
    private void initUnitPane()
    {
        unitOptions = new ArrayList<JLabel>();
        //IF unitTypes is not null
        if(unitTypes != null)
        {
            //FOR every string unit in unitTypes
            for(String unit : unitTypes)
            {
                //CREATE a new JLabel temp with an image icon generated using
                //the image that unit maps to in images
                JLabel temp = new JLabel(unit, new ImageIcon(images.get(unit)),
                        SwingConstants.CENTER);
                //SET temp's tool tip string to be unit
                temp.setToolTipText(unit);
                //SET a boarder around temp
                temp.setBorder(new javax.swing.border.LineBorder(new
                        java.awt.Color(0, 0, 0), 1, true));
                //ADD temp to unitOptions
                unitOptions.add(temp);
            }
            //END FOR
            //INITIALIZE unitList as a JList with unit options
            unitList = new JList(unitOptions.toArray());
            //SET unitList's cell renderer to be a instance of MyCellRenderer
            unitList.setCellRenderer(new MyCellRenderer());
            //SET unitPane's viewport view to be unitList
            unitPane.setViewportView(unitList);
            //ADD a new MyUnitListSelectionListener as a list selection listener
            //to unitLists selection model
            unitList.getSelectionModel().addListSelectionListener(new
                    MyUnitListSelectionListener());
        }
        //END IF
    }

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e)
    {
        //IF e is not null
        if(e != null)
        {
            //IF e's action command is "player1"
            if(e.getActionCommand().equals("player1"))
            {
                //CALL acceptAction with kChoosePlayer and kPlayer2
                view.acceptAction(EditorAction.kChoosePlayer.toString() +
                        " kPlayer1");
            }
            //ELSE IF e's action command is "player2"
            else if(e.getActionCommand().equals("player2"))
            {
                //CALL acceptAction with kChoosePlayer and kPlayer2
                view.acceptAction(EditorAction.kChoosePlayer.toString() +
                        " kPlayer2");
            }
            //END IF
        }
        //END IF
    }

    /**
     * Draws an single element in a JList.
     */
    private class MyCellRenderer extends JLabel implements ListCellRenderer
    {
        /**
         * Constructor for MyCellRenderer which sets opaque to true
         */
        public MyCellRenderer()
        {
            //CALL setOpaque with true
            setOpaque(true);
        }

        /**
         * {@inheritDoc}
         */
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellhasFocus)
        {
            //CALL setIcon with value.getIcon
            setIcon(((JLabel)value).getIcon());
            //CALL setToolTip with value.getToolTipText
            setToolTipText(((JLabel)value).getToolTipText());
            //CALL setText with value.getText
            setText(((JLabel)value).getText());

            //DECLARE a Color variable background
            Color background;
            //DECLARE a Color variable foreground
            Color foreground;

            //INITIALIZE a DropLocation dropLocation with list.getDropLocation()
            JList.DropLocation dropLocation = list.getDropLocation();
            //IF dropLocation is not null AND dropLocation is not an insert slot
            //AND the index of dropLocation is not index
            if(dropLocation != null && !dropLocation.isInsert() &&
                    dropLocation.getIndex() != index)
            {
                //SET the background to BLUE
                background = Color.BLUE;
                //SET the foreground to WHITE
                foreground = Color.WHITE;
            }
            //ELSE IF isSelected
            else if(isSelected)
            {
                //SET the background to RED
                background = Color.BLACK;
                //SET the foreground to WHITE
                foreground = Color.WHITE;
            }
            //ELSE
            else
            {
                //SET the background to WHITE
                background = Color.WHITE;
                //SET the foreground to BLACK
                foreground = Color.BLACK;
            }
            //END IF
            //CALL setBackground with background
            setBackground(background);
            //CALL setForeground with foreground
            setForeground(foreground);

            //return this
            return this;
        }
    }

    /**
     * Handles user clicks on the Unit List.
     */
    private class MyUnitListSelectionListener implements ListSelectionListener
    {
        /**
         * @inheritDoc
         */
        public void valueChanged(ListSelectionEvent event)
        {
            //Get the selected index in the list.
            int selectedIndex = unitList.getSelectedIndex();

            //IF the selected index is not -1.
            if (selectedIndex != -1)
            {
                //CALL acceptAction with a string containing the editor action enum
                //kChooseUnit and the terrain that was constructed string
                view.acceptAction(EditorAction.kChooseUnit.toString() + " " +
                    unitTypes.get(selectedIndex));

                //CALL unitList.clearSelection
                terrainList.clearSelection();
            }
            //END IF
        }
    }

    /**
     * Handles user clicks on the Terrain List.
     */
    private class MyTerrainListSelectionListener implements ListSelectionListener
    {
        /**
         * {@inheritDoc}
         */
        public void valueChanged(ListSelectionEvent event)
        {
            //Get the selected index in the list.
            int selectedIndex = terrainList.getSelectedIndex();

            //IF the selected index is not -1.
            if (selectedIndex != -1)
            {
                //CALL acceptAction with a string containing the editor action enum
                //kChooseTerrain and the terrain that was constructed string
                view.acceptAction(EditorAction.kChooseTerrain.toString() + " " +
                    terrainTypes.get(selectedIndex));

                //CALL unitList.clearSelection
                unitList.clearSelection();
            }
            //END IF
        }
    }

    //CHECKSTYLE:OFF - Ignore generated code.
    // Authorized by Dr. Dalbey.
    
    /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    @SuppressWarnings("unchecked")
    private void initComponents()
    {
        buttonGroup1 = new javax.swing.ButtonGroup();
        topStringHolder = new javax.swing.JLabel();
        player1Button = new javax.swing.JRadioButton();
        player2Button = new javax.swing.JRadioButton();
        unitTerrainTabs = new javax.swing.JTabbedPane();
        unitPane = new javax.swing.JScrollPane();
        terrainPane = new javax.swing.JScrollPane();

        setPreferredSize(new java.awt.Dimension(200, 300));

        topStringHolder.setFont(new java.awt.Font("DejaVu Sans", 1, 24));
        topStringHolder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topStringHolder.setText("Map Editor"); // NOI18N
        topStringHolder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        buttonGroup1.add(player1Button);
        player1Button.setText("Leaftron"); // NOI18N

        buttonGroup1.add(player2Button);
        player2Button.setText("Cromagmar"); // NOI18N

        unitPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        unitPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        unitTerrainTabs.addTab("Units", unitPane);

        terrainPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        terrainPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        unitTerrainTabs.addTab("Terrain", terrainPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(player1Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(player2Button)
                        .addGap(63, 63, 63))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(topStringHolder))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(unitTerrainTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(topStringHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unitTerrainTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(player1Button)
                    .addComponent(player2Button))
                .addContainerGap())
        );
    }

    //CHECKSTYLE:ON
}
