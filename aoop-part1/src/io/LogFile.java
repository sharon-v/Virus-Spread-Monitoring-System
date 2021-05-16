package io;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogFile {
	/**
	 * 
	 * @param infoToExport - string to print to logFile
	 * @param path         - String object that contains the path where we want to
	 *                     save the info
	 * @return true if the writing succeeded
	 */
	public static boolean exportToLog(String infoToExport, String path) {
		Logger logger = Logger.getLogger("DeceasedLogDocumentation");
		FileHandler fh = null;
        try {
        	fh = new FileHandler(path);
            logger.addHandler(fh);
			logger.info(infoToExport);
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
        }
		finally {
			fh.close();
		}
		return true;
	}
}
