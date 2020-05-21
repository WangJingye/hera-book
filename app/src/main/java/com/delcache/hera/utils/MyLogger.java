package com.delcache.hera.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class MyLogger {
	// 是否记录
	public static boolean logFlag = true;
	// 是否写入文件
	public static boolean logWriteToFile = false;
	// 此文件夹是否存在是日志是否写到文件的开关
	private final static String logFilePath = Environment.getExternalStorageDirectory()
			.getPath() + "/logs";

	private static String logFileName = null;
	// TAG
	private final static String TAG = "APP_LOG";
	// log文件的前缀
	private final static String logFileNamePrefix = "frame_";
	
	private static int logLevel = Log.VERBOSE;

	private static Hashtable<String, MyLogger> loggerTable = null;
	
	private String logFilter;

	/**
	 * 缺省构造函数
	 */
	private MyLogger() {
		logFilter = "";
	}

	/**
	 * 带类别名称的构造函数
	 * 
	 * @param name
	 */
	private MyLogger(String logFilter) {
		this.logFilter = logFilter;
	}

	public static MyLogger getLogger(String logFilter) {
		deleteExsitLogFiles();

		if (loggerTable == null) {
			loggerTable = new Hashtable<String, MyLogger>();
		}

		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		logFileName = String.format(logFilePath + "/" + logFileNamePrefix
				+ "%s.log.txt", formatter.format(today));

		MyLogger logger = (MyLogger) loggerTable.get(logFilter);
		if (logger == null) {
			logger = new MyLogger(logFilter);
			loggerTable.put(logFilter, logger);
		}
		return logger;
	}

	public static MyLogger getLogger() {
		return getLogger("");
	}

	public static void deleteExsitLogFiles() {
		// 如果在sdCard中有上次运行时遗留的log，将被删除。
		File dir = new File(logFilePath);
		if ((dir != null) && dir.exists() && dir.isDirectory()) {
			File[] files = new File(logFilePath).listFiles();
			if (files != null && files.length > 0) {
				Date today = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				cal.add(Calendar.DATE, -1);
				Date yestoday = cal.getTime();
				for (File file : files) {
					if (!file.isHidden() && file.isFile()) {
						if (file.getName().contains(logFileNamePrefix)
								&& file.getName().length() >= 15) {
							Date fileDate = stringToDate(file.getName()
									.substring(7, 15));
							if (fileDate.before(yestoday)) {
								file.delete();
							}
						}
					}
				}
			}
		}
	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()
					|| st.getClassName().equals(Thread.class.getName())
					|| st.getClassName().equals(this.getClass().getName()))
				continue;
			else
				return "[ " + Thread.currentThread().getName() + ": "
						+ st.getFileName() + ":" + st.getLineNumber() + " ]";
		}
		return null;
	}

	public void d(Object obj) {
		if ((!logFlag) || (logLevel > Log.DEBUG))
			return;
		String str = obj2str(obj);
		Log.d(TAG, str);
		if (logWriteToFile) {
			writeLog2File(getFunctionName() + " - " + str);
		}
	}

	public void v(Object obj) {
		if ((!logFlag) || (logLevel > Log.VERBOSE))
			return;
		String str = obj2str(obj);
		Log.v(TAG, str);
		if (logWriteToFile) {
			writeLog2File(getFunctionName() + " - " + str);
		}
	}

	public void i(Object obj) {
		if ((!logFlag) || (logLevel > Log.INFO))
			return;
		String str = obj2str(obj);
		Log.i(TAG, str);
		if (logWriteToFile) {
			writeLog2File(getFunctionName() + " - " + str);
		}
	}

	public void w(Object obj) {
		if ((!logFlag) || (logLevel > Log.WARN))
			return;
		String str = obj2str(obj);
		Log.w(TAG, str);
		if (logWriteToFile) {
			writeLog2File(getFunctionName() + " - " + str);
		}
	}

	public void e(Object obj) {
		if ((!logFlag) || (logLevel > Log.ERROR))
			return;
		String str = obj2str(obj);
		Log.e(TAG, str);
		if (logWriteToFile) {
			writeLog2File(getFunctionName() + " - " + str);
		}
	}

	public void e(Exception ex) {
		if (logFlag) {
			if (logLevel <= Log.ERROR) {
				Log.e(TAG, "error", ex);
				if (logWriteToFile) {
					writeLog2File(ex);
				}
			}
		}
	}

	public void e(String log, Throwable tr) {
		if (logFlag) {
			String line = getFunctionName();
			Log.e(TAG, "{Thread:" + Thread.currentThread().getName() + "}"
					+ "[" + logFilter + line + ":] " + log + "\n", tr);
			if (logWriteToFile) {
				writeLog2File("{Thread:" + Thread.currentThread().getName()
						+ "}" + "[" + logFilter + line + ":] " + log + "\n"
						+ Log.getStackTraceString(tr));
			}
		}
	}

	private String obj2str(Object obj) {
		String name = getFunctionName();
		String str = obj.toString();
		if (name != null) {
			str = name + " - " + obj.toString();
		}

		return str;
	}

	public static void writeLog2File(Object obj) {
		// 如果在sdCard中没有video_log目录，将不写log。
		File dir = new File(logFilePath);
		if (!dir.exists())
			return;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File logfile = new File(logFileName);
			if (!logfile.exists())
				logfile.createNewFile();
			fos = new FileOutputStream(logfile, true);
			pw = new PrintWriter(fos);
			if (pw != null) {
				pw.print(obj + "\r\n");
				pw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (pw != null) {
					pw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Date stringToDate(String time) {
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(4, 6));
		int day = Integer.parseInt(time.substring(6, 8));
		return new Date(year - 1900, month - 1, day);
	}
}
