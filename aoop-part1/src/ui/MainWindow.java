package ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSlider;

import country.Map;

public class MainWindow extends JFrame {
	public MainWindow() {
		super("Main Window");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		Map map = new Map();// Map instance

		//add statistic instance
		Statistics stat = new Statistics(map, this);
		
		// add MapDrawing instance
		drawMap = new MapDrawing(map, stat);
		// add Menu instance
		menu = new Menu(this, stat, map, drawMap);
//		 add JSlider instance
		slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

//		slider.addChangeListener(new ChangeListener c);

		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		this.add(menu);
		this.add(drawMap);
		this.add(slider);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	
//	public void stateChanged(ChangeEvent e) {
//	    JSlider source = (JSlider)e.getSource();
//	    if (!source.getValueIsAdjusting()) {
//	        int fps = (int)source.getValue();
//	        if (fps == 0) {
//	            if (!frozen) stopAnimation();
//	        } else {
//	            delay = 1000 / fps;
//	            timer.setDelay(delay);
//	            timer.setInitialDelay(delay * 10);
//	            if (frozen) startAnimation();
//	        }
//	    }
//	}

//	private final JFrame frame;// main window frame
	private final Menu menu;// Menu object for main window
	private final MapDrawing drawMap;// MapDrawing panel object for main window
	private final JSlider slider;// simulation speed slider for main window
	
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15;    //initial frames per second
}
