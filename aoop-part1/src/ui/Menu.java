package ui;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu {

	public Menu(JFrame frame) {
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

		}
	}

		private class Statistics extends JDialog {
//			private final JTextField tbUsername;
//			private final JTextField tbPassword;
//			private int result = -1;
			public Statistics(JFrame frame) {
				super(frame, "Statistics Window", false);
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
			m_simulation.add(play);
			m_simulation.add(pause);
			m_simulation.add(stop);
			m_simulation.add(setTicks);

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

}

