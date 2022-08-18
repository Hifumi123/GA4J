package com.hifumi123.ga4j.mutation;

import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.Population;

public class SimpleMutation implements MutationOperator {

	private Random random;
	
	public SimpleMutation() {
		random = new Random();
	}

	@Override
	public void mutate(Population population, double probabilityOfMutation) {
		for (int i = 0; i < population.sizeOfEvolvingGroup(); i++) {
			AbstractIndividual individual = population.getFromEvolvingGroup(i);
			
			for (int j = 0; j < individual.getChromosome().getLength(); j++) {
				double p = random.nextDouble();
				
				if (p < probabilityOfMutation)
					individual.getChromosome().mutate(j);
			}
		}
	}
}
