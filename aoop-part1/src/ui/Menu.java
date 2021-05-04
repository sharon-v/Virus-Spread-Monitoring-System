package ui;


import java.awt.FileDialog;
import java.awt.FlowLayout;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import country.Map;
import simulation.Clock;
import simulation.Main;

public class Menu extends JMenuBar {

//	public Menu(JFrame frame, Statistics st, Map map, MapDrawing draw, JSlider slider) {
	public Menu(JFrame frame, Statistics st, MapDrawing draw, Map map) {

		window = frame;
		stat =st;
		mutation = new Mutations(window);
		this.map = map;
//		m_slider = slider;
		myMapDraw = draw;
	
		m_file = new File();
		m_simulation = new Simulation();
		m_help = new Help();
		// add menu options to bar
		this.add(m_file);
		this.add(m_simulation);
		this.add(m_help);

	}

	private class File extends JMenu {
		public File() {
			super("File");
			JMenuItem load = new JMenuItem("Load");
			JMenuItem stats = new JMenuItem("Statistics");
			JMenuItem edit = new JMenuItem("Edit Mutations");
			JMenuItem exit = new JMenuItem("Exit");
			// add to file
			this.add(load);
			this.add(stats);
			this.add(edit);
			this.add(exit);

			// add ActionListeners
			load.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (Main.getLoadFlag() == false) {// ?????? maybe check if initialized
						Clock.reset();// reset current time to 0 // somehow----------->fixxxxxxxxxxxxxxxx
						// Create a file chooser
						
						FileDialog dialog = new FileDialog((JFrame)null, "Select File to Open");
					    dialog.setMode(FileDialog.LOAD);
					    dialog.setVisible(true);
					    String path = dialog.getFile();
						if (path != null) {
							map.loadInfo(path);
//							myMapDraw.repaint();
							map.intialization();// second stage
							Main.setLoadFlag(true);
							JOptionPane.showMessageDialog(load, "Simulation Started");
						}
//						StatisticsFile.exportToCSV(table, path);
						
//						final JFileChooser fc = new JFileChooser();

						// In response to a button click:
//						int returnVal = fc.showOpenDialog(load);
//						fc.setDialogTitle("Choose Directory");
//						if (returnVal == JFileChooser.APPROVE_OPTION) {
//							String path = fc.getSelectedFile().getAbsolutePath();
							// This is where a real application would open the file.
//							map.loadInfo(path);
//							myMapDraw.repaint();
//
//							map.intialization();// second stage
//						}
					}
				}
			});

			stats.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					stat.showDialog();
				}
			});

			edit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mutation.showDialog();
				}
			});

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});


		}
	}


	private class Simulation extends JMenu {
		public Simulation() {
			super("Simulation");
			JMenuItem play = new JMenuItem("Play");
			JMenuItem pause = new JMenuItem("Pause");
			JMenuItem stop = new JMenuItem("Stop");
			JMenuItem setTicks = new JMenuItem("Set Ticks Per Day");
			// add to file
			this.add(play);
			this.add(pause);
			this.add(stop);
			this.add(setTicks);

			play.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.getLoadFlag() == true && Main.getPlayFlag() == false) {
						Main.setPlayFlag(true);
						JOptionPane.showMessageDialog(play, "Simulation Resumed");
					}
					else
						JOptionPane.showMessageDialog(play, "No file has been loaded", "Inane warning",
							JOptionPane.WARNING_MESSAGE);

				}
			});

			pause.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.getPlayFlag() == true && Main.getLoadFlag() == true) {
						Main.setPlayFlag(false);
						//countinue
						JOptionPane.showMessageDialog(pause, "Simulation Paused");
					}
					else
						JOptionPane.showMessageDialog(pause, "Not currently playing", "Inane warning",
							JOptionPane.WARNING_MESSAGE);

				}
			});

			stop.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (Main.getLoadFlag() == true) {
						Main.setLoadFlag(false);
						Main.setPlayFlag(true);
						JOptionPane.showMessageDialog(stop, "Simulation Stopped \nPlease re-load in order to resume");
					}
					else
						JOptionPane.showMessageDialog(stop, "No file has been loaded", "Inane error",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			// set ticks
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
					// "Set Ticks Per Day"

				}
			});
		}
	}

	private class Help extends JMenu {
		public Help() {
			super("Help");
			JMenuItem help = new JMenuItem("Help");
			JMenuItem about = new JMenuItem("About");
			// add to file
			this.add(help);
			this.add(about);

			help.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog info = new JDialog(window, "Information", JDialog.ModalityType.DOCUMENT_MODAL);
					String expl = "Our program ....";  //?????
					//					JOptionPane.showMessageDialog(info, expl,  JOptionPane.INFORMATION_MESSAGE);
					JLabel text = new JLabel(expl);
					info.add(text);
					info.setSize(300, 300);
					info.setVisible(true);

				}
			});

			about.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog info = new JDialog(window, "About");
					String expl = "Authors : Sharon Vazana and Yarden Hovav\n "
							+ "Time of writing : April 2021";  //?????
					JLabel text = new JLabel(expl);
					info.add(text);
					info.setSize(300, 300);
					info.setVisible(true);
				}
			});

		}

	}

	private final File m_file;
	private final Simulation m_simulation;
	private final Help m_help;
//	private final JMenu m_menu;
	private MapDrawing myMapDraw;
//	private final JSlider m_slider;
	

	private Map map;// maybe can be final????
	private Statistics stat;
	private Mutations mutation;
	private final JFrame window;// hold frame

}

