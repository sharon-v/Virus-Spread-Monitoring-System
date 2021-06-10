package ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import virus.VirusEnum;
import virus.VirusManager;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Mutations extends JDialog {

	/**
	 * constructor
	 */
	public Mutations() {
		super((JFrame) null, "Edit Mutations", true);
		MyCheckModel model = new MyCheckModel();
		JTable table = new JTable(model);
		VirusManager vm = VirusManager.SingeltonVirusManager(table);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		/**
		 * saves the marked CheckBoxes to it's corresponding Variant
		 */
//		this.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				for (int i = 0; i < table.getRowCount(); ++i) {
//					ArrayList<String> variants = new ArrayList<>();
//					for (int j = 0; j < table.getColumnCount(); ++j) {
//						if (table.getValueAt(i, j).equals(true))
//							variants.add(table.getColumnName(j));
//					}
//					String name = table.getColumnName(i);
//					if (name.equals("British Variant"))
//						BritishVariant.setPossibleVariants(variants);
//					else if (name.equals("Chinese Variant"))
//						ChineseVariant.setPossibleVariants(variants);
//					else
//						SouthAfricanVariant.setPossibleVariants(variants);
//
//				}
//			}
//		});

		panel.add(table);

		// JTable view settings
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		panel.add(new RowedTableScroll(table, model.getColNames()));

		// add JPanel to JDialog
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
	 * inner Class to create our TableModel
	 *
	 */
	private class MyCheckModel extends AbstractTableModel {

		/**
		 * constructor
		 */
		public MyCheckModel() {

			String temp = "";
			for (VirusEnum v : VirusEnum.values()) {
				temp += v.getType() + ";";
			}
			colNames = temp.split(";");

			// make data array of check boxes
			data = new Boolean[getRowCount()][getColumnCount()];
			for (int i = 0; i < getRowCount(); ++i) {
				for (int j = 0; j < getColumnCount(); ++j) {
					data[i][j] = false;
				}
			}
		}

		@Override
		public int getRowCount() {
			return getColumnCount();
		}

		@Override
		public int getColumnCount() {
			return colNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];

		}

		@Override
		public String getColumnName(int column) {
			return colNames[column];
		}

		@Override
		public Class getColumnClass(int column) { return getValueAt(0, column).getClass(); }


		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex >= 0;
		}

		/**
		 * 
		 * @return array of column names
		 */
		public String[] getColNames() {
			return colNames;
		}

		@Override
		public void setValueAt(Object aValue, int row, int col) {
			data[row][col] = (Boolean) aValue;
			fireTableCellUpdated(row, col);
		}

		private final Boolean[][] data;
		private String[] colNames = new String[0];
	}
}
