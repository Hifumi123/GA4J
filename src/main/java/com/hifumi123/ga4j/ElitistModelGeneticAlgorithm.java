package com.hifumi123.ga4j;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class ElitistModelGeneticAlgorithm extends AbstractGeneticAlgorithm {
	
	private int bestIndividualIndex;
	
	private int worstIndividualIndex;
	
	private AbstractIndividual bestIndividualSoFar;

	public ElitistModelGeneticAlgorithm(int populationSize, int maxGeneration, double probabilityOfCrossover, double probabilityOfMutation, IndividualGenerator individualGenerator, Evaluator evaluator, SelectionOperator selection, CrossoverOperator crossover, MutationOperator mutation) {
		super(populationSize, maxGeneration, probabilityOfCrossover, probabilityOfMutation, individualGenerator, evaluator, selection, crossover, mutation);
	}
	
	private void searchBestAndWorstIndividualIndex() {
		bestIndividualIndex = 0;
		worstIndividualIndex = 0;
		
		for (int i = 1; i < population.size(); i++) {
			if (population.get(i).getFitness() > population.get(bestIndividualIndex).getFitness())
				bestIndividualIndex = i;
			
			if (population.get(i).getFitness() < population.get(worstIndividualIndex).getFitness())
				worstIndividualIndex = i;
		}
	}

	@Override
	public void run() {
		generation = 0;
		initializePopulation();
		
		evaluatePopulation();
		
		bestIndividualIndex = searchBestIndividualIndex();
		
		if (dataCollector != null)
			dataCollector.collectData(population, generation);
		
		while (generation < maxGeneration) {
			generation++;
			
			//保留适应度最高的个体
			bestIndividualSoFar = population.remove(bestIndividualIndex);
			
			generateNewPopulation();
			
			evaluatePopulation();
			
			searchBestAndWorstIndividualIndex();
			//将原适应度最高的个体放回
			population.add(bestIndividualSoFar);
			if (population.get(bestIndividualIndex).getFitness() < bestIndividualSoFar.getFitness())
				bestIndividualIndex = population.size() - 1;
			try {
				AbstractIndividual ai = (AbstractIndividual) population.get(bestIndividualIndex).clone();
				population.set(worstIndividualIndex, ai);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if (dataCollector != null)
				dataCollector.collectData(population, generation);
		}
	}
}
