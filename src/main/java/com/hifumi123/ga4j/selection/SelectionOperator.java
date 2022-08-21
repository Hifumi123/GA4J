package com.hifumi123.ga4j.selection;

import java.util.List;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.Population;
import com.hifumi123.ga4j.exception.NegativeFitnessException;

public interface SelectionOperator {
	
	public void protect(Population population);
	
	public List<AbstractIndividual> select(Population population, int n) throws NegativeFitnessException;
	
	public void survival(Population population);
}
