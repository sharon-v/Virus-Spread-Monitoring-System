package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class StatisticsFile {
// write to file format csv
	public static boolean exportToCSV(JTable tableToExport, String pathToExportTo) {

		try {

			TableModel model = tableToExport.getModel();
			FileWriter csv = new FileWriter(new File(pathToExportTo + ".csv"));

			for (int i = 0; i < model.getColumnCount(); i++) {
				csv.write(model.getColumnName(i) + ",");
			}

			csv.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					csv.write(model.getValueAt(i, j).toString() + ",");
				}
				csv.write("\n");
			}

			csv.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
