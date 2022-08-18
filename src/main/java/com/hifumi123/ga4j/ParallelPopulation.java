package com.hifumi123.ga4j;

import java.util.ArrayList;
import java.util.List;

public class ParallelPopulation {

	private List<AbstractIndividual> remainedIndividualList;
	
	private ParallelList<AbstractIndividual> evolvingIndividualList;
	
	private List<AbstractIndividual> extinctIndividualList;
	
	public ParallelPopulation(int nThreads) {
		remainedIndividualList = new ArrayList<AbstractIndividual>();
		evolvingIndividualList = new ParallelList<AbstractIndividual>(nThreads);
		extinctIndividualList = new ArrayList<AbstractIndividual>();
	}

	public ParallelList<AbstractIndividual> getEvolvingIndividualList() {
		return evolvingIndividualList;
	}
}
