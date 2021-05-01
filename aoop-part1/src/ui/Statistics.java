package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;//????
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import country.Map;
import country.Settlement;
import io.StatisticsFile;

public class Statistics extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Statistics(Map map, Container f) {// change to panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));// statistics window frame

		MyMapModel model1 = new MyMapModel(map);
		JTable table = new JTable(model1);
		JPanel upperMenu = new JPanel();
		JPanel lowerMenu = new JPanel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(750, 200));
		table.setFillsViewportHeight(true);


		upperMenu.setLayout(new BoxLayout(upperMenu, BoxLayout.LINE_AXIS));
		lowerMenu.setLayout(new BoxLayout(lowerMenu, BoxLayout.LINE_AXIS));
		
		table.setRowSorter(sorter = new TableRowSorter<MyMapModel>(model1));
//		table.setRowFilter();
		m_combo = new JComboBox<>(filterOptions.values());

		upperMenu.add(m_combo);
		upperMenu.add(tbFilterText = new JTextField());
		tbFilterText.setToolTipText("Filter Name Column");
		tbFilterText.getDocument().addDocumentListener(new DocumentListener() {
		public void insertUpdate(DocumentEvent e) { newFilter(); }
		public void removeUpdate(DocumentEvent e) { newFilter(); }
		public void changedUpdate(DocumentEvent e) { newFilter(); }
		});
		
		

		m_combo.setSelectedIndex(0);
		m_combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = m_combo.getItemAt(m_combo.getSelectedIndex()).toString();
				if(value.equals("filter by"))
					sorter.setRowFilter(null);
				else
					newFilter1();

			}
		});
		JButton saveBt = new JButton("Save");
		saveBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();

				// In response to a button click:
				int returnVal = fc.showOpenDialog(saveBt);
				fc.setDialogTitle("Choose Directory");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getAbsolutePath();
					// This is where a real application would open the file.
					StatisticsFile.exportToCSV(table, path);
				}
			}
		});

		JButton addSickBt = new JButton("Add Sick");
		addSickBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				map.activateOnePercent(table.getValueAt(table.getSelectedRow(), 0));

			}
		});

		JButton addVaccineBt = new JButton("Vaccinate");
		addVaccineBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				int num;
				String result = (String) JOptionPane.showInputDialog(table, "Enter amount of vaccine doses",
										"Vaccine", JOptionPane.PLAIN_MESSAGE, null, null, "0");
				try {
					num = Integer.parseInt(result);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					return;
				}
				map.addVaccines(table.getValueAt(table.getSelectedRow(), 0), num);
				return;
			}
		});
		
		lowerMenu.add(saveBt);
		lowerMenu.add(addSickBt);
		lowerMenu.add(addVaccineBt);
		
		m_table = table; ////??????????????????????????????????????????????????????????????????/
		
		this.add(upperMenu);
		this.add(table);
		this.add(new JScrollPane(table));
		this.add(lowerMenu);

	}

	private void newFilter() {
		try {
			sorter.setRowFilter(RowFilter.regexFilter(tbFilterText.getText(), 0, 1, 2, 3, 4, 5, 6));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}
	}
	
	private void newFilter1() {
		String value = m_combo.getItemAt(m_combo.getSelectedIndex()).toString();
		try {
			sorter.setRowFilter(RowFilter.regexFilter(value, 1, 2));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}
	}
	
	public void markLine(String settl) {
		for(int i=0;i<7;++i)
			if(m_table.getValueAt(i, 0).equals(settl))
				m_table.addRowSelectionInterval(i, i); //can mark a selected line
	}
	
	

//	private enum columnNames {
//		col1("settlement name"), col2("settlement type"), col3("ramzor color"), col4("sick percentage"),
//		col5("vaccine doses"), col6("amount deceased"), col7("population");
//
//		private final String colName;
//
//		private columnNames(String name) {
//			colName = name;
//		}
//
//		public String toString() {
//			return colName;
//		}
//
//		public static String getValueAt(int index) {
//			return values()[index].toString();
//		}
//
//	}

	private class MyMapModel extends AbstractTableModel {
		private Map data;
		private final String[] colNames = { "settlement name", "settlement type", "ramzor color", "sick percentage",
				"vaccine doses", "amount deceased", "population" };
		
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
				return settlement.getRamzorColor();// ???
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
			return columnIndex > 0;
		}

//		@Override
//		public void setValueAt(Object aValue, int row, int col) {
//			Settlement settlement = data.at(row);
//			if (col == 2)
//
//			fireTableCellUpdated(row, col);
//		}

		public String[] getColNames() {
			return colNames;
		}

//		public int getColIndex(String name) {
//			for (int i = 0; i < colNames.length; ++i) {
//				if (colNames[i].equals(name))
//					return i;
//			}
//
//		}


	}

	private enum filterOptions {
		op0("filter by"),
		// settlement type options
		sop1("City"), sop2("Kibbutz"), sop3("Moshav"),
		// ramzor color options
		cop1("GREEN"), cop2("YELLOW"), cop3("ORANGE"), cop4("RED");

		private final String opName;

		private filterOptions(String name) {
			opName = name;
		}

		public String toString() {
			return opName;
		}
	}
	
	private JTable m_table;////???? ask sharon
	private JTextField tbFilterText;
	private TableRowSorter<MyMapModel> sorter;
	private TableRowSorter<MyMapModel> sorter1;
	public final JComboBox<filterOptions> m_combo;// ?????
}
