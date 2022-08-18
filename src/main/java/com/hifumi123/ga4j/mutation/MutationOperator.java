package com.hifumi123.ga4j.mutation;

import com.hifumi123.ga4j.Population;

public interface MutationOperator {
	
	public void mutate(Population population, double probabilityOfMutation);
}
