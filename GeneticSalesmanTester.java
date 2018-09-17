package A3GeneticSalesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GeneticSalesmanTester {

	public static void main(String[] args) throws IOException {
		int populationSize1 = 500;
		int numberOfIterations1 = 5000;
		double crossoverRate1 = 0.8;
		double mutationRate1 = 0.2;
		int solveSeed1 = 34483;
		
		int populationSize2 = 500;
		int numberOfIterations2 = 3000;
		double crossoverRate2 = 0.6;
		double mutationRate2 = 0.09;
		int solveSeed2 = 5678;
		setupAndSolveProblem("C:\\Users\\a\\Desktop\\Class\\COSC461 Heuristics\\src\\A3GeneticSalesman\\file3",
				populationSize1, numberOfIterations1, crossoverRate1, mutationRate1, solveSeed1);
		System.out.println();
		setupAndSolveProblem("C:\\Users\\a\\Desktop\\Class\\COSC461 Heuristics\\src\\A3GeneticSalesman\\file4",
				populationSize2, numberOfIterations2, crossoverRate2, mutationRate2, solveSeed2);
	}

	private static void setupAndSolveProblem(String file, int populationSize, int numberOfIterations,
			double crossoverRate, double mutationRate, int solveSeed) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		int size = scanner.nextInt();
		int amountOfEdges = scanner.nextInt();
		int[][] connectionMatrix = new int[size][size];
		createMatrix(connectionMatrix, size, amountOfEdges, scanner);
		displayMatrix(connectionMatrix, size);
		System.out.println();
		GeneticSalesman g = new GeneticSalesman(connectionMatrix, size);
		g.setParameters(populationSize, size, numberOfIterations, crossoverRate, mutationRate, solveSeed);
		g.solve();
	}

	private static void createMatrix(int[][] matrix, int size, int amountOfEdges, Scanner scanner) {
		for (int i = 0; i < size; i++) {
			matrix[i][i] = 0;
		}
		for (int i = 0; i < amountOfEdges; i++) {
			int vertexA = scanner.nextInt() - 1;
			int vertexB = scanner.nextInt() - 1;
			int weight = scanner.nextInt();
			matrix[vertexA][vertexB] = matrix[vertexB][vertexA] = weight;
		}
	}

	private static void displayMatrix(int[][] matrix, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
