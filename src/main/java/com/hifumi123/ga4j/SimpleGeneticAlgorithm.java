package com.hifumi123.ga4j;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.exception.NegativeFitnessException;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class SimpleGeneticAlgorithm {
	
	private int populationSize;
	
	private int maxGeneration;
	
	private double probabilityOfCrossover;
	
	private double probabilityOfMutation;
	
	private IndividualGenerator individualGenerator;
	
	private Evaluator evaluator;
	
	private SelectionOperator selection;
	
	private CrossoverOperator crossover;
	
	private MutationOperator mutation;
	
	private DataCollector dataCollector;
	
	private int generation;
	
	private Population population;
	
	private AbstractIndividual bestSoFar;
	
	public SimpleGeneticAlgorithm(int populationSize, int maxGeneration, double probabilityOfCrossover, double probabilityOfMutation, IndividualGenerator individualGenerator, Evaluator evaluator, SelectionOperator selection, CrossoverOperator crossover, MutationOperator mutation) {
		this.populationSize = populationSize;
		this.maxGeneration = maxGeneration;
		this.probabilityOfCrossover = probabilityOfCrossover;
		this.probabilityOfMutation = probabilityOfMutation;
		this.individualGenerator = individualGenerator;
		this.evaluator = evaluator;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
	}

	private void initializePopulation() {
		population = new Population();
		for (int i = 0; i < populationSize; i++)
			population.addToEvolvingGroup(individualGenerator.generateIndividual());
	}
	
	private void evaluatePopulation() {
		for (int i = 0; i < population.sizeOfEvolvingGroup(); i++)
			evaluator.evaluate(population.getFromEvolvingGroup(i));
	}
	
	private void generateNewPopulation() {
		try {
			selection.select(population);
		} catch (NegativeFitnessException e) {
			e.printStackTrace();
		}
		
		crossover.cross(population, probabilityOfCrossover);
		
		mutation.mutate(population, probabilityOfMutation);
	}
	
	public AbstractIndividual run() {
		generation = 0;
		bestSoFar = null;
		initializePopulation();
		
		evaluatePopulation();
		
		bestSoFar = population.searchBestIndividual();
		
		if (dataCollector != null)
			dataCollector.collectData(population, generation);
		
		while (generation < maxGeneration) {
			generation++;
			
			generateNewPopulation();
			
			evaluatePopulation();
			
			selection.survival(population);
			
			AbstractIndividual bestCandidate = population.searchBestIndividual();
			if (bestCandidate.getFitness() > bestSoFar.getFitness())
				bestSoFar = bestCandidate;
			
			if (dataCollector != null)
				dataCollector.collectData(population, generation);
		}
		
		return bestSoFar;
	}

	public void setDataCollector(DataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}
}
