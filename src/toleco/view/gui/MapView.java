package toleco.view.gui;

import java.awt.Component;
import toleco.terrain.Terrain;
import toleco.view.I_GameView;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import toleco.controller.EditorAction;
import toleco.controller.Player;
import toleco.controller.PlayerAction;
import toleco.logic.GameBoard;
import toleco.unit.Unit;

/**
* A MapView is responsible for providing the logic to properly display the
* game map. MapView is used by the SwingGameView class and the
* EditorGameView class.
*
* @author Adam Armstrong (Javadoc)
* @author Jon Moorman (Implementation)
* @version 1.0
*/
public class MapView extends JPanel
{
    /**
     * The size of an image that will be used for map cells
     */
    public static final int kImageSize = 40;

    /**
     * The rgb value that will be treated as transparent during overlays
     */
    public static final int kTransparentColor = -1;

    /**
    * Stores all Terrain and Unit images.
    */
    private JTable gameTable;
    
    /**
     * Stores the GameView that this MapView is a part of.
     */
    private I_GameView gameView;

    /**
    * Stores the BufferedImage's the map needs to display. The key used for
    * looking up values in this HashMap is the String for the Unit or Terrain
    * name.
    */
    private HashMap<String, BufferedImage> images;
    
    /**
     * Stores the state of the GUI for dealing with overlays.
     */
    private BoardCell[][] board;

    /**
     * Stores the coordinates of the selected cell.
     */
    private Point selectedCell;

    /**
    * Create a MapView with a reference to the images it will need to display.
    *
    * @param images a HashMap used to look up images the map will
    * display
    */
    public MapView(HashMap<String, BufferedImage> images)
    {
        //STORE a reference to images
        this.images = images;

        //INIT board with the number of rows and columns
        board = new BoardCell[GameBoard.kNumRows][GameBoard.kNumCols];
        
        //FOR every row of the board
        for (int row = 0; row < GameBoard.kNumRows; row++)
        {
            //FOR every column of the board
            for (int col = 0; col < GameBoard.kNumCols; col++)
            {
                //SET the current cell of the board to a cell with null values
                board[row][col] = new BoardCell(null, null, null);
            }
        }

        //Create an array of strings to use as column headings
        String[] emptyCols = new String[GameBoard.kNumCols];

        //FOR every column of the board
        for (int col = 0; col < GameBoard.kNumCols; col++)
        {
            //SET the column array to be an empty String
            emptyCols[col] = "";
        }

        //SET gameTable to a new JTable with board and column array
        gameTable = new JTable(board, emptyCols)
        {
            //Override the isCellEditable method to always return false
            @Override
            public boolean isCellEditable(int x, int y)
            {
                //RETURN false
                return false;
            }
        };

        //INIT gameTable to display the gird properly
        gameTable.setShowGrid(false);
        gameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameTable.setCellSelectionEnabled(false);
        gameTable.setRowHeight(kImageSize + 1);
        gameTable.setCellEditor(null);

        //FOR every column on the board
        for (int col = 0; col < GameBoard.kNumCols; col++)
        {
            //SET preferred column width to image size + 1
            gameTable.getColumnModel().getColumn(col).setPreferredWidth(kImageSize + 1);
            //SET cell renderer to be a new ImageRenderer
            gameTable.getColumnModel().getColumn(col).setCellRenderer(
                new ImageRenderer());
        }

        //add a mouse listener to the gameTable
        gameTable.addMouseListener(new MyMouseAdapter());

        add(gameTable);
        setVisible(true);
    }

