package toleco.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import toleco.controller.PlayerAction;

/**
* A GameButtonViewManual is responsible for creating and modifying the game's
* button panel. GameButtonViewManual is used by the SwingGameView class.
*
* @author Adam Armstrong (javadocs)
* @author Andrew Barton (implementation)
* @version 1.0
*/
public class GameButtonView extends javax.swing.JPanel implements ActionListener
{
    /*
     * The Strings that will be shown on the buttons.
     */
    private static final String kAttackText = "Attack";
    private static final String kEndTurnText = "End Turn";
    private static final String kQuitText = "Quit";
    private static final String kCancelText = "Cancel";
    private static final String kSaveText = "Save";

    /*
     * The Strings that the buttons will send as their
     * ActionEvent's command string.
     */
    private static final String kCancelPressed = "cancel";
    private static final String kAttackPressed = "attack";
    private static final String kSavePressed = "save";
    private static final String kQuitPressed = "quit";
    private static final String kEndTurnPressed = "endTurn";


    /*
     * The tooltips that the various buttons will display.
     */
    private static final String kDisabledActionButtonToolTip = "You can attack" +
            " only if the selected location has one of your units on it.";
    private static final String kCancelActionButtonToolTip = "Cancel your attack," +
            " allowing you to move your units and select other terrains.";
    private static final String kEnabledActionButtonToolTip = "Attack an enemy unit!";
    private static final String kSaveToolTip = "Save your game so you can" +
            " play this later.";
    private static final String kQuitToolTip = "Quit to the main menu.";
    private static final String kEndTurnToolTip = "End your turn";



    /**
     * Used to pass data from the buttonView out into the view.
     */
    private SwingGameView view;

    /** 
     * Creates new form GameButtonView
     */
    public GameButtonView()
    {
        //Layout the components using initComponents()
        initComponents();
        //Add this as an actionListener to the buttons using initializeButtons()
        initializeButtons();
    }


    /**
     * Sets the current view.
     *
     * @param newView the SwingGameView that this GameButtonView will send actions to
     * @pre newView is a valid SwingGameView
     */
    public void setGameView(SwingGameView newView)
    {
        //SET view to newView
        view = newView;
    }

    /**
    * Disables the attack button.
    * @post actionButton is disabled.
    */
    public void switchToDisabledAttack()
    {
        //Disable the attack button by calling setEnabled
        actionButton.setText(kAttackText);
        //SET tooltip text to kDisabledActionButtonToolTip
        actionButton.setToolTipText(kDisabledActionButtonToolTip);
        //Disable the action button
        actionButton.setEnabled(false);
    }
    
    /**
    * Enables the attack button.
    * @post ActionButton says "Attack" and is disabled.
    */
    public void switchToEnabledAttack()
    {
        //SET the action button text to "Attack"
        actionButton.setText(kAttackText);
        //SET the actionButton's toolTipText to kEnabledActionButtonToolTip
        actionButton.setToolTipText(kEnabledActionButtonToolTip);
        //SET the action for when the button is clicked
        actionButton.setActionCommand(kAttackPressed);
        //SET hotkey for the attack button
        actionButton.setMnemonic(KeyEvent.VK_A);
        //Enable the button by calling setEnabled
        actionButton.setEnabled(true);
    }
    
    /**
    * Switches the attack button to the cancel button.
    * @post ActionButton says "Cancel" and has other properties set.
    */
    public void switchToCancel()
    {
        //SET the action button text to 'Cancel'
        actionButton.setText(kCancelText);
        //SET the toolTip text to kDisabledActionButtonToolTip
        actionButton.setToolTipText(kCancelActionButtonToolTip);
        //SET the action for when the button is clicked
        actionButton.setActionCommand(kCancelPressed);
        //SET hotkey for the cancel button
        actionButton.setMnemonic(KeyEvent.VK_C);
        //Enable the button by calling setEnabled
        actionButton.setEnabled(true);
    }
    
    /**
    * Adds this as actionListener to all the buttons
    * @pre Buttons have been initialized using initComponents()
    * @post this is an actionListener of the butons.
    */
    private void initializeButtons()
    {
        //ADD this actionListener to actionButton
        actionButton.addActionListener(this);
        //ADD this actionListener to quitButton
        quitButton.addActionListener(this);
        //ADD this actionListener to saveButton
        saveButton.addActionListener(this);
        //ADD this actionListener to endTurnButton
        endTurnButton.addActionListener(this);
        
    }

    //CHECKSTYLE:OFF - Ignore Cyclomatic

