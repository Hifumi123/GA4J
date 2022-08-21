package com.hifumi123.ga4j;

import java.util.ArrayList;
import java.util.List;

public class Population {

	private List<AbstractIndividual> remainedIndividualList;
	
	private List<AbstractIndividual> evolvingIndividualList;
	
	private List<AbstractIndividual> extinctIndividualList;
	
	public Population() {
		remainedIndividualList = new ArrayList<AbstractIndividual>();
		evolvingIndividualList = new ArrayList<AbstractIndividual>();
		extinctIndividualList = new ArrayList<AbstractIndividual>();
	}
	
	public int size() {
		return remainedIndividualList.size() + evolvingIndividualList.size() + extinctIndividualList.size();
	}

	public AbstractIndividual get(int index) {
		if (index < remainedIndividualList.size())
			return remainedIndividualList.get(index);
		else {
			index -= remainedIndividualList.size();
			
			if (index < evolvingIndividualList.size())
				return evolvingIndividualList.get(index);
			else {
				index -= evolvingIndividualList.size();
				
				return extinctIndividualList.get(index);
			}
		}
	}

	public AbstractIndividual searchBestIndividual() {
		AbstractIndividual best = null;
		if (remainedIndividualList.size() > 0)
			best = remainedIndividualList.get(0);
		else if (evolvingIndividualList.size() > 0)
			best = evolvingIndividualList.get(0);
		else if (extinctIndividualList.size() > 0)
			best = extinctIndividualList.get(0);
		else
			return best;
		
		for (int i = 0; i < remainedIndividualList.size(); i++) {
			AbstractIndividual individual = remainedIndividualList.get(i);
			
			if (individual.getFitness() > best.getFitness())
				best = individual;
		}
		
		for (int i = 0; i < evolvingIndividualList.size(); i++) {
			AbstractIndividual individual = evolvingIndividualList.get(i);
			
			if (individual.getFitness() > best.getFitness())
				best = individual;
		}
		
		for (int i = 0; i < extinctIndividualList.size(); i++) {
			AbstractIndividual individual = extinctIndividualList.get(i);
			
			if (individual.getFitness() > best.getFitness())
				best = individual;
		}
		
		return best;
	}
	
	public int sizeOfRemainedGroup() {
		return remainedIndividualList.size();
	}
	
	public boolean addToRemainedGroup(AbstractIndividual individual) {
		return remainedIndividualList.add(individual);
	}
	
	public AbstractIndividual getFromRemainedGroup(int index) {
		return remainedIndividualList.get(index);
	}
	
	public AbstractIndividual removeFromRemainedGroup(int index) {
		return remainedIndividualList.remove(index);
	}

	public int sizeOfEvolvingGroup() {
		return evolvingIndividualList.size();
	}

	public boolean addToEvolvingGroup(AbstractIndividual individual) {
		return evolvingIndividualList.add(individual);
	}
	
	public boolean addAllToEvolvingGroup(List<AbstractIndividual> list) {
		return evolvingIndividualList.addAll(list);
	}

	public AbstractIndividual getFromEvolvingGroup(int index) {
		return evolvingIndividualList.get(index);
	}
	
	public AbstractIndividual removeFromEvolvingGroup(int index) {
		return evolvingIndividualList.remove(index);
	}
	
	public void clearEvolvingGroup() {
		evolvingIndividualList.clear();
	}
	
	private int searchBestIndividualIndexInList(List<AbstractIndividual> list) {
		if (list.size() < 1)
			return -1;
		
		int bestIndex = 0;
		for (int i = 1; i < list.size(); i++)
			if (list.get(i).getFitness() > list.get(bestIndex).getFitness())
				bestIndex = i;
		
		return bestIndex;
	}
	
	private int searchWorstIndividualIndexInList(List<AbstractIndividual> list) {
		if (list.size() < 1)
			return -1;
		
		int worstIndex = 0;
		for (int i = 1; i < list.size(); i++)
			if (list.get(i).getFitness() < list.get(worstIndex).getFitness())
				worstIndex = i;
		
		return worstIndex;
	}
	
	public int searchBestIndividualIndexInEvolvingGroup() {
		return searchBestIndividualIndexInList(evolvingIndividualList);
	}
	
	public int searchWorstIndividualIndexInEvolvingGroup() {
		return searchWorstIndividualIndexInList(evolvingIndividualList);
	}
}
