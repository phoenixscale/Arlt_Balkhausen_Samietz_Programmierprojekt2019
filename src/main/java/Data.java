import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Data {

	private static int nodeAmount, edgeAmount;
	private static BufferedReader reader;

	public static int[] nodeOffset; // length of nodeAmount
	public static double[] xLatitude, yLatitude; // length of nodeAmount, sorted by unique nodeID by array index

	public static int[] startNodeID, endNodeID, edgeValue; // length of edgeAmount, sorted by unique edgeID as array
															// index

	/**
	 * This method skips the next line for a given amount of times for a given
	 * scanner.
	 * 
	 * @param skips
	 * @param scanner
	 */
	private static void skipLines(int skips) {
		try {
			for (int ctr = 0; ctr < skips; ctr++) {
				reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readAndWrite() {
		Utility.startTimer();
		readNodes();
		readEdges();
		Utility.addLineToFile(
				"Reading data from file completed in " + Utility.endTimer() + " seconds." + System.lineSeparator(),
				Utility.getLogFileWriter());
	}

	/**
	 * This method extrapolates all node data needed to run the dijkstra algorithm.
	 */
	private static void readNodes() {
		String[] tempArray = null;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {

			try {
				tempArray = reader.readLine().split(" ");
			} catch (Exception e) {
				e.printStackTrace();
			}

			xLatitude[ctr] = Double.parseDouble(tempArray[2]);
			yLatitude[ctr] = Double.parseDouble(tempArray[3]);

			// the offsetArray is inicialized with -1, when a node is a dead end it will be
			// distinguished
			nodeOffset[ctr] = -1;
		}
		// System.out.println("Reading and writing nodes finished");
	}

	/**
	 * This method extrapolates all edge data needed to run the dijkstra algorithm.
	 */
	private static void readEdges() {
		String[] tempArray = null;
		int thisNode = 0;
		int lastNode = -1;
		for (int ctr = 0; ctr < edgeAmount; ctr++) {

			try {
				tempArray = reader.readLine().split(" ");
			} catch (Exception e) {
				e.printStackTrace();
			}

			thisNode = Integer.parseInt(tempArray[0]);

			startNodeID[ctr] = thisNode;
			endNodeID[ctr] = Integer.parseInt(tempArray[1]);
			edgeValue[ctr] = Integer.parseInt(tempArray[2]);

			if (thisNode != lastNode)
				nodeOffset[thisNode] = ctr;

			lastNode = thisNode;
		}
		// System.out.println("Reading and writing edges finished");
	}

	public static int[] getOffsetArray() {
		return nodeOffset;
	}

	public static int[] getStartNodeIDArray() {
		return startNodeID;
	}

	public static int[] getEndNodeIDArray() {
		return endNodeID;
	}

	public static int[] getEdgeValueArray() {
		return edgeValue;
	}

	public static void initialize(File map) {

		Utility.startTimer();
		// initializing the scanner
		try {
			reader = new BufferedReader(new FileReader(map));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// initializing nodeAmount, edgeAmount
		skipLines(5);
		try {
			nodeAmount = Integer.parseInt(reader.readLine());
			edgeAmount = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// initializing the arrays to be used with the amount of nodes and edges
		nodeOffset = new int[nodeAmount];
		xLatitude = new double[nodeAmount];
		yLatitude = new double[nodeAmount];

		startNodeID = new int[edgeAmount];
		endNodeID = new int[edgeAmount];
		edgeValue = new int[edgeAmount];

		// reading the data of the .fmi file
		readAndWrite();
		Window.eventWindow.setText("Data Structures for the map are set.");
		System.out.println("Data structures setup in " + Utility.endTimer() + " seconds.");
	}

	/**
	 * This is a getter-method for the variable nodeAmount.
	 * 
	 * @return
	 */
	public static int getNodeAmount() {
		return nodeAmount;
	}

	/**
	 * This is a getter-method for the variable edgeAmount.
	 * 
	 * @return
	 */
	public static int getEdgeAmount() {
		return edgeAmount;
	}

}