package ui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import country.Map;
import country.Settlement;
import io.StatisticsFile;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Statistics extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * 
	 * @param map - Map object containing all the Settlements information
	 * @param f   - parent JFrame of this Class
	 */
	public Statistics(Map map) {// change to panel
		super((JFrame) null, "Statistics Window", false);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));// statistics window frame

		MyMapModel model1 = new MyMapModel(map);
		JTable table = new JTable(model1);
		JPanel upperMenu = new JPanel();
		JPanel lowerMenu = new JPanel();
		upperMenu.setLayout(new BoxLayout(upperMenu, BoxLayout.LINE_AXIS));
		lowerMenu.setLayout(new BoxLayout(lowerMenu, BoxLayout.LINE_AXIS));
		JLabel label = new JLabel("  Filter TextField: ");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(750, 200));
		table.setFillsViewportHeight(true);
		table.setRowSorter(sorter = new TableRowSorter<MyMapModel>(model1));
		String[] filterOptions = { "Settlement Name", "Settlement Type", "Ramzor Color" };
		m_table = table;
		m_combo = new JComboBox<>(filterOptions);

		// upper menu additions
		upperMenu.add(m_combo);
		upperMenu.add(label);
		upperMenu.add(tbFilterText = new JTextField());

		tbFilterText.setToolTipText("Filter Name Column");
		tbFilterText.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				newFilter(model1.getColIndexCombo(filterOptions[m_combo.getSelectedIndex()]));
			}

			public void removeUpdate(DocumentEvent e) {
				newFilter(model1.getColIndexCombo(filterOptions[m_combo.getSelectedIndex()]));
			}

			public void changedUpdate(DocumentEvent e) {
				newFilter(model1.getColIndexCombo(filterOptions[m_combo.getSelectedIndex()]));
			}
		});
		
		

		m_combo.setSelectedIndex(0);
		JButton saveBt = new JButton("Save");
		saveBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a file chooser
				
				FileDialog dialog = new FileDialog((JFrame)null, "Select File to Open");
			    dialog.setMode(FileDialog.SAVE);
			    dialog.setVisible(true);
				String path = dialog.getFile();
				if(path != null)
					StatisticsFile.exportToCSV(table, path);
			}
		});

		JButton addSickBt = new JButton("Add Sick");
		addSickBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				if(row == -1)
					row = 0;
				map.activateOnePercent(table.getValueAt(row, 0));
				table.updateUI();
			}
		});

		JButton addVaccineBt = new JButton("Vaccinate");
		addVaccineBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				if(row == -1)
					row = 0;
				int num;
				String result = (String) JOptionPane.showInputDialog(table, "Enter amount of vaccine doses",
										"Vaccine", JOptionPane.PLAIN_MESSAGE, null, null, "0");
				try {
					num = Integer.parseInt(result);
				} catch (NumberFormatException ex) {	
					System.out.println("Non integer input");
					return;
				}
				map.addVaccines(table.getValueAt(row, 0), num);
				table.updateUI();
				return;
			}
		});

		// lower menu additions
		lowerMenu.add(saveBt);
		lowerMenu.add(addSickBt);
		lowerMenu.add(addVaccineBt);
		
		// add to JPanel
		panel.add(upperMenu);
		panel.add(table);
		panel.add(new JScrollPane(table));
		panel.add(lowerMenu);

		// add JPanel to JDialoog
		this.add(panel);

		
	}
	
	/**
	 * opens the window
	 */
	public void showDialog() {
		setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
	}

	/**
	 * 
	 * @param index - index to filter by
	 */
	private void newFilter(int index) {
		try {
			sorter.setRowFilter(RowFilter.regexFilter(tbFilterText.getText(),index));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}
	}
	
	/**
	 * update statistics table
	 */
	public void updatStatistics() {
		System.out.print("");
		m_table.updateUI();
	}
	/**
	 * 
	 * @param index - index to sort by
	 */
	public void markLine(int index) {
		index = m_table.getRowSorter().convertRowIndexToModel(index);
		m_table.addRowSelectionInterval(index, index); //can mark a selected line
	}
	
	/**
	 * 
	 * inner class for the TableModel
	 *
	 */
	private class MyMapModel extends AbstractTableModel {
		private Map data;
		private final String[] colNames = { "Settlement Name", "Settlement Type", "Ramzor Color", "Sick Percentage",
				"Vaccine Doses", "Amount Deceased", "Population" };
		
		/**
		 * constructor
		 * 
		 * @param data - Map object with all the Settlement
		 */
		public MyMapModel(Map data) {
			this.data = data;
		}

		@Override
		public int getRowCount() {
			return data.getMapSize();
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Settlement settlement = data.at(rowIndex);
			switch (columnIndex) {
			case 0:
				return settlement.getSettlementName();
			case 1:
				return settlement.getSettlementType();
			case 2:
				return settlement.getRamzorColor();
			case 3:
				return settlement.contagiousPercent();
			case 4:
				return settlement.getVaccineDoses();
			case 5:
				return settlement.getNumOfDeceased();
			case 6:
				return settlement.getNumOfPeople();
			}
			return null;
		}

		@Override
		public String getColumnName(int column) {
			return colNames[column];
		}

		@Override
		public Class getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			return columnIndex > 0;
			return false;
		}

		@Override
		public void setValueAt(Object aValue, int row, int col) {
			fireTableCellUpdated(row, col);
		}

		/**
		 * 
		 * @return array of column names
		 */
		public String[] getColNames() {
			return colNames;
		}

		/**
		 * 
		 * @param name - column name
		 * @return index if name is in the ComboBox filterOptions, if not -1
		 */
		public int getColIndexCombo(String name) {
			for (int i = 0; i < colNames.length; ++i) {
				if (colNames[i].equals(name))
					return i;
			}
			return -1;
		}
	}
	
	private JTable m_table;// reference to our JTable
	private JTextField tbFilterText;// regular expression text filtering
	private TableRowSorter<MyMapModel> sorter;// sorter Object
	public final JComboBox<String> m_combo;// contains filter options
}
