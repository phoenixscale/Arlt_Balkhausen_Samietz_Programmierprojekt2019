import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

	Scanner scan;
	Graph map;
	File que, sol;
	Dijkstra dij;

	/**
	 * Constructor for class UI
	 */
	public UI() {
		this.scan = new Scanner(System.in);
		run();
	}

	/**
	 * Initializes the main file
	 */
	private void run() {
		boolean running = true;

		System.out.println("Before we can do anything, we have to read the required files.");

		try {
			readFile("map");
		} catch (FileNotFoundException e) {
			System.out.println("Invalid Input.");
		}

		System.out.println("\nYou have four choices. Input the corresponding number to proceed:");

		while (running) {
			selector();
			System.out.println("");
		}
	}

	/**
	 * Creates main menu
	 */
	private void selector() {
		System.out.println("1. A to B");
		System.out.println("2. One-To-All Dijkstra");
		System.out.println("3. Benchmarking via Que and Sol");
		System.out.println("4. Read new map");

		int input = scan.nextInt();

		switch (input) {
		case 1:
			aToB();
			break;
		case 2:
			oneToAll();
			break;
		case 3:
			benchmark();
			break;
		case 4:
			try {
				readFile("map");
			} catch (FileNotFoundException e) {
				System.out.println("Invalid Input.");
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Reads files from given filepath
	 */
	private void readFile(String type) throws FileNotFoundException {
		File file = new File("emptystringhopefullynooneeveruses");
		while (!file.exists()) {
			System.out.println("Please enter the path of the " + type + " file you wish to read:");
			file = new File(scan.next());

			if (file.exists()) {
				switch (type) {
				case "map":
					System.out.println("Loading map file...");
					Utility.startTimer();
					map = new Graph(file.getPath());
					dij = new Dijkstra(map);
					System.out.println("Task was completed in " + Utility.endTimer() + "s.");
					break;
				case "que":
					que = new File(file.getPath());
					break;
				case "sol":
					sol = new File(file.getPath());
					break;
				default:
					break;
				}
				break;
			} else {
				System.out.println("Invalid Input.");
			}
		}

	}

	/**
	 * Executes One-to-One Dijktra
	 */
	private void aToB() {
		int a = 0, b = 0;
		System.out.println("Please enter an integer value for the start-point:");
		try {
			a = scan.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input.");
			return;
		}
		if (a > map.getNodeCount() - 1 || a < 0) {
			System.out.println("Invalid Input.");
			return;
		}

		System.out.println("Please enter an integer value for the end-point:");
		try {
			b = scan.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input.");
			return;
		}
		if (b > map.getNodeCount() - 1 || b < 0) {
			System.out.println("Invalid Input.");
			return;
		}

		System.out.println("Calculating distance from " + a + " to " + b + ".");

		Utility.startTimer();
		dij.dijkstra(a, b); // insert variables in method
		System.out.println(dij.getDistanceTo(b));
		System.out.println("Task was completed in " + Utility.endTimer() + "s.");
	}

	/**
	 * Executes One-to-All Dijktra
	 */
	private void oneToAll() {
		int start = 0;
		System.out.println("Please enter an integer value for the start-point:");
		try {
			start = scan.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input.");
			return;
		}
		if (start > map.getNodeCount() - 1 || start < 0) {
			System.out.println("Invalid Input.");
			return;
		}
		Utility.startTimer();
		dij.dijkstra(start);
		System.out.println("Task was completed in " + Utility.endTimer() + "s.");
		oTASelector(start);
	}

	/**
	 * Reads que, sol and executes benchmark
	 */
	private void benchmark() {
		try {
			readFile("que");
		} catch (FileNotFoundException e) {
			System.out.println("Invalid Input.");
		}

		try {
			readFile("sol");
		} catch (FileNotFoundException e) {
			System.out.println("Invalid Input.");
		}

		Utility.startTimer();
		Benchmark.calculateDifference(map, que, sol, dij);
		System.out.println("Task was completed in " + Utility.endTimer() + "s.");
	}

	/**
	 * Creates menu, after the One-to-All has been executed, for further queries
	 */
	private void oTASelector(int start) {
		boolean running = true;
		while (running) {

			System.out.println("\nSelect one of the following:");
			System.out.println("1. Choose destination.");
			System.out.println("2. Return to main menu.");
			int input = scan.nextInt();

			switch (input) {
			case 1:
				System.out.print("Please input a destination: ");
				input = scan.nextInt();
				System.out.println(
						"The distance from " + start + " to " + input + " is " + dij.getDistanceTo(input) + ".");
				break;
			case 2:
				running = false;
				break;
			default:
				break;
			}
		}
	}

}