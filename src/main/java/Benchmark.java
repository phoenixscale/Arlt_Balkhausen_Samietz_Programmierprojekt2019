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
	
	public static int calculateDifference(File map, File que, File sol) {
		
		Utility.startTimer();
		
		Data.initialize(map);
		Data.readAndWrite();
		
		readFiles(que, sol);
		
		checkQueSol();
		
		System.out.println("Calculated " + amountOfQuestions + " questions in " + Utility.endTimer() + " seconds!");
		System.out.println("Amount of Mistakes: " + amountOfMistakes);
		
		return amountOfMistakes;
	}

	private static void checkQueSol() {
		System.out.println("Commencing Calculation. This might take a while...");
		
		amountOfMistakes = 0;
		
		for (int ctr = 0; ctr < questionSources.length; ctr++) {
			
			try {
				if (Dijkstra.setSourceAndTarget(questionSources[ctr], questionTargets[ctr]) != solutions[ctr])
					amountOfMistakes++;
			} catch (Exception e) {
				
			}
		}
	}

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
		
		System.out.println(questionSources.length + " questions have been read.\n"
				+ solutions.length + " solutions have been read.\n");
	}

}
