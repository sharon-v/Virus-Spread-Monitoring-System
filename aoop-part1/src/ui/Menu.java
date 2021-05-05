package ui;


import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
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
import simulation.Main;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Menu extends JMenuBar {

	/**
	 * constructor
	 * 
	 * @param st   - Statistics window
	 * @param draw - MapDrawing windoe
	 * @param map  - Map Object
	 */
	public Menu(Statistics st, MapDrawing draw, Map map) {
		// set fields
		stat = st;
		mutation = new Mutations();
		this.map = map;
		myMapDraw = draw;

		m_file = new File();
		m_simulation = new Simulation();
		m_help = new Help();

		// add menu options to bar
		this.add(m_file);
		this.add(m_simulation);
		this.add(m_help);

	}

	/**
	 * 
	 * inner Class for handling work with the File
	 *
	 */
	private class File extends JMenu {

		/**
		 * constructor
		 */
		public File() {
			super("File");// call parent constructor
			JMenuItem load = new JMenuItem("Load");
			JMenuItem stats = new JMenuItem("Statistics");
			JMenuItem edit = new JMenuItem("Edit Mutations");
			JMenuItem exit = new JMenuItem("Exit");
			// add to file
			this.add(load);
			this.add(stats);
			this.add(edit);
			this.add(exit);

			/**
			 * load menu option actions
			 */
			load.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (Main.getLoadFlag() == false) {
						Clock.reset();// reset current time to 0
						// Create a file chooser
						FileDialog dialog = new FileDialog((JFrame) null, "Select File to Open");
						dialog.setMode(FileDialog.LOAD);
						dialog.setVisible(true);
						dialog.setLocationRelativeTo(null);

						String path = dialog.getFile();
						if (path != null) {
							map.loadInfo(path);
							map.intialization();// second stage
							myMapDraw.repaint();
							Main.setLoadFlag(true);

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
			 * exit menue option, closes the program
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
					if (Main.getLoadFlag() == true && Main.getPlayFlag() == false) {
						Main.setPlayFlag(true);
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
					if (Main.getPlayFlag() == true && Main.getLoadFlag() == true) {
						Main.setPlayFlag(false);
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
					if (Main.getLoadFlag() == true) {
						Main.setLoadFlag(false);
						Main.setPlayFlag(true);
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
	private final File m_file;// file menu
	private final Simulation m_simulation;// simulation menu
	private final Help m_help;// help menu
	private MapDrawing myMapDraw;// map drawing panel
	private Map map;// contains all Settlements information
	private Statistics stat;// contains the statistic data
	private Mutations mutation;// mutation info
}

