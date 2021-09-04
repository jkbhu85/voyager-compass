package com.jk.core.util;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {
	public static Logger logger;

	public LoggerManager() {
	}

	public Logger getLogger(String logFileName) {
		logger = Logger.getLogger("Logger");

		String vcLogDirPath = System.getenv("VCLOGPATH");

		if (vcLogDirPath == null) {
			throw new RuntimeException("Fatal Error: Environment variable \"VCLOGPATH\" is not set.");
		}

		File vcLogDir = new File(vcLogDirPath);

		if (!vcLogDir.exists() || !vcLogDir.isDirectory()) {
			throw new RuntimeException("Fatal Error: Environment variable \"VCLOGPATH\" is not a directory.");
		}

		if (!vcLogDir.canWrite()) {
			throw new RuntimeException("Fatal Error: Environment variable"
					+ " \"VCLOGPATH\" points to a directory that can not be written.");
		}

		try {
			LogManager lm = LogManager.getLogManager();
			FileHandler fh = new FileHandler(vcLogDirPath + File.separator + logFileName, true);

			logger = Logger.getLogger("LoggerManager");
			logger.setUseParentHandlers(false);

			lm.addLogger(logger);
			logger.setLevel(Level.INFO);

			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (Exception e) {
			logger.log(Level.INFO, e.toString(), e.fillInStackTrace());
		}

		return logger;
	}

	public static void writeLogInfo(Exception e) {
		logger.log(Level.INFO, e.toString(), e.fillInStackTrace());
	}

	public static void writeLogSevere(Exception e) {
		logger.log(Level.SEVERE, e.toString(), e.fillInStackTrace());
	}

	public static void writeLogWarning(Exception e) {
		logger.log(Level.WARNING, e.toString(), e.fillInStackTrace());
	}

	public static void writeLogInfo(String info) {
		logger.log(Level.INFO, info);
	}

	public static void writeLogSevere(String severe) {
		logger.log(Level.SEVERE, severe);
	}

	public static void writeLogWarning(String warning) {
		logger.log(Level.WARNING, warning);
	}
}
