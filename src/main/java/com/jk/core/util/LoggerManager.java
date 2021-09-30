package com.jk.core.util;

import java.time.*;
import java.util.logging.*;

public class LoggerManager {
	public static Logger logger;

	public LoggerManager() {
	}

	public static void writeLogInfo(Exception e) {
		log(e);
	}

	public static void writeLogSevere(Exception e) {
		log(e);
	}

	public static void writeLogWarning(Exception e) {
		log(e);
	}

	public static void writeLogInfo(String info) {
		log(info);
	}

	public static void writeLogSevere(String severe) {
		log(severe);
	}

	public static void writeLogWarning(String warning) {
		log(warning);
	}
	
	static void log(Exception e) {
		log("Error occurred.");
		e.printStackTrace();
	}
	
	static void log(String msg) {
		System.out.println(LocalTime.now() + "  " + msg);
	}
}
