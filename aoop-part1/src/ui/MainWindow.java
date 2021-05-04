package ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import country.Map;
import simulation.Main;

public class MainWindow extends JFrame {
	public MainWindow(Map map) {
		super("Main Window");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		

		//add statistic instance
		Statistics stat = new Statistics(map, this);
		
		// add MapDrawing instance
		drawMap = new MapDrawing(map, stat);

//		 add JSlider instance
		slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Main.setSleepTime(slider.getValue());
			}
		});
		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		// add Menu instance
//		menu = new Menu(this, stat, map, drawMap, slider);
		menu = new Menu(this, stat, drawMap, map);
		this.add(menu);
		this.add(drawMap);
		this.add(new JScrollPane(drawMap));
		this.add(slider);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private final Menu menu;// Menu object for main window
	private final MapDrawing drawMap;// MapDrawing panel object for main window
	private final JSlider slider;// simulation speed slider for main window
	
	
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 10;
	static final int FPS_INIT = 5;    //initial frames per second
}
