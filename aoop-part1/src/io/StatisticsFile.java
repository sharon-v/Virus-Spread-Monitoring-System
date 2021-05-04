package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class StatisticsFile {
// write to file format csv
	public static boolean exportToCSV(JTable tableToExport, String pathToExportTo) {
		FileWriter csv = null;
		try {

			TableModel model = tableToExport.getModel();
			csv = new FileWriter(new File(pathToExportTo + ".csv"));

			for (int i = 0; i < model.getColumnCount(); i++) {
				csv.write(model.getColumnName(i) + ",");
			}

			csv.write("\n");

			for (int row = 0; row < tableToExport.getRowCount(); ++row) {
				for (int col = 0; col < tableToExport.getColumnCount(); ++col) {
					csv.write(tableToExport.getModel().getValueAt(tableToExport.convertRowIndexToModel(row),
							tableToExport.convertColumnIndexToModel(col)) + ",");

				}
				csv.write("\n");
			}
			csv.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();

			try {
				csv.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

}
