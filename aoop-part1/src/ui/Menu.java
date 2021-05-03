package ui;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import country.Map;
import simulation.Clock;

public class Menu extends JMenuBar {

	public Menu(JFrame frame, Statistics st, Map map, MapDrawing draw) {
		window = frame;
		stat =st;
		mutation = new Mutations(window);
		this.map = map;
		myMapDraw = draw;
		playFlag = false;// currently simulation is not playing
		loadFlag = false;// currently map is not loaded
		// large menu
		m_menu = new JMenu("Menu");
		// menu options on bar
		m_file = new File();
		m_simulation = new Simulation();
		m_help = new Help();
		// add menu options to bar
		m_menu.add(m_file);
		m_menu.add(m_simulation);
		m_menu.add(m_help);
		this.add(m_menu);


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
					
					if (loadFlag == false) {// ?????? maybe check if initialized somehow----------->fixxxxxxxxxxxxxxxx
						loadFlag = true;
						// Create a file chooser
						final JFileChooser fc = new JFileChooser();

						// In response to a button click:
						int returnVal = fc.showOpenDialog(load);
						fc.setDialogTitle("Choose Directory");
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							String path = fc.getSelectedFile().getAbsolutePath();
							// This is where a real application would open the file.
							map.loadInfo(path);
							myMapDraw.repaint();
						
							map.intialization();// second stage
						}
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
			this.add(play);
			this.add(pause);
			this.add(stop);
			this.add(setTicks);

			// need to action listeners for all??????
			
			play.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (playFlag == false && loadFlag == true) {
						playFlag = true;
						try {
							map.executeSimulation(); // third stage
						} catch (Exception ex) {
							System.out.println("an unexpected ERROR has occurred :(");
							ex.printStackTrace();
						}
					}
				}
			});
			
			pause.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (playFlag == true) {
						playFlag = false;
					//countinue
					}
				}
			});

			stop.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (loadFlag == true)
						loadFlag = false;
					// stop simulation ????????????
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
					SpinnerModel model = new SpinnerNumberModel(Clock.getTicksPerDay(),
							Clock.getTicksPerDay() - 1000 + 1,
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
	private final JMenu m_menu;
	private MapDrawing myMapDraw;
	private boolean playFlag;
	private boolean loadFlag;

	private Map map;// maybe can be final????
	private Statistics stat;
	private Mutations mutation;
	private final JFrame window;// hold frame

}

