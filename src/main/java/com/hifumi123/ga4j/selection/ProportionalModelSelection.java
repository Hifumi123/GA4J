package com.hifumi123.ga4j.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;

public class ProportionalModelSelection implements SelectionOperator {

	private Random random;
	
	public ProportionalModelSelection() {
		random = new Random();
	}

	@Override
	public List<AbstractIndividual> select(List<AbstractIndividual> individuals) {
		double totalFitness = 0;
		
		for (AbstractIndividual individual : individuals)//TODO 适应度为负数时要报错
			totalFitness += individual.getFitness();
		
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
