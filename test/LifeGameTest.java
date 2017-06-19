import junit.framework.Assert;
import lifegame.LifeGameModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Omar
 */
public class LifeGameTest {

    LifeGameModel lg = null;

    public LifeGameTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lg = new LifeGameModel();
        lg.initSimple();
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    /**
     *
     */
    @Test
    public void test_Get_ter() {
        char c = lg.get_ter(3, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     *
     */
    @Test
    public void test_szomszed() {
        char c = lg.neighbourCells(3, 4, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     * teszteljük, hogy a megadott cellában tényleg a megfelelo allapotu sejt
     */
    @Test
    public void test_szomszeddb() {
        int db = lg.numberOfNeighbouringLivingCells(3, 4);
        Assert.assertEquals(db, 2);
    }

    /**
     *
     */
    @Test
    public void test_lep() {
        char c;
        lg.nextStep();
        c = lg.get_ter(4, 4);
        Assert.assertEquals(c, LifeGameModel.FULL);
    }

    /**
     * teszteli, hogy megfelelo tipusu-e az adott Cell
     */
    @Test
    public void test_getCella() {
        char c = lg.getTable().getCell(3, 4).getType();
        Assert.assertEquals(c, LifeGameModel.FULL);
    }
}
