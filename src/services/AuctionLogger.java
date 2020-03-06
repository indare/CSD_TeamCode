package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AuctionLogger {
	private static AuctionLogger instance;
	private AuctionLogger() {
	}
	public static AuctionLogger getInstance() {
		if (instance == null) {
			instance = new AuctionLogger();
		}
		return instance;
	}

	public void log(String _fileName, String _message) {
		FileWriter logWriter = OpenFileWriter(_fileName);
		if(logWriter!=null) {
			try {
				logWriter.append(_message+"\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				CloseWriter(logWriter);
			}
		}
	}

	public boolean findMessage(String _fileName, String message) {
		FileReader logReader = OpenFileReader(_fileName);
		if(logReader!=null) {
			try {
				Scanner src = new Scanner(logReader);
				while (src.hasNext()) {
					if(src.nextLine().equals(message))
						return true;
				}
			} finally {
				CloseReader(logReader);
			}
		}
		return false;
	}
	public String returnMessage(String _fileName, String message) {
		FileReader logReader = OpenFileReader(_fileName);
		if(logReader!=null) {
			try {
				Scanner src = new Scanner(logReader);
				while (src.hasNext()) {
					if(src.nextLine().equals(message))
						return message;
				}
			} finally {
				CloseReader(logReader);
			}
		}
		return "";
	}
	
	

	private void CloseWriter(FileWriter logWriter) {
		try {
			logWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void CloseReader(FileReader logReader) {
		try {
			logReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FileWriter OpenFileWriter(String _fileName) {
		File logFile = new File(_fileName);
		FileWriter logWriter = null;
		try {
			logWriter = new FileWriter(logFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logWriter;
	}
	
	
	
	private FileReader OpenFileReader(String _fileName) {
		File logFile = new File(_fileName);
		FileReader logReader = null;
		try {
			logReader = new FileReader(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logReader;
	}
	public void clearLog(String _fileName) {
		File logFile = new File(_fileName);
		logFile.delete();
		return;
	}
}
