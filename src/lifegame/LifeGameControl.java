package lifegame;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;

/**
 * communicates between model and view, coordinates the simulation's run, 
 * handles events
 * @author Omar
 */
public final class LifeGameControl extends WindowAdapter{

    private LifeGameModel lifeGameModel; //az lifeSpace szimulaciojanak a  modellezese, es kirajzolasa
    private LifeGameView lifeGameView; //megjelenites
    private MyThread mt; //utemezes kezelese
    private boolean ableToRun = false; //futast vezerlo flag

    /**
     * letrehozza a modellt, a nezetet, es elinditja a szimulaciot
     */
    public LifeGameControl() {
        lifeGameModel = new LifeGameModel(); //creates model
        lifeGameModel.createEmptyTable(); //feltolti az ures tablat
        //++++++++++++++++
        lifeGameModel.generateRandomTable(); //letrehoz veletlenszeruen egy jatekallast
        //++++++++++++++++
        lifeGameView = new LifeGameView(this); //letrehozza a megjelenitot
        lifeGameView.setVisible(true);

        mt = new MyThread(this); //letrehozza az utemezot
        mt.start();   //elinditja a szalat

        startSimulation(); //elinditja a szimulacio futasat
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    /**
     * Returns height of table
     * @return height of table
     */
    public int getWidth() {
        return lifeGameModel.width;
    }

    /**
     * Returns width of table
     * @return width of table
     */
    public int getHeight() {
        return lifeGameModel.height;
    }

    /**
     * Draws table
     * @param g
     */
    public void draw(Graphics g) {
        lifeGameModel.draw(g);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * Stops simulation, and empties table
     */
    public void stopSimulation() {
        ableToRun = false;

        lifeGameModel.createEmptyTable();

        do_repaint();
    }

    /**
     * Pauses simulation
     */
    public void pauseSimulation() {
        ableToRun = false;
        lifeGameModel.printStat();
    }

    /**
     * Sets ableToRun to true
     * 
     */
    public void startSimulation() {
        ableToRun = true;
    }

    /**
     * fut-e a jatek
     * @return
     */
    public boolean isRunning() {
        return ableToRun;
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * stops current simulation, and generates a new start setup
     */
    public void generate() {
        stopSimulation();
        lifeGameModel.generateRandomTable();
    }

    /**
     * readSetupFromFile egy fajlbol egy elmentett jatekallast: eloszor leallitja az eppen
 futo jatekot, majd beolvassa a jatekallast
     */
    public void readSetupFromFile() {
        stopSimulation();
        lifeGameModel.readFromFile();
    }

    /**
     * a Stop! gomb allapotatol fuggoen indul, vagy megall a jatek
     * @param leptet ettol fuggoen fut, vagy all meg a jatek
     */
    public void fut(boolean leptet) {
        if (leptet) {
            startSimulation();
        } else {
            pauseSimulation();
        }
    }

    /**
     *megallitja a szimulaciot, es lementi egy fajlba az aktualis allast
     */
    public void mentes() {
        pauseSimulation();
        lifeGameModel.saveCurrentTable();
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

     /**
     *elelnorzi az osszes cella allapotat
     */
    public void checkState() {
        lifeGameModel.checkState();
        do_repaint();
    }

    /**
     *ujrarajzolja a tablat
     */
    public void do_repaint() {
        if (lifeGameView != null) {
            lifeGameView.do_repaint();
        }
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    class MyThread extends Thread {     //szakezeles

        LifeGameControl control;

        public MyThread(LifeGameControl control) {
            this.control = control;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (control.isRunning()) {
                        control.checkState();
                    }
                    sleep(500);
                }
            } catch (InterruptedException e) {
            }
        }
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}
