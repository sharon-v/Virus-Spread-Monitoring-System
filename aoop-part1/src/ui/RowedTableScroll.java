package ui;

import java.awt.Component;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class RowedTableScroll extends JScrollPane {
	private static class RowHeaderRenderer extends JLabel implements ListCellRenderer<String> {
		RowHeaderRenderer(JTable table) {
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(CENTER);
			JTableHeader header = table.getTableHeader();
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	public RowedTableScroll(final JTable table, final String[] rowHeaders) {
		super(table);
		final JList<String> rowHeader = new JList<String>(new AbstractListModel<String>() {
			public int getSize() {
				return Math.min(rowHeaders.length, table.getRowCount());
			}

			public String getElementAt(int index) {
				return rowHeaders[index];
			}
		});
		rowHeader.setFixedCellWidth(150);
		rowHeader.setFixedCellHeight(table.getRowHeight());
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));
//		table.setFillsViewportHeight(true);
		this.setRowHeaderView(rowHeader);
	}
}

