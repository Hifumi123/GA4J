package com.hifumi123.ga4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hifumi123.ga4j.crossover.CrossoverOperator;
import com.hifumi123.ga4j.exception.NegativeFitnessException;
import com.hifumi123.ga4j.mutation.MutationOperator;
import com.hifumi123.ga4j.selection.SelectionOperator;

public class StandardParallelGeneticAlgorithm extends AbstractGeneticAlgorithm {
	
	private class SelectionTask implements Runnable {
		
		private Population population;
		
		private int n;
		
		private CountDownLatch latch;
		
		private List<AbstractIndividual> list;

		public SelectionTask(Population population, int n, CountDownLatch latch) {
			this.population = population;
			this.n = n;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				list = selection.select(population, n);
			} catch (NegativeFitnessException e) {
				e.printStackTrace();
			}
			
			latch.countDown();
		}

		public List<AbstractIndividual> getList() {
			return list;
		}
	}
	
	private int nThreads;
	
	private ExecutorService exec;
		
	private int[] numbersOfPopulationForThreads;
	
	private int[] indexesOfPopulationForThreads;
	
	public StandardParallelGeneticAlgorithm(int populationSize, int maxGeneration, double probabilityOfCrossover, double probabilityOfMutation, IndividualGenerator individualGenerator, Evaluator evaluator, SelectionOperator selection, CrossoverOperator crossover, MutationOperator mutation, int nThreads) {
		this.populationSize = populationSize;
		this.maxGeneration = maxGeneration;
		this.probabilityOfCrossover = probabilityOfCrossover;
		this.probabilityOfMutation = probabilityOfMutation;
		this.individualGenerator = individualGenerator;
		this.evaluator = evaluator;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.nThreads = nThreads;
	}

	private void generateArraysForThreads() {
		numbersOfPopulationForThreads = new int[nThreads];
		indexesOfPopulationForThreads = new int[nThreads + 1];
		
		int a = population.sizeOfEvolvingGroup() / nThreads;
		int b = population.sizeOfEvolvingGroup() % nThreads;
		
		for (int i = 0; i < numbersOfPopulationForThreads.length; i++)
			numbersOfPopulationForThreads[i] = a;
		
		for (int i = 0; b > 0; i++, b--)
			numbersOfPopulationForThreads[i]++;
		
		for (int i = 1; i < indexesOfPopulationForThreads.length; i++)
			indexesOfPopulationForThreads[i] = numbersOfPopulationForThreads[i - 1];
		
		for (int i = 2; i < indexesOfPopulationForThreads.length; i++)
			indexesOfPopulationForThreads[i] += indexesOfPopulationForThreads[i - 1];
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		exec = Executors.newFixedThreadPool(nThreads);
		
		generateArraysForThreads();
	}
	
	private void parallelEvaluate() {
		CountDownLatch latch = new CountDownLatch(nThreads);
		
		for (int i = 0; i < nThreads; i++) {
			int startIndex = indexesOfPopulationForThreads[i];
			int endIndex = indexesOfPopulationForThreads[i + 1];
			
			exec.execute(new Runnable() {
				
				@Override
				public void run() {
					for (int j = startIndex; j < endIndex; j++)
						evaluator.evaluate(population.getFromEvolvingGroup(j));
					
					latch.countDown();
				}
			});
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void evaluate() {
		parallelEvaluate();
	}

	@Override
	protected void beforeGenerateNewPopulation() {
		super.beforeGenerateNewPopulation();
		
		generateArraysForThreads();
	}
	
	private void parallelSelect() {
		List<SelectionTask> stList = new ArrayList<SelectionTask>(nThreads);
		
		CountDownLatch latch = new CountDownLatch(nThreads);
		
		for (int i = 0; i < nThreads; i++) {
			SelectionTask st = new SelectionTask(population, numbersOfPopulationForThreads[i], latch);
			stList.add(st);
			
			exec.execute(st);
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		population.clearEvolvingGroup();
		for (int i = 0; i < stList.size(); i++)
			population.addAllToEvolvingGroup(stList.get(i).getList());
	}

	@Override
	protected void select() {
		parallelSelect();
	}
	
	@Override
	protected void finish() {
		super.finish();
		
		exec.shutdown();
	}
}
