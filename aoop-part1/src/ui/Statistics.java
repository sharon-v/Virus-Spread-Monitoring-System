package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;//????
import javax.swing.JPanel;
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
	public Statistics(Map map, JFrame f) {// change to panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));// statistics window frame

		MyMapModel model1 = new MyMapModel(map);
		JTable table = new JTable(model1);
		JPanel upperMenu = new JPanel();
		JPanel lowerMenu = new JPanel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);


		upperMenu.setLayout(new BoxLayout(upperMenu, BoxLayout.LINE_AXIS));
		lowerMenu.setLayout(new BoxLayout(lowerMenu, BoxLayout.LINE_AXIS));
		
		table.setRowSorter(sorter = new TableRowSorter<MyMapModel>(model1));
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
				DefaultTableModel model = new DefaultTableModel(
						Map.filtering(new JTable(model1), m_combo.getItemAt(m_combo.getSelectedIndex()).toString())
								.getSelectionModel(),
						model1.getColNames());
				table.setModel(
						Map.filtering(new JTable(model1), m_combo.getItemAt(m_combo.getSelectedIndex()).toString())
								.getSelectionModel());
				model.fireTableDataChanged();
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

		lowerMenu.add(saveBt);

		this.add(upperMenu);
		this.add(table);
		this.add(new RowedTableScroll(table, model1.getColNames()));
		this.add(lowerMenu);

	}

	private void newFilter() {
		try {
			sorter.setRowFilter(RowFilter.regexFilter(tbFilterText.getText(), 0, 1, 2, 3, 4, 5, 6));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}
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

		public String[] getColNames() {
			return colNames;
		}

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

	private JTextField tbFilterText;
	private TableRowSorter<MyMapModel> sorter;
	public final JComboBox<filterOptions> m_combo;// ?????
}
