package com.hifumi123.ga4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class StandardParallelGeneticAlgorithm {

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
	
	private int nThreads;
	
	private int generation;
	
	private ParallelPopulation population;
	
	private ExecutorService exec;
	
	private AbstractIndividual bestSoFar;
	
	private void initializePopulation() {
		population = new ParallelPopulation(nThreads);
		for (int i = 0; i < populationSize; i++)
			population.getEvolvingIndividualList().add(individualGenerator.generateIndividual());
	}
	
	private void evaluatePopulation() {
		ParallelList<AbstractIndividual> evolvingIndividualList = population.getEvolvingIndividualList();
		evolvingIndividualList.distributeElementsEvenly();
		
		CountDownLatch latch = new CountDownLatch(nThreads);
		
		for (int i = 0; i < evolvingIndividualList.sizeOfList(); i++) {
			List<AbstractIndividual> list = evolvingIndividualList.getList(i);
			
			exec.execute(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < list.size(); j++)
						evaluator.evaluate(list.get(j));
					
					latch.countDown();
				}
			});
		}
		
		exec.shutdown();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		generation = 0;
		exec = Executors.newFixedThreadPool(nThreads);
		initializePopulation();
		
		//TODO
	}
}
