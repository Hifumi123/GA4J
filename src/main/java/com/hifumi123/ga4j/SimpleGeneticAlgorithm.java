package com.hifumi123.ga4j;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class SimpleGeneticAlgorithm extends AbstractGeneticAlgorithm {
	
	public SimpleGeneticAlgorithm(int populationSize, int maxGeneration, double probabilityOfCrossover, double probabilityOfMutation, IndividualGenerator individualGenerator, Evaluator evaluator, SelectionOperator selection, CrossoverOperator crossover, MutationOperator mutation) {
		super(populationSize, maxGeneration, probabilityOfCrossover, probabilityOfMutation, individualGenerator, evaluator, selection, crossover, mutation);
	}

	@Override
	public void run() {
		generation = 0;
		initializePopulation();
		initializeStatisticalData();
		
		evaluatePopulation();
		
		makeStatistics();
		if (dataCollector != null)
			dataCollector.collectData(population, generation);
		
		while (generation < maxGeneration) {
			generation++;
			
			generateNewPopulation();
			
			evaluatePopulation();
			
			makeStatistics();
			if (dataCollector != null)
				dataCollector.collectData(population, generation);
		}
	}
}
