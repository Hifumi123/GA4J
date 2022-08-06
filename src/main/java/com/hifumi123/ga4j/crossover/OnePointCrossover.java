package com.hifumi123.ga4j.crossover;

import java.util.List;
import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;

public class OnePointCrossover implements CrossoverOperator {

	private Random random;
	
	public OnePointCrossover() {
		random = new Random();
	}
	
	@Override
	public void cross(List<AbstractIndividual> individuals, double probabilityOfCrossover) {
		int[] indexs = new int[individuals.size()];
		for (int i = 0; i < indexs.length; i++)
			indexs[i] = i;
		
		for (int i = 0; i < indexs.length; i++) {
			int p = random.nextInt(indexs.length - i);
			
			int temp = indexs[i];
			indexs[i] = indexs[p + i];
			indexs[p + i] = temp;
		}
		
		for (int i = 0; i < indexs.length - 1; i += 2)
			crossPairOfIndividuals(individuals.get(indexs[i]), individuals.get(indexs[i + 1]), probabilityOfCrossover);
	}
	
	private void crossPairOfIndividuals(AbstractIndividual i1, AbstractIndividual i2, double probabilityOfCrossover) {
		double p = random.nextDouble();
		
		if (p < probabilityOfCrossover) {
			int begin = random.nextInt(i1.getChromosome().getLength() - 1) + 1;
			
			i1.getChromosome().cross(i2.getChromosome(), begin, i1.getChromosome().getLength());
		}
	}
}
