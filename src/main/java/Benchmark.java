import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Benchmark {

	private static int[] questionSources;
	private static int[] questionTargets;
	private static int[] solutions;

	private static int amountOfQuestions;
	private static int amountOfMistakes;

	/**
	 * Executes the benchmark
	 * 
	 * @param map
	 * @param que
	 * @param sol
	 * @param dij
	 */
	public static void calculateDifference(Graph map, File que, File sol, Dijkstra dij) {
		readFiles(que, sol);
		checkQueSol(dij);

		System.out.println("Calculated " + amountOfQuestions + " questions in " + Utility.endTimer() + " seconds!");
		System.out.println("Amount of Mistakes: " + amountOfMistakes);
	}

	/**
	 * Executes a One-to-One Dijktra for each query within the .que file and
	 * compares the results with the ones contained in the .sol file
	 * 
	 * @param dij
	 */
	private static void checkQueSol(Dijkstra dij) {
		System.out.println("Commencing Calculation. This might take a while.");

		amountOfMistakes = 0;

		for (int ctr = 0; ctr < questionSources.length; ctr++) {
			dij.dijkstra(questionSources[ctr], questionTargets[ctr]);

			try {
				if (dij.getDistanceTo(questionTargets[ctr]) != solutions[ctr])
					System.out.println(questionTargets[ctr]);
				amountOfMistakes++;
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Reads the .que and .sol files
	 * 
	 * @param que
	 * @param sol
	 */
	private static void readFiles(File que, File sol) {
		int ctr = 0;

		Scanner lineCounter = null;
		Scanner readerQue = null;
		Scanner readerSol = null;

		try {
			lineCounter = new Scanner(new BufferedReader(new FileReader(sol)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (lineCounter.hasNext()) {
			amountOfQuestions++;
			lineCounter.next();
		}

		questionSources = new int[amountOfQuestions];
		questionTargets = new int[amountOfQuestions];
		solutions = new int[amountOfQuestions];

		lineCounter.close();

		try {
			readerQue = new Scanner(new BufferedReader(new FileReader(que)));
			readerSol = new Scanner(new BufferedReader(new FileReader(sol)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (readerQue.hasNext()) {
			try {
				questionSources[ctr] = readerQue.nextInt();
				questionTargets[ctr] = readerQue.nextInt();
				solutions[ctr] = readerSol.nextInt();
			} catch (Exception e) {
			}

			ctr++;
		}

		readerQue.close();
		readerSol.close();

		System.out.println(questionSources.length + " questions have been read.\n" + solutions.length
				+ " solutions have been read.\n");
	}

}
