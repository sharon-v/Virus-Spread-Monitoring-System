package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import virus.BritishVariant;
import virus.ChineseVariant;
import virus.SouthAfricanVariant;

public class Mutations extends JDialog {
	public Mutations(Frame window) {
		super(window, "Edit Mutations", true);
		MyCheckModel model = new MyCheckModel();
		JTable table = new JTable(model);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); ++i) {
					ArrayList<String> variants = new ArrayList<>();
					for (int j = 0; j < table.getColumnCount(); ++j) {
						if (table.getValueAt(i, j).equals(true))
							variants.add(table.getColumnName(j));
					}
					String name = table.getColumnName(i);
					if (name.equals("British Variant"))
						BritishVariant.setPossibleVariants(variants);
					else if (name.equals("Chinese Variant"))
						ChineseVariant.setPossibleVariants(variants);
					else
						SouthAfricanVariant.setPossibleVariants(variants);
				}
			}
		});
		panel.add(table);
		panel.add(new RowedTableScroll(table, model.getColNames()));
		panel.add(saveBtn);
		this.add(panel);
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
			// make data array for checkboxes
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
