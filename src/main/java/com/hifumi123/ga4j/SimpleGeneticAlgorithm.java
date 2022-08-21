package com.hifumi123.ga4j;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class SimpleGeneticAlgorithm extends AbstractGeneticAlgorithm {

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
}
