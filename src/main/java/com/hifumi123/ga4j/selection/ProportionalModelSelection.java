package com.hifumi123.ga4j.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.exception.NegativeFitnessException;

public class ProportionalModelSelection implements SelectionOperator {

	private Random random;
	
	public ProportionalModelSelection() {
		random = new Random();
	}

	@Override
	public List<AbstractIndividual> select(List<AbstractIndividual> individuals) throws NegativeFitnessException {
		double totalFitness = 0;
		
		for (AbstractIndividual individual : individuals) {
			if (individual.getFitness() < 0)
				throw new NegativeFitnessException("出现负数适应度！");
			
			totalFitness += individual.getFitness();
		}
		
		double[] cumulativeFitnesses = new double[individuals.size()];
		for (int i = 0; i < cumulativeFitnesses.length; i++)
			cumulativeFitnesses[i] = individuals.get(i).getFitness() / totalFitness;
		
		for (int i = 1; i < cumulativeFitnesses.length; i++)
			cumulativeFitnesses[i] += cumulativeFitnesses[i - 1];
		
		List<AbstractIndividual> newPopulation = new ArrayList<AbstractIndividual>(individuals.size());
		for (int i = 0; i < individuals.size(); i++) {
			int index = selectOne(cumulativeFitnesses);
			
			try {
				newPopulation.add((AbstractIndividual) individuals.get(index).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		return newPopulation;
	}
	
	private int selectOne(double[] cumulativeFitnesses) {
		double p = random.nextDouble();
		
		int index = 0;
		while (p > cumulativeFitnesses[index])
			index++;
		
		return index;
	}
}
