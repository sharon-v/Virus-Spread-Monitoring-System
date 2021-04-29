package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;//????
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import country.Map;

public class Statistics extends JPanel {
	public Statistics(Map map, JFrame f) {// change to panel
		f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));// statistics window frame
		m_statsTable = new JTable(map.getTableData(), columnNames.values());
		m_combo = new JComboBox<>(filterOptions.values());
		JPanel upperMenu = new JPanel();
		upperMenu.setLayout(new BoxLayout(upperMenu, BoxLayout.LINE_AXIS));
		f.add(upperMenu);
		f.add(m_statsTable);
		upperMenu.add(m_combo);
		JScrollPane sp = new JScrollPane(m_statsTable);// ???
		f.add(sp);//???

		m_combo.setSelectedIndex(0);
		m_combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = new DefaultTableModel(
						Map.filtering(map.getTableData(), m_combo.getItemAt(m_combo.getSelectedIndex()).toString()),
						columnNames.values());
				m_statsTable.setModel(model);
				model.fireTableDataChanged();
			}
		});

	}

	private enum columnNames {
		col1("settlement name"), col2("settlement type"), col3("ramzor color"), col4("sick percentage"),
		col5("vaccine doses"), col6("amount deceased"), col7("population");

		private final String colName;

		private columnNames(String name) {
			colName = name;
		}

		public String toString() {
			return colName;
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

	public final JTable m_statsTable;// ????
	public final JComboBox<filterOptions> m_combo;// ?????
}
