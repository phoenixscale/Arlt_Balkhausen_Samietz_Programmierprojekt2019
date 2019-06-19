import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {

	private int[] source;
	private int[] destination;
	private int[] weight;
	private int[] offset;

	private double[] latitude;
	private double[] longitude;

	private int nodeCount;
	private int edgeCount;

	/**
	 * Constructor for Graph class
	 */
	public Graph(String path) {
		try {
			readFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		createOffset();
	}

	/**
	 * Reads the map(.fmi) file
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void readFile(String path) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			for (int i = 0; i < 5; i++) {
				reader.readLine();
			}
			this.nodeCount = Integer.parseInt(reader.readLine());
			latitude = new double[nodeCount];
			longitude = new double[nodeCount];
			this.edgeCount = Integer.parseInt(reader.readLine());
			source = new int[edgeCount];
			destination = new int[edgeCount];
			weight = new int[edgeCount];

			for (int i = 0; i < nodeCount; i++) {
				String[] numbers = reader.readLine().split(" ");
				latitude[i] = Double.parseDouble(numbers[2]);
				longitude[i] = Double.parseDouble(numbers[3]);
			}
			for (int i = 0; i < edgeCount; i++) {
				String[] numbers = reader.readLine().split(" ");
				source[i] = Integer.parseInt(numbers[0]);
				destination[i] = Integer.parseInt(numbers[1]);
				weight[i] = Integer.parseInt(numbers[2]);
			}

		}
	}

	/**
	 * Return offset of given node
	 * 
	 * @param node
	 * @return offset
	 */
	public int getOffsetOf(int node) {
		return offset[node];
	}

	/**
	 * Return weight of given node
	 * 
	 * @param node
	 * @return weight
	 */
	public int getWeight(int edge) {
		return this.weight[edge];
	}

	/**
	 * Return node count
	 * 
	 * @return node count
	 */
	public int getNodeCount() {
		return this.nodeCount;
	}

	/**
	 * Return offset of given node
	 * 
	 * @param node
	 * @return offset
	 */
	public int getEdgeCount() {
		return this.edgeCount;
	}

	/**
	 * Return latitude of given node
	 * 
	 * @param node
	 * @return latitude
	 */
	public double getLat(int node) {
		return latitude[node];
	}

	/**
	 * Return longitude of given node
	 * 
	 * @param node
	 * @return longitude
	 */
	public double getLon(int node) {
		return longitude[node];
	}

	/**
	 * creates offset array
	 * 
	 */
	public void createOffset() {
		this.offset = new int[this.nodeCount];
		offset[0] = 0;
		int current = 1;
		int start = 0;
		while (source[start] == 0) {
			start++;
		}
		for (int i = start; i < source.length; i++) {
			if (current >= offset.length)
				break;
			if (offset[current] == 0 && source[i] == current) {
				offset[current] = i;
				current++;
			} else {
				if (offset[current] == 0 && source[i] > current) {
					offset[current] = -1;
					current++;
				}
			}
		}
	}

	/**
	 * returns source
	 * 
	 * @param offset
	 * @return source
	 */
	public int getSourceOf(int offset) {
		return source[offset];
	}

	/**
	 * returns destination
	 * 
	 * @param offset
	 * @return destination
	 */
	public int getDestinationOf(int offset) {
		return destination[offset];
	}

	/**
	 * returns next node
	 * 
	 * @param x
	 * @param y
	 * @return node
	 */
	public String getNextNode(double x, double y) {
		double dist = Double.MAX_VALUE;
		int next = Integer.MAX_VALUE;
		for (int i = 0; i < nodeCount; i++) {
			double temp = Math.hypot(latitude[i] - x, longitude[i] - y);
			if (temp < dist) {
				dist = temp;
				next = i;
			}

		}
		return next + " " + latitude[next] + " " + longitude[next];
	}

}