package lifegame;

/**
 *
 * @author Omar
 */
public class Coordinate {
    public int row;
    public int column;

    /**
     * Public constructor
     */
    public Coordinate() {
        this.row = -1;
        this.column = -1;
    }

    /**
     *
     * @param row
     * @param column
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }
}