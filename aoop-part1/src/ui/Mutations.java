package ui;

import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import country.Map;
import country.Settlement;

public class Mutations extends JDialog implements ItemListener {	
	public Mutations(Frame window) {
		super(window, "Edit mutations", true);
		
		
		String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };
		JCheckBox[][] data = new JCheckBox[3][3];
		for(int i = 0 ; i < 3 ; ++i) {
			for(int j =0 ;j < 3; ++j) {
				data[i][j] = new JCheckBox();
			
				data[i][j].addItemListener(this);
			}
		}
	
		
//		DefaultTableModel model = new DefaultTableModel(data, colNames);
		
		
//		MyCheckModel model = new MyCheckModel();
		
		JTable table = new JTable(data, colNames);
		this.add(table);
		this.add(new RowedTableScroll(table, colNames));

	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox ck = (JCheckBox) e.getItemSelectable();
		String state = (e.getStateChange() == ItemEvent.DESELECTED ? "Deselected" : "Selected");
	}
	
	public void showDialog() {
		setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
	}
	
	
	
	
	private class MyCheckModel extends DefaultTableModel implements ItemListener {
		private final JCheckBox[][] data;
		private final String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };
		
		public MyCheckModel() {
			data = new JCheckBox[getColumnCount()][getColumnCount()];
			for(int i = 0 ; i < getColumnCount() ; ++i) {
				for(int j =0 ;j < getColumnCount(); ++j) {
					data[i][j] = new JCheckBox();
					data[i][j].addItemListener(this);
				}
			}
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
		JCheckBox ck = (JCheckBox) e.getItemSelectable();
		String state = (e.getStateChange() == ItemEvent.DESELECTED ? "Deselected" : "Selected");
		
//		this.fireTableDataChanged();
		}
		
	
		
		public String[] getColNames() {
		return colNames;
		}

	}
	
	
//	private class MyCheckModel extends AbstractTableModel implements ItemListener {
//		private final JCheckBox[][] data;//???
//		private final String[] colNames = { "British Variant", "Chinese Variant", "South African Variant" };
//		
//		public MyCheckModel() {
//			data = new JCheckBox[getColumnCount()][getColumnCount()];
//			for(int i = 0 ; i < getColumnCount() ; ++i) {
//				for(int j =0 ;j < getColumnCount(); ++j) {
//					data[i][j] = new JCheckBox();
//					data[i][j].addItemListener(this);
//				}
//			}
//		}
//
//		@Override
//		public int getRowCount() {
//			return getColumnCount();
//		}
//
//		@Override
//		public int getColumnCount() {
//			return colNames.length;
//		}
//
//		@Override
//		public Object getValueAt(int rowIndex, int columnIndex) {	
//			return data[rowIndex][columnIndex].getIcon();
//		
//		}
//
//		@Override
//		public String getColumnName(int column) {
//			return colNames[column];
//		}
//
//		@Override
//		public Class getColumnClass(int column) {
//			return Boolean.class;
//		}
//
//		@Override
//		public boolean isCellEditable(int rowIndex, int columnIndex) {
//			return columnIndex > 0;
//		}
//
//
//		public String[] getColNames() {
//			return colNames;
//		}
//		
//		@Override
//		public void itemStateChanged(ItemEvent e) {
//		JCheckBox ck = (JCheckBox) e.getItemSelectable();
//		String state = (e.getStateChange() == ItemEvent.DESELECTED ? "Deselected" : "Selected");
//		
////		this.fireTableDataChanged();
//		}
//
//	}
}
