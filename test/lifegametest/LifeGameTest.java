package lifegametest;

import org.junit.Assert;
import org.junit.Test;
import lifegame.LifeGameModel;
import org.junit.Before;

/**
 *
 * @author Omar
 */
public class LifeGameTest {

    LifeGameModel lg = null;

    @Before
    public void setUp() {
        lg = new LifeGameModel();
        lg.initSimple();
    }


    /**
     *teszteli a get_ter() fuggvenyt
     */
    @Test
    public void test_get_ter() {
        char c = lg.get_ter(3, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     *teszteli a neighbourCells() fuggvenyt
     */
    @Test
    public void test_szomszed() {
        char c = lg.neighbourCells(3, 4, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     * tesztelj�k, hogy a megadott cell�ban t�nyleg a megfelelo allapotu sejt
     */
    @Test
    public void test_szomszeddb() {
        int db = lg.numberOfNeighbouringLivingCells(3, 4);
        Assert.assertEquals(db, 2);
    }

    /**
     *teszteli a get_ter() fuggvenyt: ha j�l mukodik a (3, 4) pozicioban FULL-nek kell lenni
     */
    @Test
    public void test_lep() {
        char c;
        
        lg.nextStep();
        c = lg.get_ter(4, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     *teszteli, hogy megfelelo tipusu-e az adott Cell
     */
    @Test
    public void test_getCella() {
        char c = lg.getTable().getCell(3, 4).getType();
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

}//class LifeGameTest vege
