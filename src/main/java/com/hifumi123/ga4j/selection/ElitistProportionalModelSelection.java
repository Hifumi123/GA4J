package com.hifumi123.ga4j.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.Population;
import com.hifumi123.ga4j.exception.NegativeFitnessException;

public class ElitistProportionalModelSelection implements SelectionOperator {
	
	private Random random;
	
	public ElitistProportionalModelSelection() {
		random = new Random();
	}
	
	@Override
	public void protect(Population population) {
		if (population.sizeOfRemainedGroup() < 1) {
			int bestIndexInEvolvingGroup = population.searchBestIndividualIndexInEvolvingGroup();
			AbstractIndividual best = population.removeFromEvolvingGroup(bestIndexInEvolvingGroup);
			population.addToRemainedGroup(best);
		}
	}

	@Override
	public List<AbstractIndividual> select(Population population, int n) throws NegativeFitnessException {
		double totalFitness = 0;
		
		for (int i = 0; i < population.sizeOfEvolvingGroup(); i++) {
			AbstractIndividual individual = population.getFromEvolvingGroup(i);
			
			if (individual.getFitness() < 0)
				throw new NegativeFitnessException("出现负数适应度！");
			
			totalFitness += individual.getFitness();
		}
		
		double[] cumulativeFitnesses = new double[population.sizeOfEvolvingGroup()];
		for (int i = 0; i < cumulativeFitnesses.length; i++)
			cumulativeFitnesses[i] = population.getFromEvolvingGroup(i).getFitness() / totalFitness;
		
		for (int i = 1; i < cumulativeFitnesses.length; i++)
			cumulativeFitnesses[i] += cumulativeFitnesses[i - 1];
		
		List<AbstractIndividual> list = new ArrayList<AbstractIndividual>(n);
		for (int i = 0; i < n; i++) {
			int index = selectOne(cumulativeFitnesses);
			
			try {
				list.add((AbstractIndividual) population.getFromEvolvingGroup(index).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public void survival(Population population) {
		int bestIndexInEvolvingGroup = population.searchBestIndividualIndexInEvolvingGroup();
		
		AbstractIndividual bestCandidate = population.getFromEvolvingGroup(bestIndexInEvolvingGroup);
		AbstractIndividual best = population.getFromRemainedGroup(0);
		
		if (bestCandidate.getFitness() > best.getFitness()) {
			population.removeFromEvolvingGroup(bestIndexInEvolvingGroup);
			population.removeFromRemainedGroup(0);
			
			population.addToEvolvingGroup(best);
			population.addToRemainedGroup(bestCandidate);
		}
		
		int worstIndexInEvolvingGroup = population.searchWorstIndividualIndexInEvolvingGroup();
		population.removeFromEvolvingGroup(worstIndexInEvolvingGroup);
		try {
			population.addToEvolvingGroup((AbstractIndividual) population.getFromRemainedGroup(0).clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	private int selectOne(double[] cumulativeFitnesses) {
		double p = random.nextDouble();
		
		int index = 0;
		while (p > cumulativeFitnesses[index])
			index++;
		
		return index;
	}
}
