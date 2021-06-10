package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.CyclicBarrier;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import country.Map;
import simulation.Clock;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class MainWindow extends JFrame {

	/**
	 * constructor
	 * 
	 * @param map - Map object for Settlements information
	 */
	public MainWindow() {
		super("Main Window");// call parent constructor
		map = new Map();// Map instance
		this.setLayout(new BorderLayout(10, 0));

		// add statistic instance
		stat = new Statistics(map);

		// add MapDrawing instance
		drawMap = new MapDrawing(map, stat);

		// add JSlider instance
		slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		// Slider labels
		labelTable.put(FPS_MIN, new JLabel("Fast"));
		labelTable.put(7, new JLabel("Simulation Speed"));
		labelTable.put(FPS_MAX, new JLabel("Slow"));
		slider.setLabelTable(labelTable);


		// labels and tick markings
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		menu = new Menu();// create a Menu object
		// add to Frame
		this.add(menu, BorderLayout.NORTH);
		this.add(drawMap, BorderLayout.CENTER);
		this.add(slider, BorderLayout.SOUTH);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}


	//===============================================
	//private class menu
	private class Menu extends JMenuBar {

		/**
		 * constructor
		 * 
		 * @param st   - Statistics window
		 * @param draw - MapDrawing window
		 * @param map  - Map Object
		 */
		public Menu() {
			// set fields
			mutation = new Mutations();
			ticks = new JLabel("Ticks: " + Clock.now() + "         ");
			ticks.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			ticks.setFont(new Font("", Font.BOLD, 15));
			ticks.setForeground(Color.MAGENTA.brighter());

			m_file = new MFile();
			m_simulation = new Simulation();
			m_help = new Help();
			m_originator = new Originator();
			m_careTaker = new CareTaker(m_originator);

			// add menu options to bar
			this.add(m_file);
			this.add(m_simulation);
			this.add(m_help);
			add(Box.createHorizontalGlue());
			this.add(ticks);

		}

		public void updateTicksLable() {
			ticks.setText("Ticks: " + Clock.now() +  "         " );
		}

		/**
		 * 
		 * inner Class for handling work with the File
		 *
		 */
		private class MFile extends JMenu {

			/**
			 * constructor
			 */
			public MFile() {
				super("File");// call parent constructor
				JMenuItem load = new JMenuItem("Load");
				JMenuItem stats = new JMenuItem("Statistics");
				JMenuItem edit = new JMenuItem("Edit Mutations");
				JMenuItem log = new JMenuItem("Save Log File");
				JMenuItem undo = new JMenuItem("Undo Log File Path");
				JMenuItem exit = new JMenuItem("Exit");
				// add to file
				this.add(load);
				this.add(stats);
				this.add(edit);
				this.add(log);
				this.add(undo);
				this.add(exit);

				/**
				 * load menu option actions
				 */
				load.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (map.getLoadFlag() == false) {
							Clock.reset();// reset current time to 0
							// Create a file chooser
							final JFileChooser fc = new JFileChooser();
							// opens in current directory
							File workingDirectory = new File(System.getProperty("user.dir"));
							fc.setCurrentDirectory(workingDirectory);
							// In response to a button click:
							int returnVal = fc.showOpenDialog(load);
							fc.setDialogTitle("Select File to Open");
							String path = null;
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								path = fc.getSelectedFile().getAbsolutePath();
							}

							if (path != null) {
								map.setLoadFlag(true);
								map.loadInfo(path);
								map.intialization();// second stage
								drawMap.repaint();
								drawMap.updateStatWindow();
								CyclicBarrier temp = new CyclicBarrier(map.getMapSize(), new Runnable(){
									@Override
									public void run(){
										SwingUtilities.invokeLater(new Runnable() {
											public void run() {
												drawMap.repaint();
												drawMap.updateStatWindow();
											}
										});
										System.out.println("ticks : " + Clock.now());
										updateTicksLable();
										Clock.nextTick();
										try {
											Thread.sleep(slider.getValue() * 1000);
										} catch (InterruptedException ex) {
											System.out.println("an unexpected ERROR has occurred :(");
											ex.printStackTrace();
										}
									}

								});
								map.setCyclic(temp);
								map.createThreads();
							}
						}
					}
				});

				/**
				 * statistics menu option actions
				 */
				stats.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (map.getLoadFlag() == false) {
							JOptionPane.showMessageDialog(stat, "No file has been loaded", "Inane warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						stat.showDialog();
					}
				});

				/**
				 * edit mutations menu option actions
				 */
				edit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						mutation.showDialog();
					}
				});

				/**
				 * log file menu option actions
				 */
				log.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!map.getLoadFlag()) {
							JOptionPane.showMessageDialog(load, "No file has been loaded", "Inane warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						// Create a file chooser
						final JFileChooser fc = new JFileChooser();
						// In response to a button click:
						int returnVal = fc.showOpenDialog(log);
						fc.setDialogTitle("Select File to Open");
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							String path = fc.getSelectedFile().getAbsolutePath() + ".log";
							//save to previous path
							m_careTaker.save();
							m_originator.setOriginator(path);

							// create the empty file
							File file = new File(path);
							try {
								file.createNewFile();
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (SecurityException e2) {
								e2.printStackTrace();
							}
							map.setLogPath(path);
							map.setLogFlag(true);
						}
					}

				});

				undo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						m_careTaker.undo();
						map.setLogPath(m_originator.getpath());
					}
				});

				/**
				 * exit menu option, closes the program
				 */
				exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
			}
		}


		/**
		 * 
		 * inner class for handling Simulations
		 *
		 */
		private class Simulation extends JMenu {
			/**
			 * constructor
			 */
			public Simulation() {
				super("Simulation");// call the parent constructor
				JMenuItem play = new JMenuItem("Play");
				JMenuItem pause = new JMenuItem("Pause");
				JMenuItem stop = new JMenuItem("Stop");
				JMenuItem setTicks = new JMenuItem("Set Ticks Per Day");
				// add to file
				this.add(play);
				this.add(pause);
				this.add(stop);
				this.add(setTicks);

				/**
				 * play menu option actions
				 */
				play.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (map.getLoadFlag() == true && map.getPlayFlag() == false) {
							synchronized (map) {
								map.setPlayFlag(true);
								map.notifyAll();
							}
							JOptionPane.showMessageDialog(play, "Simulation Resumed");
						} else
							JOptionPane.showMessageDialog(play, "No file has been loaded", "Inane warning",
									JOptionPane.WARNING_MESSAGE);

					}
				});

				/**
				 * pause menu option actions
				 */
				pause.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (map.getPlayFlag() == true && map.getLoadFlag() == true) {
							synchronized (map) {
								map.setPlayFlag(false);
							}
							// countinue
							JOptionPane.showMessageDialog(pause, "Simulation Paused");
						} else
							JOptionPane.showMessageDialog(pause, "Not currently playing", "Inane warning",
									JOptionPane.WARNING_MESSAGE);

					}
				});

				/**
				 * stop menu option actions
				 */
				stop.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (map.getLoadFlag() == true) {
							synchronized (map) {
								map.setLoadFlag(false);
								map.setPlayFlag(true);
								map.resetMap();
							}
							JOptionPane.showMessageDialog(stop, "Simulation Stopped \nPlease re-load in order to resume");
						} else
							JOptionPane.showMessageDialog(stop, "No file has been loaded", "Inane error",
									JOptionPane.ERROR_MESSAGE);
					}
				});

				/**
				 * set ticks per day menu options actions
				 */
				setTicks.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JDialog di = new JDialog();
						JPanel panel = new JPanel();
						panel.setLayout(new FlowLayout());
						JLabel lb = new JLabel("Set Ticks Per Day");
						SpinnerModel model = new SpinnerNumberModel(Clock.getTicksPerDay(), 0,
								Clock.getTicksPerDay() + 1000, 1);
						JLabel ticks = new JLabel("ticks per day is now = " + Clock.getTicksPerDay());
						JSpinner spinner = new JSpinner(model);
						spinner.addChangeListener(new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent e) {
								Clock.setTicksPerDay((int) spinner.getValue());
								ticks.setText("ticks per day is now = " + Clock.getTicksPerDay());

							}
						});

						JComponent editor = new JSpinner.NumberEditor(spinner);
						spinner.setEditor(editor);
						spinner.setSize(70, 30);
						panel.add(lb);
						panel.add(spinner);
						panel.add(ticks);
						di.add(panel);
						di.setSize(200, 150);
						di.setVisible(true);
					}
				});
			}
		}

		/**
		 * 
		 * inner Class for handling help menu
		 *
		 */
		private class Help extends JMenu {
			/**
			 * constructor
			 */
			public Help() {
				super("Help");// call parent constructor
				JMenuItem help = new JMenuItem("Help");
				JMenuItem about = new JMenuItem("About");
				// add to file
				this.add(help);
				this.add(about);

				/**
				 * help menu option actions
				 */
				help.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JDialog info = new JDialog((JFrame) null, "Information", true);
						String expl = "////////////////////////////////////\n" + "************\n"
								+ "Background: \n************\n this program was developed in order to keep track of the developement of the Virus and it's mutations in our country.\n"
								+ "************\n"
								+ "Contains: \n************\n Displays real-time Statistic data in a graphic, user friendly interface. "
								+ "in order to use the program you will need a pre-made .txt file that contains the countries information and the connections between Settlements in the following format: \n"
								+ "---Settlement information---\n 'Settlement Type; Settlement-Name; x-coardinate; y-coardinte; Settlement Width; Settlement Height; Population'\n"
								+ "---Connection---\n '#; First-Settlement-Name; Second-Settlement-Name;'\n"
								+ "////////////////////////////////////";

						StyleContext context = new StyleContext();
						StyledDocument document = new DefaultStyledDocument(context);

						Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
						StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
						StyleConstants.setFontSize(style, 14);
						StyleConstants.setSpaceAbove(style, 4);
						StyleConstants.setSpaceBelow(style, 4);

						try {
							document.insertString(document.getLength(), expl, style);
						} catch (BadLocationException badLocationException) {
							System.err.println("Oops");
						}

						JTextPane textPane = new JTextPane(document);
						textPane.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textPane);
						info.add(scrollPane, BorderLayout.CENTER);

						info.setSize(600, 400);
						info.setVisible(true);
					}
				});

				/**
				 * about menu option actions
				 */
				about.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JDialog info = new JDialog((JFrame) null, "About", false);
						JPanel panel = new JPanel(new GridLayout(4, 1));

						// define labels
						String expl = "Authors : Sharon Vazana, Yarden Hovav";
						String expl1 = "Date : May 2021";
						String expl2 = "Version : 1.2";
						String expl3 = "Platform : Java SE 16";
						JLabel text = new JLabel(expl);
						JLabel text1 = new JLabel(expl1);
						JLabel text2 = new JLabel(expl2);
						JLabel text3 = new JLabel(expl3);
						// align in center
						text.setHorizontalAlignment(CENTER);
						text1.setHorizontalAlignment(CENTER);
						text2.setHorizontalAlignment(CENTER);
						text3.setHorizontalAlignment(CENTER);

						// add text to panel
						panel.add(text);
						panel.add(text1);
						panel.add(text2);
						panel.add(text3);
						// add panel and set view
						info.add(panel);
						info.setSize(300, 200);
						info.setVisible(true);
					}
				});
			}
		}

		// fields

		private final MFile m_file;// file menu
		private final Simulation m_simulation;// simulation menu
		private final Help m_help;// help menu
		private JLabel ticks;
		private Mutations mutation;// mutation info
		private CareTaker m_careTaker;
		private Originator m_originator;

	}//end private class menu
	//===============================================


	// fields
	private final Menu menu;// Menu object for main window
	private final MapDrawing drawMap;// MapDrawing panel object for main window
	private final JSlider slider;// simulation speed slider for main window
	private Map map;// contains all Settlements information
	private Statistics stat;// contains the statistic data


	// slider parameters
	private final int FPS_MIN = 0;
	private final int FPS_MAX = 15;
	private final int FPS_INIT = 5;    //initial frames per second
}