    /**
     * Set this component to have a reference to the game view that owns it.
     * @param view the I_GameView to set the MapView to be.
     */
    public void setGameView(I_GameView view)
    {
        gameView = view;
    }
    /**
    * Select a specific location on the map.
    *
    * @param xCoord the x-coordinate to select on the map
    * @param yCoord the y-coordinate to select on the map
    */
    public void selectLocation(int xCoord, int yCoord)
    {
        //Add selection to cell at x and y coordinates
        if(selectedCell != null)
        {
            deselectLocation();
        }
        board[xCoord][yCoord].setSelection();
        selectedCell = new Point(xCoord, yCoord);
 
        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Deselects the selected location.
    */
    public void deselectLocation()
    {
        //IF the selected cell is null.
        if (selectedCell != null)
        {
            //Remove the selection at the coordinates of selectedCell
            board[selectedCell.x][selectedCell.y].removeSelection();

            //Set selectedCell to null
            selectedCell = null;

            //CALL validate
            validate();
            //CALL repaint
            repaint();
        }
        //ENDIF
    }
    
    /**
    * Add highlights to the given cells.
    *
    * @param points the cells to give the highlights to
    */
    public void addHighlights(ArrayList<Point> points)
    {
        //FOR every point
        for(int index = 0; index < points.size(); index++)
        {
            //Add a highlight to the cell at the current point
            board[points.get(index).x][points.get(index).y].setHighlight();
        }

        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Remove highlights from the cells.
    */
    public void removeHighLights()
    {
        //FOR every row of the board
        for (int row = 0; row < GameBoard.kNumRows; row++)
        {
            //FOR every column of the board
            for (int col = 0; col < GameBoard.kNumCols; col++)
            {
                //Remove selection from current cell
                if(board[row][col].isSelected())
                {
                    board[row][col].removeHighlight();
                }
            }
        }

        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Draw the given map.
    *
    * @param map the map to draw
    */
    public void drawMap(Terrain[][] map)
    {
        //FOR every row in the map
        for(int row = 0; row < GameBoard.kNumRows; row++)
        {
            //FOR every column in the map
            for(int col = 0; col < GameBoard.kNumCols; col++)
            {
                //System.err.println(map[row][col].getType());
                //System.err.println(images.get(map[row][col].getType()));
                
                //SET terrain image from images
                board[row][col].setTerrain(images.get(map[row][col].getType()));
                
                Unit unit = map[row][col].getUnit();
                //IF there is a unit on the terrain
                if(unit != null)
                {
                    board[row][col].setUnit(images.get(unit.getType()));

                    //IF the unit is owned by Player 1
                    if(unit.getOwner() == Player.kPlayer1)
                    {
                        board[row][col].setTeamOverlay(images.get("OverlayPlayer1"));
                    }
                    //ELSE IF the unit is owned by Player 2
                    else if(unit.getOwner() == Player.kPlayer2)
                    {
                        board[row][col].setTeamOverlay(images.get("OverlayPlayer2"));
                    }
                    board[row][col].setGreyed(!(unit.canAttack() ||
                        unit.getCurrentMoves() > 0));
                }
                else
                {
                    board[row][col].setUnit(null);
                    board[row][col].setTeamOverlay(null);
                    board[row][col].setGreyed(false);
                }
            }
        }

        //CALL validate
        validate();
        //CALL repaint
        repaint();
    }
    
    /**
    * Overlays the images in src on top of one another, with the lowest indexed
    * image being the base and every subsequent image being laid on top of the
    * one before it.
    *
    * @pre all images, in src and the dest image, must be the same size
    * @param dest an image that will be overwritten by the new overlaid image
    * @param src a list of images to be overlaid
    * @return the resulting image after all images in src have been overlaid
    */
    public BufferedImage overlayImages(BufferedImage dest, ArrayList<BufferedImage> src)
    {
        //FOR each image in src
        for(int imgIndex = 0; imgIndex < src.size(); imgIndex++)
        {
            //FOR every column of pixels in dest
            for(int col = 0; col < kImageSize; col++)
            {
                //FOR every row of pixels in dest
                for(int row = 0; row < kImageSize; row++)
                {
                    //SET rgb to rgb value of current image
                    int rgb = src.get(imgIndex).getRGB(row, col);
                    
                    //System.err.println("RGB!: " + rgb);

                    //IF rgb is not the transparent color THEN
                    if (rgb != kTransparentColor)
                    {
                        //SET rgb of dest at row and col to rgb
                        dest.setRGB(row, col, rgb);
                    //END IF
                    }
                //END FOR
                }
            //END FOR
            }
        //END FOR
        }
        //RETURN dest
        return dest;
    }

    /**
     * A private inner class that maintains the list of images needed
     * for each cell.
     */
    private class BoardCell
    {
        private BufferedImage unitImg;
        private BufferedImage terrainImg;
        private BufferedImage selectImg;
        private BufferedImage teamOverlayImg;

        private BufferedImage dispImg;
        private boolean isValid;
        private boolean isGreyed;
        private boolean isHighlight;

        public BoardCell(BufferedImage unit, BufferedImage teamOverlay,
            BufferedImage terrain)
        {
            unitImg = unit;
            teamOverlayImg = teamOverlay;
            terrainImg = terrain;
            selectImg = null;

            isHighlight = false;
            isValid = false;
            isGreyed = false;
        }

        public boolean isSelected()
        {
            return (selectImg != null);
        }

        public void removeSelection()
        {
            //IF the image has a selection overlay
            if (selectImg != null && !isHighlight)
            {
                isValid = false;
                selectImg = null;
            }
        }
        public void removeHighlight()
        {
            //IF the image has a highlight overlay
            if (selectImg != null && isHighlight)
            {
                isValid = false;
                selectImg = null;
                isHighlight = false;
            }
        }
        public void setSelection()
        {
            //IF the unit is not already selected
            if (selectImg != images.get("Select"))
            {
                isValid = false;
                selectImg = images.get("Select");
                isHighlight = false;
            }
        }
        public void setHighlight()
        {
            //IF the unit is not already highlighted
            if (selectImg != images.get("Highlight"))
            {
                isValid = false;
                selectImg = images.get("Highlight");
                isHighlight = true;
            }
        }

        public void setGreyed(boolean newGreyed)
        {
            //IF the image is being changed to or from gray
            if (newGreyed != isGreyed)
            {
                isValid = false;
                isGreyed = newGreyed;
            }
        }

        public void setUnit(BufferedImage newUnit)
        {
            // IF the unit being set is different than what is already there
            if (newUnit != unitImg)
            {
                isValid = false;
                unitImg = newUnit;
            }
        }

        public void setTeamOverlay(BufferedImage newTeamOverlay)
        {
            //IF the new team overlay is different from the old one
            if (newTeamOverlay != teamOverlayImg)
            {
                isValid = false;
                teamOverlayImg = newTeamOverlay;
            }
        }

        public void setTerrain(BufferedImage newTerrain)
        {
            //IF the new terrain is different from the old terrain
            if (newTerrain != null && newTerrain != terrainImg)
            {
                isValid = false;
                terrainImg = newTerrain;
            }
        }

        //Terrain should never! be null!
        public BufferedImage getDispImage()
        {
            ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
            BufferedImageOp op;

            //IF the image is invalid
            if (!isValid)
            {
                //IF there is a terrain image
                if (terrainImg != null)
                {
                    dispImg = (BufferedImage)createImage(terrainImg.getWidth(),
                        terrainImg.getHeight());
                }
                else
                {
                    return null;
                }
                //IF there is a terrain image
                if (terrainImg != null)
                {
                    imgs.add(terrainImg);
                }

                //IF there is a unit image
                if (unitImg != null)
                {
                    //IF the unit should be greyed
                    if (isGreyed)
                    {
                        op = new ColorConvertOp(ColorSpace.getInstance(
                            ColorSpace.CS_GRAY), null);
                        unitImg = op.filter(unitImg, null);
                    }
                    imgs.add(unitImg);
                }
                //IF there is a team overlay image
                if (teamOverlayImg != null)
                {
                    imgs.add(teamOverlayImg);
                }
                //IF there is a selection image
                if (selectImg != null)
                {
                    imgs.add(selectImg);
                }

                dispImg = overlayImages(dispImg, imgs);
            }

            isValid = true;
            return dispImg;
        }
    }
    /**
     * An inner class that overrides the default table model being used
     */
    private class MyDefaultTableModel extends DefaultTableModel
    {
        @Override
        public boolean isCellEditable(int row, int col)
        {
            return false;
        }
    }

    /**
     * An inner class that overrides the default table cell renderer
     */
    private class ImageRenderer extends DefaultTableCellRenderer
    {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column)
        {
            JLabel rtn = new JLabel();
            BufferedImage dispImg;

            //IF the given object is a board cell
            if ((value instanceof BoardCell))
            {
                dispImg = ((BoardCell)value).getDispImage();
                //IF there is a display image
                if (dispImg != null)
                {
                    rtn.setIcon(new ImageIcon(dispImg));
                    return rtn;
                }
                else
                {
                    return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                }

            }
            else
            {
                return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            }
        }
    }
    /**
     * An inner class that overrides the MouseAdapter
     */
    private class MyMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent ev)
        {
            int col = gameTable.getSelectedColumn();
            int row = gameTable.getSelectedRow();

            // Is it a left mouse click?
            if (SwingUtilities.isLeftMouseButton(ev))
            {
                //Fixes defect #212 by adding 1 to image size
                row = (int) (ev.getPoint().getY()/(kImageSize + 1));
                col = (int) (ev.getPoint().getX()/(kImageSize + 1));

                //Fixes defect #231
                //IF clicked row is above bounds
                if (row >= GameBoard.kNumRows)
                {
                    //SET row to 1 less than kNumRows
                    row = GameBoard.kNumRows - 1;
                }
                //ELSE IF clicked row is below bounds
                else if (row < 0)
                {
                    //SET row to 0
                    row = 0;
                }

                //IF clicked col is above bounds
                if (col >= GameBoard.kNumCols)
                {
                    //SET col to 1 less than kNumCols
                    col = GameBoard.kNumCols - 1;
                }
                //ELSE IF clicked col is below bounds
                else if (col < 0)
                {
                    //SET col to 0
                    col = 0;
                }
                
                //IF gameView is a SwingGameView
                if (gameView instanceof SwingGameView)
                {
                    String action = PlayerAction.kSelect.toString() +
                        " " + row + " " + col;
                    gameView.acceptAction(action);
                }
                else
                {
                    String action = EditorAction.kSelectCell.toString() +
                        " " + row + " " + col;
                    gameView.acceptAction(action);
                }
            }
        }
    }
}
