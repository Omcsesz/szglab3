package lifegame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Omar
 */
public class Table {

    /** 
     * number of rows
     */
    public int numberOfRows = 10;
    /** 
     * number of columns
     */
    public int numberOfColumns = 10;
    /**
     *  array of cells
     */
    private Cell cells[][] = null;
    //...
    public LifeGameModel model;

    /**
     * constructor
     * @param model
     * @param numberOfRows
     * @param numberOfColumns
     */
    public Table(LifeGameModel model, int numberOfRows, int numberOfColumns) {
    	this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.model = model;

        int x, y;

        cells = new Cell[numberOfRows][numberOfColumns];

        for (x = 0; x < numberOfRows; x++) {
            for (y = 0; y < numberOfColumns; y++) {
                cells[x][y] = new Cell(model, x * 25, y * 25);
                cells[x][y].column = y;
                cells[x][y].row = x;
            }
        }
    }

    /**
     * Returns the cell at the specified coordinate
     * @param x horizontal coordinate of the cell
     * @param y vertical coordinate of the cell
     * @return
     */
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * Draws table
     * @param g
     */
    public void draw(Graphics g) {
        int x, y;
        g.setColor(Color.BLACK);
        g.drawString("LifeGame", 300, 15);

        g.setColor(Color.BLACK);

        for (x = 0; x < numberOfRows; x++) {
            for (y = 0; y < numberOfColumns; y++) {
                cells[x][y].draw(g);
            }
        }
    }

    /**
     * Returns the number of cellType cells
     * @param cellType
     * @return the number of cells of the specified type
     */
    public int cellCount(char cellType) {
        int x, y;       
        int numberOfFullCells;

        numberOfFullCells = 0;
        for (x = 0; x < numberOfRows; x++) {
            for (y = 0; y < numberOfColumns; y++) {
                if (cells[x][y].getType() == cellType) {
                    numberOfFullCells = numberOfFullCells + 1;
                }
            }
        }

        return numberOfFullCells;
    }
}
