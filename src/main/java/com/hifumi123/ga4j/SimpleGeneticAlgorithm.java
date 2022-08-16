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
		bestSoFar = null;
		initializePopulation();
		
		evaluatePopulation();
		
		bestSoFar = population.get(searchBestIndividualIndex());
		
		if (dataCollector != null)
			dataCollector.collectData(population, generation);
		
		while (generation < maxGeneration) {
			generation++;
			
			generateNewPopulation();
			
			evaluatePopulation();
			
			AbstractIndividual bestCandidate = population.get(searchBestIndividualIndex());
			if (bestCandidate.getFitness() > bestSoFar.getFitness())
				bestSoFar = bestCandidate;
			
			if (dataCollector != null)
				dataCollector.collectData(population, generation);
		}
	}
}
