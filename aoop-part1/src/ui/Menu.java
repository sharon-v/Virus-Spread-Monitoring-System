package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import country.Map;
import simulation.Clock;

public class Menu {

	public Menu(JFrame frame) {
		window = frame;
		// large menu
		mb = new JMenuBar();
		// menu options on bar
		m_file = new File();
		m_simulation = new Simulation();
		m_help = new Help();
		// add menu options to bar
		mb.add(m_file);
		mb.add(m_simulation);
		mb.add(m_help);


	}

	private class File extends JMenu {
		public File() {
			super("File");
			JMenuItem load = new JMenuItem("Load");
			JMenuItem stats = new JMenuItem("Statistics");
			JMenuItem edit = new JMenuItem("Edit Mutations");
			JMenuItem exit = new JMenuItem("Exit");
			// add to file
			m_file.add(load);
			m_file.add(stats);
			m_file.add(edit);
			m_file.add(exit);

			// add ActionListeners
			load.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (map == null) {// ?????? maybe check if initialized somehow
						// Create a file chooser
						final JFileChooser fc = new JFileChooser();

						// In response to a button click:
						int returnVal = fc.showOpenDialog(load);
						fc.setDialogTitle("Choose Directory");
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							String path = fc.getSelectedFile().getAbsolutePath();
							// This is where a real application would open the file.
							map.loadInfo(path);
							map.intialization();// second stage
							try {
								map.executeSimulation(); // third stage
							} catch (Exception ex) {
								System.out.println(ex);
								System.out.println("an unexpected ERROR has occurred :(");
							}
						}
					}
				}
			});

			stats.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					stat = new Statistics(map, window);
				}
			});

			edit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Mutations mutation = new Mutations(window);
				}
			});

			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});


		}
	}

//		private class Statistics extends JDialog {
//			private final JTextField tbUsername;
//			private final JTextField tbPassword;
//			private int result = -1;
//				super(frame, "Statistics Window", false);
//				this.setLayout(new GridLayout(0, 2));
//				this.add(new JLabel("Username: "));
//				this.add(tbUsername = new JTextField());
//				JButton cancel = new JButton("Cancel");
//				cancel.addActionListener(new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						result = JOptionPane.CANCEL_OPTION;
//						setVisible(false);
//					}
//				});
//				this.add(cancel);
//
//			}
//		}

	private class Simulation extends JMenu {
		public Simulation() {
			super("Simulation");
			JMenuItem play = new JMenuItem("Play");
			JMenuItem pause = new JMenuItem("Pause");
			JMenuItem stop = new JMenuItem("Stop");
			JMenuItem setTicks = new JMenuItem("Set Ticks Per Day");
			// add to file
			m_simulation.add(play);
			m_simulation.add(pause);
			m_simulation.add(stop);
			m_simulation.add(setTicks);

			// need to action listeners for all??????

			// set ticks
			setTicks.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SpinnerModel model = new SpinnerNumberModel(Clock.getTicksPerDay(), 1, 10000, 1);
					JSpinner spinner = new JSpinner(model);
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
			m_help.add(help);
			m_help.add(about);

		}

	}

	private final JMenuBar mb;
	private final File m_file;
	private final Simulation m_simulation;
	private final Help m_help;
	private Map map;// maybe can be final????
	private Statistics stat;
	private final JFrame window;// hold frame

}

