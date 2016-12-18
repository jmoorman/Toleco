package toleco;

import toleco.controller.I_Controller;
import toleco.logic.GameBoard;
import toleco.view.I_GameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Observable;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import toleco.controller.EditorController;
import toleco.controller.GameController;
import toleco.view.ConsoleGameView;
import toleco.view.gui.SwingGameView;
import toleco.view.gui.editor.EditorGameView;

//CHECKSTYLE:OFF - Ignore fan-out complexity (allowed = 20, actual = 21).
// Ignore authorized by Dr. Dalbey.

/**
*The main class for the Toleco application. This class will either launch into
* console (debug) mode or into a GUI mode depending on the command arguments.
*
* If GUI mode is launched, TolecoApp will prompt the user, via a main menu, about what
* he/she would like to do. The options are:
* New Game - prompts the user to choose a map file to start a new battle.
* Load Game - prompts the user to choose a save file to contine a previous battle.
* Map Editor - takes the user to the Map Editor where they ccan create their own maps.
* Help - pops up a window with descriptions of the game mechanics.
* Exit - Exits the application.
*
* @author Eriq Augustine (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public class TolecoApp extends JFrame implements ActionListener
{
    //CHECKSTYLE:ON

    /**
    * The view that displays that gameboard.
    * This is the View in the MVC architecture.
    */
    private I_GameView view;

    /**
    * The controller that regulates the UI and game board.
    * This is the Controller in the MVC architecture.
    */
    private I_Controller controller;

    /**
    * The playing area that units and terrain occupy.
    * This is the Model in the MVC architecture.
    */
    private GameBoard board;

    /**
    * A main selection menu for the user (if GUI mode is launched).
    * Contains the following options:
    * New Game - prompts the user to choose a map file to start a new battle.
    * Load Game - prompts the user to choose a save file to contine a previous
    * battle.
    * Map Editor - takes the user to the Map Editor where they ccan create their
    * own maps.
    * Help - pops up a window with descriptions of the game mechanics.
    * Exit - Exits the application.
    */
    private JPanel mainMenu;

    /**
     * A boolean representing whether or not the app should run in GUI Mode.
     */
    private boolean inGUIMode;

    /**
     * The path the the team's image.
     */
    private static final String kTeamImagePath = "images/MainMenu_Title.jpg";

    /**
     * The dialog to be shown to the user when they click the "About" button.
     */
    private static final String kAboutDialog = "Toleco v. 1.0\n\nMembers:\n" +
        "Adam Armstrong\nEriq Augustine\nAndrew Barton\nJon Moorman\n" +
        "Evan Ralson\nMatt Tognetti";

    /**
     * The dialog to be shown to the user when they click the "Help" button.
     */
    private static final String kHelpDialog = "help.txt";

    /**
     * A constructor used for running the game in console mode.
     * @param map the map file used for the game
     */
    public TolecoApp(String map)
    {
        //Set inGUIMode to false;
        inGUIMode = false;

        //CALL initialize.
        initialize();

        //CALL launch game
        launchConsoleGame(map);
    }

    /**
     * A constructor for running Toleco in GUI mode
     */
    public TolecoApp()
    {
        //TRY
        try
        {
            //Set the look and feel of the GUI
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        //CATCH
        catch (Exception e)
        {
            //Print an error informing the user that the look and feel
            // could not be changed.
            System.err.println("Unable to change look and feel.");
        }
        //END

        //Set the default close operation of the GUI
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //Disable the ability to resize the GUI window
        setResizable(false);

        //INITIALIZE GUIMode with GUImode
        inGUIMode = true;

        //Initialize main menu.
        mainMenu = new JPanel();

        //CALL initialize
        initialize();
    }

    /**
     * Performs an action based on the button clicked.
     *
     * @param event the event code to be performed
     */
    public void actionPerformed(ActionEvent event)
    {
        //Get the action command from the ActionEvent.
        String actionCmd = event.getActionCommand();

        //IF the action command is "newgame"
        if(actionCmd.equals("newgame"))
        {
            //CALL handleNewGame
            handleNewGame();
        }
        //ELSEIF loadGame
        else if(actionCmd.equals("loadgame"))
        {
            //CALL handleLoadGame
            handleLoadGame();
        }
        //ELSEIF editor
        else if(actionCmd.equals("editor"))
        {
            //CALL handleMapEditor
            handleMapEditor();
        }
        //ELSEIF help
        else if(actionCmd.equals("help"))
        {
            //FIXES Defect #192
            //TRY
            try
            {
                //Create a new scanner on kHelpDialog
                Scanner scanner = new Scanner(new File(kHelpDialog));
                //Create an empty String
                String helpDialog = "";
                //LOOP while the scanner has more lines
                while(scanner.hasNextLine())
                {
                    //ADD the next line plus a newline to the string
                    helpDialog = helpDialog + scanner.nextLine() + "\n";
                //END LOOP
                }
                //Create a new JTextArea with the string
                JTextArea area = new JTextArea(helpDialog);

                //CHECKSTYLE:OFF - Ignore GUI magic numbers.

                //SET the areas rows to 30
                area.setRows(30);
                //SET the areas columns to 50
                area.setColumns(50);

                //CHECKSTYLE:ON

                //SET linewrap on the area
                area.setLineWrap(true);
                //SET the area to be uneditable
                area.setEditable(false);

                //CREATE a new JScrollPane with the text area
                JScrollPane pane = new JScrollPane(area);
                //DISPLAY help info dialog box with the scroll pane
                JOptionPane.showMessageDialog(null, pane, "Test",
                    JOptionPane.QUESTION_MESSAGE);
            }
            catch(Exception err)
            {
                //DISPLAY error in help dialog box
                JOptionPane.showMessageDialog(mainMenu, kHelpDialog, "Help Menu", 1);
            }
        }
        //ELSEIF about
        else if(actionCmd.equals("about"))
        {
            //DISPLAY about info dialog box
            JOptionPane.showMessageDialog(mainMenu, kAboutDialog, "About", 1);
        }
        //ELSEIF quit
        else if(actionCmd.equals("quit"))
        {
            //CALL quit
            quit();
        }
        //ENDIF
    }

    /**
     * Take care of any actions when the user presses the "New Game" button.
     */
    private void handleNewGame()
    {
        //Create a file chooser
        JFileChooser chooser = new JFileChooser();

        //Set the starting directory to the map folder.
        chooser.setCurrentDirectory(new File("./maps"));

        //Call chooser.setApproveButtonText with "Load" so it has
        // the right text
        chooser.setApproveButtonText("Load");

        //Create a filter for the file chooser that only looks
        // for .ocem files
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Toleco Map File (.ocem)", "ocem");

        //Set the filter to the chooser
        chooser.setFileFilter(filter);

        //CALL showOpenDialog with this as a param
        int returnValue = chooser.showOpenDialog(this);

        //TRY
        try
        {

            //IF the result from the file chooser in the affirmative
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                //Get the canonical path from the File.
                String fileName = chooser.getSelectedFile().getCanonicalPath();

                //CALL launchGUIGame
                launchGUIGame(fileName);
            }
            //ENDIF
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error to the user about being unable to resolve the
            // map file path.
            System.err.println("Error while loading battle. Check paths.");
            ex.printStackTrace();
        }
        //END
    }

    /**
     * Take care of any actions when the user presses the "Load Game" button.
     */
    private void handleLoadGame()
    {
        //Create a file chooser
        JFileChooser chooser = new JFileChooser();

        //Set the starting directory to the map folder.
        chooser.setCurrentDirectory(new File("./saves"));

        //Call chooser.setApproveButtonText with "Load" so it
        // has the right text
        chooser.setApproveButtonText("Load");

        //Create a filter for the file chooser that only looks
        // for .osm files
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Toleco Save File (.osm)", "osm");

        //Set the filter to the chooser
        chooser.setFileFilter(filter);

        //CALL showOpenDialog with this as a param
        int returnValue = chooser.showOpenDialog(this);

        //TRY
        try
        {
            //Fixes defect #196
            //IF the result from the file chooser is not null.
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                //Get the canonical path from the File.
                String fileName = chooser.getSelectedFile().getCanonicalPath();

                //CALL launchGUIGame
                launchGUIGame(fileName);
            }
            //ENDIF
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error to the user about being unable to resolve the
            // map file path.
            System.err.println("Error while loading battle. Check paths.");
        }
        //END
    }

    /**
     * Take care of any actions when the user presses the "Map Editor" button.
     */
    private void handleMapEditor()
    {
        //Create a file chooser
        JFileChooser chooser = new JFileChooser();

        //Set the starting directory to the map folder.
        chooser.setCurrentDirectory(new File("./maps"));

        //Call chooser.setApproveButtonText with "Save" so it has the
        // right text
        chooser.setApproveButtonText("Edit");

        //Create a filter for the file chooser that only looks
        // for .ocem files
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Toleco Map File (.ocem)", "ocem");

        //Set the filter to the chooser
        chooser.setFileFilter(filter);

        //CALL showOpenDialog with this as a param
        int returnValue = chooser.showOpenDialog(this);

        //TRY
        try
        {
            //Fixes defect#232
             //IF the result from the file chooser is not null.
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                //Get the File with a call to getSelectedFile()
                File mapFile = chooser.getSelectedFile();

                //IF the result from the file chooser is not null.
                if (mapFile != null)
                {
                    //Get the canonical path from the File.
                    String fileName = mapFile.getCanonicalPath();

                    //CALL launchMapEditor
                    launchMapEditor(fileName);
                }
                //ENDIF
            }
            //ENDIF
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error to the user about being unable to resolve the
            // map file path.
            System.err.println("Error while loading battle. Check paths.");
            ex.printStackTrace();
        }
        //END
    }

    /**
     * This is auto-generated NetBeans code with some modifications.
     * 
     * Initialize any components necessary in whichever mode is running
     * (console or GUI).
     */
    private void initialize()
    {
        //IF GUIMode THEN
        if(inGUIMode)
        {
            //INITIALIZE all menu buttons

            //Create the new game button
            JButton newGameBtn = new JButton("New Game");
            //Set the new game button's action command
            newGameBtn.setActionCommand("newgame");
            //Set the mnemonic of the new game button
            newGameBtn.setMnemonic(KeyEvent.VK_N);
            //Add this as an action listener to the new game button
            newGameBtn.addActionListener(this);

            //Create the load game button
            JButton loadGameBtn = new JButton("Load Game");
            //Set the load game button's action command
            loadGameBtn.setActionCommand("loadgame");
            //Set the mnemonic of the load game button
            loadGameBtn.setMnemonic(KeyEvent.VK_L);
            //Add this as an action listener to the load game button
            loadGameBtn.addActionListener(this);

            //Create the editor button
            JButton editorBtn = new JButton("Map Editor");
            //Set the editor button's action command
            editorBtn.setActionCommand("editor");
            //Set the mnemonic of the editor button
            editorBtn.setMnemonic(KeyEvent.VK_M);
            //Add this as an action listener to the editor button
            editorBtn.addActionListener(this);

            //Create the help button
            JButton helpBtn = new JButton("Help");
            //Set the help button's action command
            helpBtn.setActionCommand("help");
            //Set the mnemonic of the help button
            helpBtn.setMnemonic(KeyEvent.VK_H);
            //Add this as an action listener to the help button
            helpBtn.addActionListener(this);

            //Create the quit button
            JButton quitBtn = new JButton("Quit");
            //Set the quit button's action command
            quitBtn.setActionCommand("quit");
            //Set the mnemonic of the quit button
            quitBtn.setMnemonic(KeyEvent.VK_Q);
            //Add this as an action listener to the quit button
            quitBtn.addActionListener(this);

            //Create the about button
            JButton aboutBtn = new JButton("Ocelot");
            //Set the about button's action command
            aboutBtn.setActionCommand("about");
            //Set the mnemonic of the about button
            aboutBtn.setMnemonic(KeyEvent.VK_O);
            //Add this as an action listener to the about button
            aboutBtn.addActionListener(this);

            //Craete a new JLabel.
            JLabel label = new JLabel();

            //Set the icon on the newly created label to be picture at kTeamImagePath
            label.setIcon(new ImageIcon(kTeamImagePath));

            //CHECKSTYLE:OFF

            //Set the preferred size of the mainMenu
            mainMenu.setPreferredSize(new java.awt.Dimension(600, 400));

            //Set the layout of the main menu to an AbsoluteLayout.
            mainMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            //Add the new game button to the main menu.
            mainMenu.add(newGameBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 174, 132, 47));

            //Add the load game button to the main menu.
            mainMenu.add(loadGameBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 174, 122, 47));

            //Add the editor button to the main menu.
            mainMenu.add(editorBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 239, 132, 50));

            //Add the help button to the main menu.
            mainMenu.add(helpBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 239, 122, 50));

            //Add the quit button to the main menu.
            mainMenu.add(quitBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 132, 49));

            //Add the about button to the main menu.
            mainMenu.add(aboutBtn,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 80, 30));

            //Add the label with the team image button to the main menu.
            mainMenu.add(label,
                new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 340, 90));

            //CHECKSTYLE:ON

            //CALL getContentPane().add with the mainMenu as the supplied param
            getContentPane().add(mainMenu);

            //CALL getContentPane().setVisible with the boolean true
            getContentPane().setVisible(true);

            //Set this JFrame to be visible.
            this.setVisible(true);

            //CALL pack
            pack();
        }
        //ELSE
        else
        {
            //PRINT a welcome message.
            System.out.println("You are playing Toleco in Console Mode." +
                "Why would you not play in GUI mode with our amazing" +
                "2-D graphics");
        }
    }

    /**
     * Start a map editor using the EditorGameView.
     *
     * @param map the location of the map to use
     */
    private void launchMapEditor(String map)
    {
        //INITIALIZE gameBoard
        board = new GameBoard();

        //INITIALIZE controller as an EditorController
        controller = new EditorController(board);

        //CALL controller.setApp with this
        controller.setApp(this);

        //INITIALIZE view as an EditorView
        //Fixes defect #197
        view = new EditorGameView(board);

        //Add the view to the controller.
        controller.setView(view);

        //Add the controller as an observer to the view
        ((Observable)(view)).addObserver(controller);

        //Add the view as an observer of the board.
        board.addObserver(view);

        //TRY
        try
        {
            //Load the map
            controller.loadMap(map);

            //Remove the main menu from the content pane.
            getContentPane().remove(mainMenu);

            //Get a JPanel from the view.
            JPanel temp = view.getPanel();

            //Set the visibility of the panel to true.
            temp.setVisible(true);

            //Add the panel to this JFrame's content pane.
            getContentPane().add(temp);

            //Set this JFrame's content pane to be visible.
            getContentPane().setVisible(true);

            //Set this JFrame to be visible.
            this.setVisible(true);

            //Pack this JFrame
            pack();
        }
        //CATCH java.io.FileNotFoundException
        catch (java.io.FileNotFoundException ex)
        {
            //Print an error about having to return to the main menu.
            System.err.println("Returning to main menu.");
        }
        //END
    }

    /**
     * Start a battle using the SwingGameView.
     *
     * @param map the location of the map to use
     */
    private void launchGUIGame(String map)
    {
        //INITIALIZE gameBoard
        board = new GameBoard();

        //INITIALIZE controller as a GameController
        controller = new GameController(board);

        //CALL GameController.setApp with this
        controller.setApp(this);

        //INITIALIZE view as a SwingGameView
        view = new SwingGameView(board);

        //Add the view to the controller.
        controller.setView(view);

        //Add the controller as an observer to the view
        ((Observable)(view)).addObserver(controller);

        //Add the view as an observer of the board.
        board.addObserver(view);

        //TRY
        try
        {
            //Load the map
            controller.loadMap(map);

            //Remove the main menu from the content pane.
            getContentPane().remove(mainMenu);

            //Get a JPanel from the view.
            JPanel temp = view.getPanel();

            //Set the visibility of the panel to true.
            temp.setVisible(true);

            //Add the panel to this JFrame's content pane.
            getContentPane().add(temp);

            //Set this JFrame's content pane to be visible.
            getContentPane().setVisible(true);

            //Set this JFrame to be visible.
            this.setVisible(true);

            //Pack this JFrame
            pack();
        }
        //CATCH java.io.FileNotFoundException
        catch (java.io.FileNotFoundException ex)
        {
            //Print an error about having to return to the main menu.
            System.err.println("Returning to main menu.");
        }
        //END
    }

    /**
    * Launch a battle.
    */
    private void launchConsoleGame(String map)
    {
        //INITIALIZE gameBoard
        board = new GameBoard();

        //INITIALIZE GameController
        controller = new GameController(board);

        //CALL GameController.setApp with this
        controller.setApp(this);

        //INITIALIZE ConsoleGameView
        view = new ConsoleGameView();

        //Add the controller as an observer to the view
        ((Observable)(view)).addObserver(controller);

        //INITIALIZE controller
        controller.setView(view);

        //INITIALIZE board
        board.addObserver(view);

        //TRY
        try
        {
            //Load the map.
            controller.loadMap(map);

            //CALL start on ConsoleGameView
            ((ConsoleGameView)view).start();
        }
        //CATCH java.io.FileNotFoundException
        catch (java.io.FileNotFoundException ex)
        {
            //Print an error about having to return to the main menu.
            System.err.println("Returning to main menu.");
        }
        //END
    }

    /**
    * Quit the application.
    */
    private void quit()
    {
        //KILL the application entirely
        System.exit(0);
    }

    /**
     * Cleans up the main menu by setting gameplay fields to null and re-displays
     * the main menu.
     */
    public void cleanUp()
    {
        getContentPane().remove(view.getPanel());
        //SET controller to null
        controller = null;
        //SET gameBoard to null
        board = null;
        //SET view to null
        view = null;

        //CALL initialize
        initialize();
    }
}
