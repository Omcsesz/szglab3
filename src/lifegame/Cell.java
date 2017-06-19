package lifegame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Omar
 */
public class Cell extends Coordinate {
    public int x;
    public int y;
    /**
     * type of cell
     */
    public char type;
    /** */
    public boolean kilove;
    private Color color;
    // ...
    public LifeGameModel model;

    /**
     * 
     * @param model
     * @param x
     * @param y
     */
    public Cell(LifeGameModel model, int x, int y) {
        super(-1, -1);
        this.x = x;
        this.y = y;
        this.model = model;
        kilove = false;
    }

    /**
    *
    */
    public void shoot() {
        kilove = true;
        setColor();
    }

    /**
     * 
     * @param type
     */
    public void setType(char type) {
        this.type = type;
        setColor();
    }

    /**
     * 
     * @return
     */
    public char getType() {
        return model.lifeSpace[row][column];
    }

    /**
    * 
    */
    public void setColor() {
        if (getType() == LifeGameModel.FULL) {
                this.color = Color.red;
        } else if (getType() == LifeGameModel.EMPTY) {
                this.color = Color.blue;
        }
    }

    /**
     * kirajzolja az adott cellat
     * @param g
     */
    public void draw(Graphics g) {
        /* beallitja a cella szinet a tipusnak megfeleloen */
        setColor();

        // beallitja a korvonal szinet
        g.setColor(Color.BLACK);

        // kirajzolja a keret�t
        g.drawRect(x, y, 25, 25);

        // beallitja a cella szinet a nezetben
        g.setColor(color);

        //kitolti a cellat
        g.fillRect(x + 1, y + 1, 25 - 1, 25 - 1);
    }
}