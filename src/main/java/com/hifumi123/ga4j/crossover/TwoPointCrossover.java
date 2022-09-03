package com.hifumi123.ga4j.crossover;

import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.Population;

public class TwoPointCrossover implements CrossoverOperator {

	private Random random;
	
	public TwoPointCrossover() {
		random = new Random();
	}
	
	@Override
	public void cross(Population population, double probabilityOfCrossover) {
		int[] indexs = new int[population.sizeOfEvolvingGroup()];
		for (int i = 0; i < indexs.length; i++)
			indexs[i] = i;
		
		for (int i = 0; i < indexs.length; i++) {
			int p = random.nextInt(indexs.length - i);
			
			int temp = indexs[i];
			indexs[i] = indexs[p + i];
			indexs[p + i] = temp;
		}
		
		for (int i = 0; i < indexs.length - 1; i += 2)
			crossPairOfIndividuals(population.getFromEvolvingGroup(indexs[i]), population.getFromEvolvingGroup(indexs[i + 1]), probabilityOfCrossover);
	}
	
	private void crossPairOfIndividuals(AbstractIndividual i1, AbstractIndividual i2, double probabilityOfCrossover) {
		double p = random.nextDouble();
		
		if (p < probabilityOfCrossover) {
			int begin = random.nextInt(i1.getChromosome().getLength() - 2) + 1;
			int end = random.nextInt(i1.getChromosome().getLength() - begin - 1) + begin + 1;
			
			i1.getChromosome().cross(i2.getChromosome(), begin, end);
		}
	}
}
