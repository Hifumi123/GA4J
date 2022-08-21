package com.hifumi123.ga4j;

import java.util.List;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.exception.NegativeFitnessException;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public abstract class AbstractGeneticAlgorithm {

	protected int populationSize;
	
	protected int maxGeneration;
	
	protected double probabilityOfCrossover;
	
	protected double probabilityOfMutation;
	
	protected IndividualGenerator individualGenerator;
	
	protected Evaluator evaluator;
	
	protected SelectionOperator selection;
	
	protected CrossoverOperator crossover;
	
	protected MutationOperator mutation;
	
	private DataCollector dataCollector;
	
	private int generation;
	
	protected Population population;
	
	private AbstractIndividual bestSoFar;
	
	private void initializePopulation() {
		population = new Population();
		for (int i = 0; i < populationSize; i++)
			population.addToEvolvingGroup(individualGenerator.generateIndividual());
	}
	
	protected void initialize() {
		generation = 0;
		bestSoFar = null;
		initializePopulation();
	}
	
	protected void evaluate() {
		for (int i = 0; i < population.sizeOfEvolvingGroup(); i++)
			evaluator.evaluate(population.getFromEvolvingGroup(i));
	}
	
	protected void beforeGenerateNewPopulation() {
		selection.protect(population);
	}
	
	protected void select() {
		try {
			List<AbstractIndividual> list = selection.select(population, population.sizeOfEvolvingGroup());
			population.clearEvolvingGroup();
			population.addAllToEvolvingGroup(list);
		} catch (NegativeFitnessException e) {
			e.printStackTrace();
		}
	}
	
	protected void cross() {
		crossover.cross(population, probabilityOfCrossover);
	}
	
	protected void mutate() {
		mutation.mutate(population, probabilityOfMutation, 0, population.sizeOfEvolvingGroup());
	}
	
	protected void afterGenerateNewPopulation() {
		selection.survival(population);
	}
	
	protected void finish() {}
	
	public AbstractIndividual run() {
		initialize();
		
		evaluate();
		
		bestSoFar = population.searchBestIndividual();
		
		if (dataCollector != null)
			dataCollector.collectData(population, generation);
		
		while (generation < maxGeneration) {
			generation++;
			
			beforeGenerateNewPopulation();
			
			select();
			
			cross();
			
			mutate();
			
			evaluate();
			
			afterGenerateNewPopulation();
			
			AbstractIndividual bestCandidate = population.searchBestIndividual();
			if (bestCandidate.getFitness() > bestSoFar.getFitness())
				bestSoFar = bestCandidate;
			
			if (dataCollector != null)
				dataCollector.collectData(population, generation);
		}
		
		finish();
		
		return bestSoFar;
	}

	public void setDataCollector(DataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}
}
