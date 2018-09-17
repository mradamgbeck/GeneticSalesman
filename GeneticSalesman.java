package A3GeneticSalesman;

import java.util.Random;

public class GeneticSalesman {
	private int[][] matrix;
	private int numberOfVertices;
	private int maximumEdge;

	private int populationSize;
	private int stringLength;
	private int numberOfIterations;
	private double crossoverRate;
	private double mutationRate;
	private Random random;

	private int[][] population;
	private double[] fitnessValues;

	public GeneticSalesman(int[][] matrix, int numberOfVertices) {
		this.matrix = matrix;
		this.numberOfVertices = numberOfVertices;
		this.maximumEdge = Integer.MIN_VALUE;

		for (int i = 0; i < numberOfVertices; i++) {
			for (int j = 0; j < numberOfVertices; j++) {
				if (matrix[i][j] > maximumEdge) {
					maximumEdge = matrix[i][j];
				}
			}
		}
	}

	public void setParameters(int populationSize, int stringLength, int numberOfIterations, double crossoverRate,
			double mutationRate, int seed) {
		this.populationSize = populationSize;
		this.stringLength = stringLength;
		this.numberOfIterations = numberOfIterations;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.random = new Random(seed);

		this.population = new int[populationSize][stringLength];
		this.fitnessValues = new double[populationSize];
	}

	public void solve() {
		initializePopulation();

		for (int i = 0; i < numberOfIterations; i++) {
			crossover();
			mutate();
			reproduce();
		}
		solution();
	}

	private void initializePopulation() {
		for(int i = 0 ; i < populationSize ; i++){
			randomPermutation(population[i]);
		}
		
		for(int i = 0 ; i < populationSize ; i++){
			fitnessValues[i] = 0;
		}

	}

	public void randomPermutation(int[] string) {
		for (int i = 0; i < stringLength; i++) {
			string[i] = i + 1;
		}

		for (int i = 0; i < stringLength - 1; i++) {
			int j = random.nextInt(i + 1);
			int temp = string[i];
			string[i] = string[j];
			string[j] = temp;
		}
	}

	private void crossover() {
		for (int i = 0; i < populationSize; i++) {
			if (random.nextDouble() < crossoverRate) {
				int j = random.nextInt(populationSize);
				int cut = random.nextInt(stringLength);

				int[] copy = new int[stringLength];
				for (int k = 0; k < stringLength; k++) {
					copy[k] = population[i][k];
				}
				for (int k = cut; k < stringLength; k++) {
					swap(population[i][k], population[j][k], population[i]);
				}
				for (int k = cut; k < stringLength; k++) {
					swap(copy[k], population[j][k], population[j]);
				}
			}
		}
	}

	private void swap(int a, int b, int[] string) {
		int i;
		for (i = 0; i < stringLength; i++) {
			if (string[i] == a) {
				break;
			}
		}

		int j;
		for (j = 0; j < stringLength; j++) {
			if (string[j] == b) {
				break;
			}
		}

		int temp = string[i];
		string[i] = string[j];
		string[j] = temp;
	}

	private void mutate() {
		for (int i = 0; i < populationSize; i++) {
			for (int j = 0; j < stringLength; j++) {
				if (random.nextDouble() < mutationRate) {
					int k = random.nextInt(stringLength);

					int temp = population[i][j];
					population[i][j] = population[i][k];
					population[i][k] = temp;
				}
			}
		}
	}

	private void reproduce() {
		computeFitnessValues();

		int[][] nextGeneration = new int[populationSize][stringLength];

		for (int i = 0; i < populationSize; i++) {
			int j = select();

			for (int k = 0; k < stringLength; k++) {
				nextGeneration[i][k] = population[j][k];
			}
		}

		for (int i = 0; i < populationSize; i++) {
			for (int j = 0; j < stringLength; j++) {
				population[i][j] = nextGeneration[i][j];
			}
		}
	}

	private void computeFitnessValues() {
		for (int i = 0; i < populationSize; i++) {
			fitnessValues[i] = fitness(population[i]);
		}
		
		for (int i = 1; i < populationSize; i++) {
			fitnessValues[i] = fitnessValues[i] + fitnessValues[i-1];
		}
		
		for (int i = 0; i < populationSize; i++) {
			fitnessValues[i] = fitnessValues[i]/fitnessValues[populationSize-1];
		}
	}

	private int select() {
		double value = random.nextDouble();
		
		int i;
		for(i = 0 ; i < populationSize ; i++){
			if(value <= fitnessValues[i]){
				break;
			}
		}
		return i;
	}

	private void solution() {
		for(int i = 0 ; i < populationSize ; i++){
			fitnessValues[i] = fitness(population[i]);
		}
		
		int best = 0;
		for(int i = 0 ; i < populationSize ; i++){
			if(fitnessValues[i] > fitnessValues[best]){
				best = i;
			}
		}
		
		display(population[best]);
	}

	private double fitness(int[] string) {
		double sum = 0;
		for(int i = 0 ; i < stringLength ; i++){
			sum += matrix[string[i] - 1][string[(i+1)%stringLength]-1];
		}
		return numberOfVertices * maximumEdge - sum;
	}

	private void display(int[] string) {
		System.out.println("Best cycle: ");
		for(int i = 0 ; i < stringLength ; i++){
			System.out.print(string[i] + " ");
		}
		System.out.print(string[0] + " ");
		
		System.out.println();
		
		double sum = 0;
		for(int i = 0 ; i < stringLength ; i++){
			sum += matrix[string[i] - 1][string[(i+1)%stringLength]-1];
		}
		
		System.out.println("Length: " + sum);
	}
}
