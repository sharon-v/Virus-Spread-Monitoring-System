package ui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Mutations extends JDialog {
	public Mutations(Frame window) {
		super(window, "Edit Mutations", true);

		// String[] colNames = { "British Variant", "Chinese Variant", "South African
		// Variant" };
		// JCheckBox[][] data = new JCheckBox[3][3];
		// for(int i = 0 ; i < 3 ; ++i) {
		// for(int j =0 ;j < 3; ++j) {
		// data[i][j] = new JCheckBox();
		//
		// data[i][j].addItemListener(this);
		// }
		// }

		// DefaultTableModel model = new DefaultTableModel(data, colNames);
//		String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };

//		DefaultTableModel model =new DefaultTableModel()
//		{
//			public Class<Boolean> getColumnClass(int column) {
//				return Boolean.class;
//
//			}
//		};

		MyCheckModel model = new MyCheckModel();
		JTable table = new JTable(model);


//		table.setModel(model);
		this.add(table);
		this.add(new RowedTableScroll(table, model.getColNames()));
	}

	public void showDialog() {
		setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
	}


	// private class MyCheckModel extends DefaultTableModel {
	// private final String[] colNames = { "British Variant", "Chinese Variant",
	// "South African Variant" };
	// private final boolean[][] data;
	// boolean bool = false;
	//
	// public MyCheckModel() {
	// data = new boolean[3][3];
	// for (int i = 0; i < 3; ++i) {
	// for (int j = 0; j < 3; ++j) {
	// data[i][j] = bool;
	// }
	// }
	// }
	//
	// public boolean[][] getData() {
	// return data;
	// }
	//
	// public String[] getColNames() {
	// return colNames;
	// }
	//
	// public String getColumnName(int column) {
	// return colNames[column];
	// }
	//
	// public Class getColumnClass(int column) {
	// return boolean.class;
	// }
	//
	// public Object getValueAt(int row, int col) {
	// return data[row][col];
	// }
	//
	// @Override
	// public int getRowCount() {
	// return getColumnCount();
	// }
	//
	// @Override
	// public int getColumnCount() {
	// return 3;
	// }
	//
	// public boolean isCellEditable(int row, int col) {
	// return true;
	// }
	//
	// public void setValueAt(boolean value, int row, int col) {
	// data[row][col] = value;
	// fireTableCellUpdated(row, col);
	// }
	// }

	// private class MyCheckModel extends DefaultTableModel implements ItemListener
	// {
	// private final JCheckBox[][] data;
	// private final String[] colNames = { "British Variant", "Chinese Variant",
	// "South African Variant" };
	//
	// public MyCheckModel() {
	// data = new JCheckBox[getColumnCount()][getColumnCount()];
	// for(int i = 0 ; i < getColumnCount() ; ++i) {
	// for(int j =0 ;j < getColumnCount(); ++j) {
	// data[i][j] = new JCheckBox();
	// data[i][j].addItemListener(this);
	// }
	// }
	// }
	//
	// @Override
	// public void itemStateChanged(ItemEvent e) {
	// JCheckBox ck = (JCheckBox) e.getItemSelectable();
	// String state = (e.getStateChange() == ItemEvent.DESELECTED ? "Deselected" :
	// "Selected");
	//
	//// this.fireTableDataChanged();
	// }
	//
	//
	//
	// public String[] getColNames() {
	// return colNames;
	// }
	//
	// }

//	private class MyCheckModel extends AbstractTableModel implements ItemListener {
	private class MyCheckModel extends AbstractTableModel {

		// private final JCheckBox[][] data;// ???

		private final Boolean[][] data;// ???
		private final String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };

		public MyCheckModel() {
//			data = new JCheckBox[getColumnCount()][getColumnCount()];
//			for (int i = 0; i < getColumnCount(); ++i) {
//				for (int j = 0; j < getColumnCount(); ++j) {
//					data[i][j] = new JCheckBox();
//					data[i][j].addItemListener(this);
//				}
//			}
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

//		@Override
//		public Class getColumnClass(int column) {
//			return JCheckBox.class;
//		}
		
		@Override
		public Class getColumnClass(int column) { return getValueAt(0, column).getClass(); }


		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex >= 0;
		}

		public String[] getColNames() {
			return colNames;
		}

//		@Override
//		public void itemStateChanged(ItemEvent e) {
//			JCheckBox ck = (JCheckBox) e.getItemSelectable();
//			String state = (e.getStateChange() == ItemEvent.DESELECTED ? "Deselected" : "Selected");
//
//			this.fireTableDataChanged();
//		}

		@Override
		public void setValueAt(Object aValue, int row, int col) {
			data[row][col] = (Boolean) aValue;
			fireTableCellUpdated(row, col);
		}

	}
}
