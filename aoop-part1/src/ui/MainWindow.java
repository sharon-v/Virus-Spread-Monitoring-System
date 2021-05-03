package ui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import country.Map;
import simulation.Clock;

public class MainWindow extends JFrame {
	public MainWindow() {
		super("Main Window");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		playFlag = false;// currently simulation is not playing
		loadFlag = false;// currently map is not loaded
		map = new Map();// Map instance

		//add statistic instance
		Statistics stat = new Statistics(map, this);
		
		// add MapDrawing instance
		drawMap = new MapDrawing(map, stat);

//		 add JSlider instance
		slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

//		slider.addChangeListener(new ChangeListener c);

		//Turn on labels at major tick marks.
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		// add Menu instance
		menu = new Menu(this, stat, map, drawMap, slider);
		this.add(menu);
		this.add(drawMap);
		this.add(new JScrollPane(drawMap));
		this.add(slider);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	
	public static void playSimu() {
		if(playFlag == false) {
			playFlag = true;
			while(loadFlag == true){
				if (playFlag == true) {
					try {
						map.executeSimulation(); // third stage
						Clock.nextTick();
						System.out.println("ticks : " + Clock.now());

						System.out.println(slider.getValue());
						Thread.sleep(slider.getValue()*1000);
					} catch (Exception ex) {
						System.out.println("an unexpected ERROR has occurred :(");
						ex.printStackTrace();
					}
				}
			}
		}
	}

//	private final JFrame frame;// main window frame
	private final Menu menu;// Menu object for main window
	private final MapDrawing drawMap;// MapDrawing panel object for main window
	private final JSlider slider;// simulation speed slider for main window
	private final Map map;
	
	private static boolean playFlag;
	private static boolean loadFlag;
	
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 10;
	static final int FPS_INIT = 5;    //initial frames per second
}
