package lifegame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *kirajzolja a jatekot, kezeli a menut, es a szunetgombot
 * @author Omar
 */
public class LifeGameView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LifeGameControl control;  //ezen keresztul kapcsolodik a modelhez 
    //...
    private MyCanvas mc; //a tabla kirajzolasahoz
    private Image osImage; //segedkep a rajzolashoz
    private Graphics osGraphics; //seged grafikus kornyezet a rajzolashoz
    //...
    private JButton jbt; //leallitogomb

    /**
     * letrehozza a menut, a tablat, es a stopgombot; valamint beallitja a
     * listenereket
     *
     * @param control
     */
    public LifeGameView(LifeGameControl control) {
        super("LifeGame");
        this.control = control;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //menu letrehozasa
        JMenuBar menuBar = new JMenuBar();

        JMenu menuA = new JMenu("Fajl");

        //----------------------------------------------------
        JMenuItem menuBeolvasas = new JMenuItem("Beolvasas");
        //----------------------------------------------------
        menuBeolvasas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                beolvas();  //egy jatekallas beolvasasa 
            }
        });
        //----------------------------------------------------
        JMenuItem menuGeneralas = new JMenuItem("Generalas");
        //----------------------------------------------------
        menuGeneralas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                general();  //veletlenszeru jatekter letrehozasa
            }
        });
        //----------------------------------------------------

        menuA.add(menuBeolvasas);
        menuA.add(menuGeneralas);
        menuA.addSeparator();

        JMenuItem menuSzimIndit = new JMenuItem("Szimulalas inditasa");
        //----------------------------------------------------
        menuSzimIndit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fut(true);     //folyamatos szimulalas
            }
        });
        //----------------------------------------------------

        JMenuItem menuSzimLeallit = new JMenuItem("Szimulalas leallitasa");
        //----------------------------------------------------
        menuSzimLeallit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fut(false);     //lepesenkenti szimulalas
            }
        });
        //----------------------------------------------------
        menuA.add(menuSzimIndit);
        menuA.add(menuSzimLeallit);

        menuA.addSeparator();

        JMenuItem menuMentes = new JMenuItem("Mentes");
        //----------------------------------------------------
        menuMentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mentes();     //az utolso allapot elmentese
            }
        });
        //----------------------------------------------------
        menuA.add(menuMentes);

        menuA.addSeparator();

        JMenuItem menuKilep = new JMenuItem("Kilepes");
        menuKilep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0); //kilepes a programbol
            }
        });
        menuA.add(menuKilep);

        menuBar.add(menuA);

        this.setJMenuBar(menuBar);
        //----------------------------------------------------------------------

        //tabla hozzaadasa
        mc = new MyCanvas();
        mc.setSize(control.getWidth(), control.getHeight());
        add(mc, BorderLayout.EAST);

        //gomb hozzaadasa
        jbt = new JButton("Stop!");
        add(jbt, BorderLayout.PAGE_END);
        jbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_pause();
            }
        });

        pack();
    }

    /**
     * meghivatja a modell generalojat
     */
    public void general() {
        control.generate();
    }

    /**
     * beolvastatja az elmentett jatekallapotot
     */
    public void beolvas() {
        control.readSetupFromFile();
    }

    /**
     * fajlba menti a jatekallapotot
     */
    public void mentes() {
        control.mentes();
    }

    /**
     * az startSimulation allapotatol fuggoen elinditja, vagy leallitja a szimulaciot
     * @param indit ennek erteketol fugg, hogy a program inditaskor elkezd-e magatol futni
     */
    public void fut(boolean indit) {
        if (indit) {
            control.startSimulation();
            jbt.setText("Stop!");
        } else {
            control.pauseSimulation();
            jbt.setText("Start!");
        }
    }

    /**
     * a szimulacio allapotatol fuggoen elinditja, vagy leallitja azt
     */
    public void do_pause() {
        if (control.isRunning()) {
            control.pauseSimulation();
            jbt.setText("Start!");
        } else {
            control.startSimulation();
            jbt.setText("Stop!");
        }
    }//do_pause() vege

    /**
     * kikenyszeriti az ujrarajzolast
     */
    public void do_repaint() {
        mc.repaint();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * sajat Canvas osztaly a rajzolashoz
     */
    class MyCanvas extends Canvas {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void paint(Graphics g) {
            if (osGraphics == null) {
                osImage = createImage(control.getWidth(), control.getHeight());//letrehoz egy kepet a rajzolashoz
                osGraphics = osImage.getGraphics();//lekeri a kephez tartozo grafikus kornyezetet
            }
            Color c = new Color(0, 192, 64);
            osGraphics.setColor(c);
            osGraphics.fillRect(0, 0, control.getWidth(), control.getHeight());//feltolti a hatteret zoldre

            //-----------------------
            control.draw(osGraphics); //meghivatja a modell rajzolojat
            //-----------------------

            //miutan osszeallitotta a kepet, kirajzolja a kepernyore
            g.drawImage(osImage, 0, 0, this);
        }

        @Override
        public void update(Graphics g) {
            paint(g);//frissiteskor is meghivja arajzolot
        }
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------      

}//LifeGameView vege