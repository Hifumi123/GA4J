package com.hifumi123.ga4j.mutation;

import java.util.List;

import com.hifumi123.ga4j.AbstractIndividual;

public interface MutationOperator {

	public void mutate(List<AbstractIndividual> individuals, double probabilityOfMutation);
}
