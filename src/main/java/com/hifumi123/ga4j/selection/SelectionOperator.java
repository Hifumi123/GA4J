package com.hifumi123.ga4j.selection;

import com.hifumi123.ga4j.Population;
import com.hifumi123.ga4j.exception.NegativeFitnessException;

public interface SelectionOperator {
	
	public void select(Population population) throws NegativeFitnessException;
	
	public void survival(Population population);
}
