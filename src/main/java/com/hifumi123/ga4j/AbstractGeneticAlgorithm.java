package com.hifumi123.ga4j;

import java.util.ArrayList;
import java.util.List;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public abstract class AbstractGeneticAlgorithm {

	protected int populationSize;
	
	protected int maxGeneration;
	
	private double probabilityOfCrossover;
	
	private double probabilityOfMutation;
	
	private IndividualGenerator individualGenerator;
	
	private Evaluator evaluator;
	
	private SelectionOperator selection;
	
	private CrossoverOperator crossover;
	
	private MutationOperator mutation;
	
	protected DataCollector dataCollector;
	
	protected int generation;
	
	protected List<AbstractIndividual> population;

	public AbstractGeneticAlgorithm(int populationSize, int maxGeneration, double probabilityOfCrossover, double probabilityOfMutation, IndividualGenerator individualGenerator, Evaluator evaluator, SelectionOperator selection, CrossoverOperator crossover, MutationOperator mutation) {
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
	
	protected void initializePopulation() {
		population = new ArrayList<AbstractIndividual>(populationSize);
		for (int i = 0; i < populationSize; i++)
			population.add(individualGenerator.generateIndividual());
	}
	
	protected void evaluatePopulation() {
		for (int i = 0; i < population.size(); i++)
			evaluator.evaluate(population.get(i));
	}
	
	protected void generateNewPopulation() {
		population = selection.select(population);
		
		crossover.cross(population, probabilityOfCrossover);
		
		mutation.mutate(population, probabilityOfMutation);
	}
	
	public abstract void run();
	
	protected int searchBestIndividualIndex() {
		int bestIndividualIndex = 0;
		
		for (int i = 1; i < population.size(); i++)
			if (population.get(i).getFitness() > population.get(bestIndividualIndex).getFitness())
				bestIndividualIndex = i;
		
		return bestIndividualIndex;
	}
	
	public AbstractIndividual getBestIndividual() {
		int index = searchBestIndividualIndex();
		
		return population.get(index);//TODO 应该返回所有代中的最好个体
	}

	public void setDataCollector(DataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}
}
