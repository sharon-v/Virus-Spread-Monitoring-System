package ui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Mutations extends JDialog {
	public Mutations(Frame window) {
		super(window, "Edit Mutations", true);
		MyCheckModel model = new MyCheckModel();
		JTable table = new JTable(model);
		this.add(table);
		this.add(new RowedTableScroll(table, model.getColNames()));
	}

	public void showDialog() {
		setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
	}


	private class MyCheckModel extends AbstractTableModel {

		private final Boolean[][] data;
		private final String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };

		public MyCheckModel() {
			// make data array foe checkbox
			data = new Boolean[getColumnCount()][getColumnCount()];
			for (int i = 0; i < getColumnCount(); ++i) {
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

		public String[] getColNames() {
			return colNames;
		}

		@Override
		public void setValueAt(Object aValue, int row, int col) {
			data[row][col] = (Boolean) aValue;
			fireTableCellUpdated(row, col);
		}

	}
}
