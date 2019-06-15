import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {
	private static double startTime = 0;
	private static File logFile;
	private static FileWriter logfileWriter;

	/**
	 * This method saves the current system time.
	 */
	public static void startTimer() {
		startTime = System.nanoTime();
	}

	/**
	 * This method returns the time passed between starting the timer and calling
	 * this method.
	 * 
	 * @return
	 */
	public static double endTimer() {
		return (System.nanoTime() - startTime) / 1000000000;
	}

	/**
	 * This method initializes the logfile-writer.
	 */
	public static void initialize() {
		startTimer();
		logFile = new File("logfile.txt");
		try {
			logFile.createNewFile();
			logfileWriter = new FileWriter(logFile);
			addLineToFile("This is the logfile for our routeplanner." + System.lineSeparator(), logfileWriter);
			addEmptyLineToFile(logfileWriter);
			logfileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method adds a given String as next line in the file written by the given
	 * writer.
	 * 
	 * @param nextLine
	 * @param writer
	 */
	public static void addLineToFile(String nextLine, FileWriter writer) {
		try {
			writer.write(nextLine, 0, nextLine.length());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates an empty line in the file written by the given writer.
	 * 
	 * @param writer
	 */
	public static void addEmptyLineToFile(FileWriter writer) {
		try {
			writer.write(System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the logfile-writer for easier logfile writing outside of
	 * this class.
	 * 
	 * @return
	 */
	public static FileWriter getLogFileWriter() {
		return logfileWriter;
	}
}