    /**
     * Handles the events dispatched by the buttons.
     * @param event the ActionEvent dispatched by the buttons
     */
    public void actionPerformed(ActionEvent event)
    {
        //CHECKSTYLE:ON

        //IF no view, throw new RuntimeException
        if(view == null)
        {
            throw new RuntimeException("GameButtonView does not have a view");
        }
        //ENDIF

        //these are all mutually exclusive, but can't use a switch since
        //not a primative and if/else would look terrible
        //IF Attack button pressed
        if(event.getActionCommand().equals(kAttackPressed))
        {
            //CALL view.acceptAction with PlayerAction.kAttack
            view.acceptAction(PlayerAction.kAttack.toString());
            switchToCancel();
        }
        //ENDIF
        //IF Cancel button pressed
        if(event.getActionCommand().equals(kCancelPressed))
        {
            //CALL view.acceptAction with PlayerAction.kCancelAttack
            view.acceptAction(PlayerAction.kCancelAttack.toString());
            switchToEnabledAttack();
        }
        //ENDIF
        //IF End Turn Button pressed
        if(event.getActionCommand().equals(kEndTurnPressed))
        {
            //DISPLAY the EndTurn dialog (JOptionPane.showConfirmDialog())
            int chosen = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to end your turn?",
                    "End Turn", JOptionPane.YES_NO_OPTION);
            //IF the user pressed the affirmative button
            if(chosen == JOptionPane.YES_OPTION)
            {
                //CALL view.acceptAction with PlayerAction.kEndTurn
                view.acceptAction(PlayerAction.kEndTurn.toString());
            }
            //ENDIF
            
        }
        //ENDIF
        //IF Save button pressed
        if(event.getActionCommand().equals(kSavePressed))
        {
            //CONSTRUCT a new JFileChooser
            JFileChooser chooser = new JFileChooser();
            //Call chooser.setCurrentDirectory with the map directory
            chooser.setCurrentDirectory(new File("./saves"));
            //Call chooser.setApproveButtonText with "Save" so it has the right text
            chooser.setApproveButtonText("Save");
            //Call chooser.setFilter with a new MapFilter
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Toleco Map Files (.osm)", "osm"));
            //Call choser.setAcceptedAllFileFilterUsed with false
            //This is so only .ocem files can be selected
            chooser.setAcceptAllFileFilterUsed(false);
            //Cal chooser.setDialogTitle with "SaveGame"
            chooser.setDialogTitle("Save Game");
            //Call chooser.showOpenDialog with this
            int returnValue = chooser.showOpenDialog(this);
            //If the affirmative button was pressed
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                //TRY
                try
                {
                    //get the filename
                    String fname = chooser.getSelectedFile().getCanonicalPath();
                    //IF the filename doesn't end with the .osm extension
                    if(!fname.endsWith(".osm"))
                    {
                        //APPEND ".osm" to the filename
                        fname += ".osm";
                    }
                    //ENDIF
                    //CALL view.acceptAction with PlayerActon.kSave and the fileName
                    view.acceptAction(PlayerAction.kSave.toString() + " "
                        + fname);
                    
                }
                //CATCH IOException
                catch(IOException exep)
                {
                    //If something went terribly terribly wrong.
                    JOptionPane.showMessageDialog(this, "Error selecting map file," +
                            " the chosen file's path cannot be resolved.");
                    System.out.println("The chosen file's path cannot be resolved");
                    exep.printStackTrace();
                }
                //END catch
            }
            //ENDIF
        }
        //ENDIF
        //IF Quit Button Pressed
        if(event.getActionCommand().equals(kQuitPressed))
        {
            //Call JOptionPanel.showConfirmDialog()
            int chosen = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit to the main menu?"
                    + "\nAll unsaved progress "
                    + "will be lost!",
                    "Quit Game", JOptionPane.YES_NO_OPTION);
            //If the affirmative button was pressed
            if(chosen == JOptionPane.YES_OPTION)
            {
                //CALL view.acceptAction with PlayerAction.kQuit
                view.acceptAction(PlayerAction.kQuit.toString());
            }
            //ENDIF
        }
        //ENDIF
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

        actionButton = new javax.swing.JButton();
        endTurnButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(200, 115));
        setMinimumSize(new java.awt.Dimension(200, 115));
        setPreferredSize(new java.awt.Dimension(200, 100));

        actionButton.setMnemonic(KeyEvent.VK_A);
        actionButton.setText(kAttackText);
        actionButton.setToolTipText(kDisabledActionButtonToolTip);
        actionButton.setActionCommand(kAttackPressed);
        actionButton.setEnabled(false);
        actionButton.setMaximumSize(new java.awt.Dimension(73, 23));
        actionButton.setMinimumSize(new java.awt.Dimension(73, 23));
        actionButton.setPreferredSize(new java.awt.Dimension(73, 23));

        endTurnButton.setMnemonic(KeyEvent.VK_E);
        endTurnButton.setText(kEndTurnText);
        endTurnButton.setToolTipText(kEndTurnToolTip);
        endTurnButton.setActionCommand(kEndTurnPressed);
        endTurnButton.setAlignmentX(0.5F);
        endTurnButton.setMaximumSize(new java.awt.Dimension(73, 23));
        endTurnButton.setMinimumSize(new java.awt.Dimension(73, 23));
        endTurnButton.setPreferredSize(new java.awt.Dimension(73, 23));

        saveButton.setMnemonic(KeyEvent.VK_S);
        saveButton.setText(kSaveText);
        saveButton.setToolTipText(kSaveToolTip);
        saveButton.setActionCommand(kSavePressed);

        quitButton.setMnemonic(KeyEvent.VK_Q);
        quitButton.setText(kQuitText);
        quitButton.setToolTipText(kQuitToolTip);
        quitButton.setActionCommand(kQuitPressed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endTurnButton, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(actionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(actionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(endTurnButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton)
                    .addComponent(quitButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionButton;
    private javax.swing.JButton endTurnButton;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    //CHECKSTYLE:ON
}
