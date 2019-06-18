import java.io.File;
import java.util.Scanner;

public class UI {

	Scanner scan;
	File map, que, sol;

	public UI() {
		this.scan = new Scanner(System.in);
		run();
	}

	public void run() {
		boolean running = true;

		System.out.println("Before we can do anything, we have to read the required files.");

		readFile("map");
		System.out.println("\nYou have four choices. Input the corresponding number to proceed:");

		while (running) {
			selector();
			System.out.println("");
		}
	}

	public void selector() {
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
			readFile("map");
			break;
		default:
			break;
		}
	}

	public void readFile(String type) {
		File file = new File("emptystringhopefullynooneeveruses");

		while (!file.exists() || file == null) {
			System.out.println("\nPlease enter the path of the " + type + " file you wish to read:");
			file = Utility.stringToFile(scan.nextLine());

			if (file.exists()) {
				switch (type) {
				case "map":
					map = new File(file.getPath());
					Data.initialize(map);
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
			}

			System.out.println("\nIncorrect Input.");
		}

	}

	public void aToB() {
		System.out.println("Please enter an integer value for the start-point:");
		int a = scan.nextInt();
		if (a > Data.getNodeAmount() || a < 0) {
			System.out.println("The StartNode you chose is not on the map!");
			return;
		}

		System.out.println("Please enter an integer value for the end-point:");
		int b = scan.nextInt();
		if (b > Data.getEdgeAmount() || b < 0) {
			System.out.println("The EndNode you chose is not on the map!");
			return;
		}

		Dijkstra.setSourceAndTarget(a, b);// insert variables in method
	}

	public void oneToAll() {
		// run dijkstra ota
	}

	public void benchmark() {
		readFile("que");
		readFile("sol");
		Benchmark.calculateDifference(map, que, sol);
	}

}