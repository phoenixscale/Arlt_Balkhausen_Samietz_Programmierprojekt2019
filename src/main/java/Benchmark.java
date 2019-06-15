import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Benchmark {
	// kommentar

	private static LinkedList<Integer> questionSources;
	private static LinkedList<int[]> questionTargets;
	private static LinkedList<int[]> solutions;

	private static int difference;

	public static int calculateDifference(File map, File que, File sol) {
		readQuestions(que);
		readSolutions(sol);

		checkQueSol();

		return difference;
	}

	/**
	 * This method compares our dijkstra-results with the previously given .sol
	 * file.
	 */
	private static void checkQueSol() {
		difference = 0;

		int[] tempQue;

		for (int ctr = 0; ctr < questionSources.size(); ctr++) {
			Utility.startTimer();

			Dijkstra.setPivotSource(questionSources.get(ctr));

			tempQue = Dijkstra.fromPivotSourceToTargets(questionTargets.get(ctr));
			System.out.println("calculated set nr " + ctr);

			for (int ctr2 = 0; ctr2 < tempQue.length; ctr2++)
				if (solutions.get(ctr)[ctr2] != tempQue[ctr2])
					difference++;

			Utility.addLineToFile("Source node " + questionSources.get(ctr) + " paths calculated in "
					+ Utility.endTimer() + " seconds." + System.lineSeparator(), Utility.getLogFileWriter());
		}
	}

	/**
	 * This method reads the given .que-file and saves its contents in arrays.
	 * 
	 * @param que
	 */
	private static void readQuestions(File que) {

		Utility.startTimer();

		questionSources = new LinkedList<Integer>();
		questionTargets = new LinkedList<int[]>();

		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(que)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int targetAmount = 1;
		int thisSource = scanner.nextInt();
		int lastSource = thisSource;
		int[] tempIntArray;
		LinkedList<Integer> tempTargets = new LinkedList<Integer>();

		tempTargets.add(scanner.nextInt());

		while (scanner.hasNext()) {
			thisSource = scanner.nextInt();

			if (lastSource == thisSource)
				targetAmount++;
			else {
				tempIntArray = new int[targetAmount];

				for (int ctr = 0; ctr < targetAmount; ctr++)
					tempIntArray[ctr] = tempTargets.pop();

				questionSources.add(lastSource);
				questionTargets.add(tempIntArray);

				targetAmount = 1;
			}

			lastSource = thisSource;
			tempTargets.add(scanner.nextInt());
		}

		scanner.close();

		tempIntArray = new int[targetAmount];

		for (int ctr = 0; ctr < targetAmount; ctr++)
			tempIntArray[ctr] = tempTargets.pop();

		questionSources.add(lastSource);
		questionTargets.add(tempIntArray);

		Utility.addLineToFile("Reading questions from file " + que.getName() + " completed in " + Utility.endTimer()
				+ " seconds." + System.lineSeparator(), Utility.getLogFileWriter());
	}

	/**
	 * This method reads a given .sol-file and saves its contents into an array.
	 * 
	 * @param sol
	 */
	private static void readSolutions(File sol) {

		Utility.startTimer();

		solutions = new LinkedList<int[]>();

		for (int[] iA : questionTargets)
			solutions.add(new int[iA.length]);

		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(sol)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		int arrayCtr = 0, integerCtr = 0;

		while (scanner.hasNext()) {
			solutions.get(arrayCtr)[integerCtr] = scanner.nextInt();

			integerCtr++;
			if (integerCtr == solutions.get(arrayCtr).length) {
				integerCtr = 0;
				arrayCtr++;
			}
		}

		scanner.close();

		Utility.addLineToFile("Reading solution from file " + sol.getName() + " completed in " + Utility.endTimer()
				+ " seconds." + System.lineSeparator(), Utility.getLogFileWriter());
	}

}
