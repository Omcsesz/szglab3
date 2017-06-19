package lifegame;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Omar
 */
public class LifeGameModel {
    private final int NUMBER_OF_ROWS = 24;
    private final int NUMBER_OF_COLUMNS = 24;
    public static final char EMPTY = ' '; // empty cell
    public static final char FULL = 'O'; // living cell
    private final char DIES = 'x'; // cell dies
    private final char BIRTH = 's'; // cell births
    // ...
    public int width = 700;
    public int height = 650;
    // ...
    private Table table = null;
    public char[][] lifeSpace;
    
    /**
     * variables needed for statistics
     */
    public ArrayList<Integer> statistics;
    
    /**
     * 
     */
    public LifeGameModel() {
        lifeSpace = new char[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        table = new Table(this, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
    }
    
    /**
     * Returns table
     * @return the table
     */
    public Table getTable() {
        return table;
    }
    
    /**
     * Initializes the table by creating an empty table
     */
    public void createEmptyTable() {
        int i, j;
        
        for (i = 0; i < NUMBER_OF_ROWS; i++) {
            for (j = 0; j < NUMBER_OF_COLUMNS; j++) {
                lifeSpace[i][j] = EMPTY;
            }
        }
        
        initStat();
    }
    
    /**
     * Creates a simple arrangement (used for JUnit test)
     */
    public void initSimple() {
        createEmptyTable();
        
        lifeSpace[3][3] = FULL;
        lifeSpace[3][4] = FULL;
        lifeSpace[3][5] = FULL;
    }
    
    /**
     * Reads a game setup from File
     */
    public void readFromFile() {
        int i, j;
        char c;
        FileInputStream fileInputStream = null;
        
        createEmptyTable();
        try {
            File file = new File("conway.txt");
            fileInputStream = new FileInputStream(file);
            
            System.out.println(">>>Read from file<<<");
            for (i = 0; i < NUMBER_OF_ROWS; i++) {
                for (j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    //reading a single cell
                    c = (char) (fileInputStream.read());
                    if (c < '0') {
                        c = EMPTY;
                    } else {
                        c = FULL;
                    }
                    lifeSpace[i][j] = c;
                }
                c = (char) (fileInputStream.read());
            }
            
            fileInputStream.close();
        } catch (IOException exception) {}
        
        System.out.println("Finished reading file.");
    }
    
    /**
     * Creates a table by randomly putting cells
     */
    public void generateRandomTable() {
        int i, j;
        Random rnd = new Random();
        
        createEmptyTable();
        
        System.out.println(">>>generateRandomTable<<<");
        
        for (i = 0; i < NUMBER_OF_ROWS; i++) {
            for (j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (rnd.nextInt(2) < 1) {
                    lifeSpace[i][j] = EMPTY;
                } else {
                    lifeSpace[i][j] = FULL;
                }
            }
        }
    }
    
    /**
     * Saves current table
     s*/
    public void saveCurrentTable() {
        FileOutputStream fileOutputStream;
        int i, j;
        
        try {
            File file = new File("conway.txt");
            fileOutputStream = new FileOutputStream(file);
            
            System.out.println(">>>saveCurrentTable<<<");
            for (i = 0; i < NUMBER_OF_ROWS; i++) {
                for (j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    fileOutputStream.write(lifeSpace[i][j]); //printing lifeSpace
                }
                fileOutputStream.write('\n');
            }
            fileOutputStream.close();
            System.out.println("ENd of saveCurrentTable.");
        } catch (IOException ex) {}
    }
    
    /**
     * Makes the table "infinite": the left and right end, and the top and bottom are connected.
     * @param i
     * @param j
     * @return
     */
    public char get_ter(int i, int j) {
        if (i < 0) {
            i = NUMBER_OF_ROWS - 1;
        }
        if (i > NUMBER_OF_ROWS - 1) {
            i = 0;
        }
        if (j < 0) {
            j = NUMBER_OF_COLUMNS - 1;
        }
        if (j > NUMBER_OF_COLUMNS - 1) {
            j = 0;
        }
        
        return lifeSpace[i][j];
    }
    
    /**
     * Returns the neighboring cell
     * @param i a cella sorindexe
     * @param j a cella oszlopindexe
     * @param k hanyadik szomszedot akarjuk
     * @return  neighbourCells
     */
    public char neighbourCells(int i, int j, int k) {
        if (k == 1) // bal felso szomsz�d
        {
            return get_ter(i - 1, j - 1);
        }
        else if (k == 2) // felso
        {
            return get_ter(i - 1, j + 0);
        }
        else if (k == 3) // jobb felso
        {
            return get_ter(i - 1, j + 1);
        }
        else if (k == 4) // jobb oldali
        {
            return get_ter(i + 0, j + 1);
        }
        else if (k == 5) // jobb also
        {
            return get_ter(i + 1, j + 1);
        }
        else if (k == 6) // also
        {
            return get_ter(i + 1, j + 0);
        }
        else if (k == 7) // bal also
        {
            return get_ter(i + 1, j - 1);
        } 
        else if (k == 8) // baloldali
        {
            return get_ter(i + 0, j - 1);
        } 
        else {
            return EMPTY; // erre az agra nem kerul sor
        }
    }
    
    /**
     * Returns the number of living cells neighboring this cell
     * @param x
     * @param y
     */
    public int numberOfNeighbouringLivingCells(int x, int y) {
        int k;
        int sum; // osszeg
        
        sum = 0;
        for (k = 1; k <= 8; k++) {
            // s= neighbourCells(lifeSpace, i, j, k);
            if (neighbourCells(x, y, k) == FULL || neighbourCells(x, y, k) == DIES) {
                sum = sum + 1;
            }
        }
        return sum;
    }
    
    /**
     * Calculates the next step
     */
    public void nextStep() {
        int numberOfNeighbours;
        int x, y;
        
        /**
         * we check all cells         
         */
        for (x = 0; x < NUMBER_OF_ROWS; x++) {
            for (y = 0; y < NUMBER_OF_COLUMNS; y++) {
                numberOfNeighbours = numberOfNeighbouringLivingCells(x, y);
                if (numberOfNeighbours == 3) {// a new cell is born
                    if (lifeSpace[x][y] == EMPTY) {
                        lifeSpace[x][y] = BIRTH;
                    }
                } else if (numberOfNeighbours == 2 || numberOfNeighbours == 3) {
                    // lives through
                } else {
                    if (lifeSpace[x][y] == FULL) {
                        lifeSpace[x][y] = DIES; // we mark it as DYING
                    }
                }
            }
        }
        
        /**
         * we change them accordingly
         */
        for (x = 0; x < NUMBER_OF_ROWS; x++) {
            for (y = 0; y < NUMBER_OF_COLUMNS; y++) {
                if (lifeSpace[x][y] == DIES) {
                    lifeSpace[x][y] = EMPTY;
                }
                if (lifeSpace[x][y] == BIRTH) {
                    lifeSpace[x][y] = FULL;
                }
            }
        }
    }
    
    /**
     * ellenorzi a cellak allapotat
     */
    public void checkState() {
        nextStep(); //ellenorzi a cellakat
        addStat(); //hozzaadja a statisztikahoz az eredmenyt
    }
    
    /**
     * Draws table
     * @param g
     */
    public void draw(Graphics g) {
        getTable().draw(g);
    }
    
    /**
     * Creates statistics for the game
     */    
    public void initStat() {
        statistics = new ArrayList<>();
    }
    
    /**
     * Saves the number of living cells in the current configuration
     */
    public void addStat() {
        statistics.add(table.cellCount(FULL));
    }
    
    /**
     * Prints the statistics containing the number of living cells
     */
    public void printStat() {
        System.out.println("LifeGame statistics");
        
        for (int i = 0; i < statistics.size(); i++) {
            System.out.println(statistics.get(i));
        }
    }
}