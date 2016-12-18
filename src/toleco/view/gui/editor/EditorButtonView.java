package toleco.view.gui.editor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import toleco.controller.EditorAction;

/**
* The EditorButtonViewManual provides the three menu buttons, "Save", "Load", and
* "Quit", and their event handlers for the map editor GUI.
*
*
* @author Matt Tognetti (javadocs)
* @author Andrew Barton (implementation)
* @version 1.0
*/
public class EditorButtonView extends javax.swing.JPanel implements ActionListener
{
    /**
     * Used to pass data from the buttonView out into the view
     */
    private EditorGameView view;
    
    /** Creates new form EditorButtonView */
    public EditorButtonView()
    {
        initComponents();
        initializeButtons();
    }
    
    /**
     * Sets the current view.
     *
     * @param newView the SwingGameView that this GameButtonView will send actions to
     * @pre newView is a valid SwingGameView
     */
    public void setGameView(EditorGameView newView)
    {
        //set view to newView
        view = newView;
    }

    /**
    * Initializes the three buttons by setting the appropriate event handlers.
    */
    private void initializeButtons()
    {
        /**
        * Used to quit the map editor, displays a confirmation box and
        * exits the map editor and returns to the main menu if confirmed.
        */
        quitButton.addActionListener(this);
        /**
        * Used to save the state of the current map, opens a file save dialog when
        * clicked.
        */
        saveButton.addActionListener(this);
        /**
        * Used to load a map into the editor, opens a file selector when clicked.
        */
        loadButton.addActionListener(this);
    }

    //CHECKSTYLE:OFF - Ignore Cyclomatic Complexity
    // Ignore Authorized by Dr. Dalbey

    /**
     * Handles the events dispatched by the buttons.
     * @param event the ActionEvent dispatched by the buttons
     */
    public void actionPerformed(ActionEvent event)
    {
        //CHECKSTYLE:ON
        
        //IF view is null
        if(view == null)
        {
            //THROW runtime exception
            throw new RuntimeException("EditorButtonView does not have a view");
        }
        //ENDIF

        //IF quit button pressed
        if(event.getActionCommand().equals("quit"))
        {
            //DIPLAY yes/no dialog
            int chosen = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit to the main menu?"
                    +"\nAll unsaved progress"
                    +" will be lost!",
                    "Quit Editor", JOptionPane.YES_NO_OPTION);
            //IF the afirmative option was chosen
            if(chosen == JOptionPane.YES_OPTION)
            {
                //CALL view.acceptAction with EditorAction.kQuit
                view.acceptAction(EditorAction.kQuit.toString());
            }
            //ENDIF
        }
        //ENDIF
        //IF save button was pressed
        if(event.getActionCommand().equals("save"))
        {
            //make a new file chooser
            JFileChooser chooser = new JFileChooser();
            //Call chooser.setCurrentDirectory with the map directory
            chooser.setCurrentDirectory(new File("./maps"));
            //Call chooser.setApproveButtonText with "Save" so it has the right text
            chooser.setApproveButtonText("Save");
            //Call chooser.setFilter with a new MapFilter
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Toleco Map Files (.ocem)", "ocem"));
            //Call choser.setAcceptedAllFileFilterUsed with false
            //This is so only .ocem files can be selected
            chooser.setAcceptAllFileFilterUsed(false);
            //Cal chooser.setDialogTitle with "SaveGame"
            chooser.setDialogTitle("Save Map");
            //Display the file chooser
            int returnValue = chooser.showOpenDialog(this);

            //If the user selected afirmatve
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                //TRY
                try
                {
                    String fname = chooser.getSelectedFile().getCanonicalPath();
                    //IF the filename doesn't end with ".ocem"
                    if(!fname.endsWith((".ocem")))
                    {
                        //APPEND ".ocem" to the filename
                        fname += ".ocem";
                    }
                    //ENDIF
                    //Call view.acceptAction with EditorAction.kSave and the file's path
                    view.acceptAction(EditorAction.kSave.toString() + " "
                        + fname);

                }
                //CATCH IOException
                catch(IOException exep)
                {
                    //Show a message dialog about the arror
                    JOptionPane.showMessageDialog(this, "Error selecting map file, " +
                            "the chosen file's path cannot be resolved");
                    //print debug statements
                    System.out.println("The chosen file's path cannot be resolved");
                    exep.printStackTrace();
                }
                //END catch

            }//ENDIF
        }
        //ENDIF
        //IF the user clicked the load button
        if(event.getActionCommand().equals("load"))
        {
            JFileChooser chooser = new JFileChooser();
            //Call chooser.setCurrentDirectory with the map directory
            chooser.setCurrentDirectory(new File("./maps"));
            //Call chooser.setApproveButtonText with "Save" so it has the right text
            chooser.setApproveButtonText("Load");
            //Call chooser.setFilter with a new MapFilter
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Toleco Map Files (.ocem)", "ocem"));
            //Call choser.setAcceptedAllFileFilterUsed with false
            //This is so only .ocem files can be selected
            chooser.setAcceptAllFileFilterUsed(false);
            //Cal chooser.setDialogTitle with "Load Map"
            chooser.setDialogTitle("Load Map");
            //Show chooser
            int returnValue = chooser.showOpenDialog(this);

            //If the user selected in the affirmative
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    //IF the file Exists and ends wih .ocem
                    if(chooser.getSelectedFile().exists()
                        && chooser.getSelectedFile().getCanonicalPath()
                        .toLowerCase().endsWith(".ocem"))
                    {
                        //Call view.acceptAction with EditorAction.kLoad and the
                        //file path
                        view.acceptAction(EditorAction.kLoad.toString() + " "
                            + chooser.getSelectedFile().getCanonicalPath());
                    }
                    //ELSE
                    else
                    {
                        //Display a message that tells the user about the bad file
                        JOptionPane.showMessageDialog(this, "Invalid file chosen" +
                                " the file either doesn't exist, or is not " +
                                "a .ocem file.");
                        //recurse with the event, it wil open another fileChooser
                        actionPerformed(event);
                    }
                    //ENDIF

                }
                //CATCH IOException
                catch(IOException exep)
                {
                    //Display a message that tell the user about the error
                    JOptionPane.showMessageDialog(this, "Error selecting map file");
                    System.out.println("The chosen file's path cannot be resolved");
                    exep.printStackTrace();
                }
                //END catch

            }//ENDIF
        }//ENDIF
    }

    //CHECKSTYLE:OFF - Ignore generated code.
    // Authorized by Dr. Dalbey.

    /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(200, 100));
        setMinimumSize(new java.awt.Dimension(200, 100));
        setPreferredSize(new java.awt.Dimension(200, 100));

        saveButton.setMnemonic(KeyEvent.VK_S);
        saveButton.setText("Save");
        saveButton.setToolTipText("Save the map that you've edited");
        saveButton.setActionCommand("save");

        loadButton.setMnemonic(KeyEvent.VK_L);
        loadButton.setText("Load");
        loadButton.setToolTipText("Load a map to be edited.\nAll unsaved progress will be lost!");
        loadButton.setActionCommand("load");

        quitButton.setMnemonic(KeyEvent.VK_Q);
        quitButton.setText("Quit");
        quitButton.setToolTipText("Quit to the main menu.\nAll unsaved progress wil be lost!");
        quitButton.setActionCommand("quit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(quitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(loadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitButton)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>                        
    
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton loadButton;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration                   

    //CHECKSTYLE:ON
}
