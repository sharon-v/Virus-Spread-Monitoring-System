package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
	/**
	 * 
	 * @param infoToExport - string to print to logFile
	 * @param path         - String object that contains the path where we want to
	 *                     save the info
	 * @return true if the writing succeeded
	 */
	public static boolean exportToLog(String infoToExport, String path) {
//		Logger logger = Logger.getLogger("DeceasedLogDocumentation");
//		FileHandler fh = null;
//		try {
//			fh = new FileHandler(path + ".log");
//			logger.addHandler(fh);
//			logger.info(infoToExport);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			fh.close();
//		}
//		return true;

		// create a new file with specified file name
		FileWriter logfw = null;

		// create the IO stream on that file
		BufferedWriter bw = null;

		try {
			logfw = new FileWriter(new File(path + ".log"));
			bw = new BufferedWriter(logfw);
			// write a string into the IO stream
			bw.write(infoToExport);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			// don't forget to close the stream
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
