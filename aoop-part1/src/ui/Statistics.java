package ui;

import javax.swing.JPanel;
import javax.swing.JTable;

import country.Map;

public class Statistics extends JPanel {
	public Statistics(Map map) {
		m_statsTable = new JTable(map.getTableData(), columnNames.values());
	}

	private enum columnNames {
		col1("settlement name"), col2("settlement type"), col3("ramzor color"), col4("sick percentage"),
		col5("vaccine doses"), col6("amount deceased"), col7("population");

		private final String colName;

		columnNames(String name) {
			colName = name;
		}

		public String toString() {
			return colName;
		}

	}

	private final JTable m_statsTable;
}
