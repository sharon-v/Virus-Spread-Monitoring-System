package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class LogFile {
	/**
	 * 
	 * @param infoToExport - string to print to logFile
	 * @param path         - String object that contains the path where we want to
	 *                     save the info
	 * @return true if the writing succeeded
	 */
	public static boolean exportToLog(String infoToExport, String path) {

		// create a new file with specified file name
		FileWriter logfw = null;

		// create the IO stream on that file
		BufferedWriter bw = null;

		try {
			logfw = new FileWriter(path, true);
			bw = new BufferedWriter(logfw);
			// write a string into the IO stream
			bw.append(infoToExport);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}// sync class //
