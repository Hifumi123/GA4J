package com.hifumi123.ga4j.mutation;

import java.util.List;
import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;

public class SimpleMutation implements MutationOperator {

	private Random random;
	
	public SimpleMutation() {
		random = new Random();
	}
	
	@Override
	public void mutate(List<AbstractIndividual> individuals, double probabilityOfMutation) {
		for (AbstractIndividual individual : individuals)
			for (int i = 0; i < individual.getChromosome().getLength(); i++) {
				double p = random.nextDouble();
				
				if (p < probabilityOfMutation)
					individual.getChromosome().mutate(i);
			}
	}
}
