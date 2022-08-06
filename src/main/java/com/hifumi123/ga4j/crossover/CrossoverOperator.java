package com.hifumi123.ga4j.crossover;

import java.util.List;

import com.hifumi123.ga4j.AbstractIndividual;

public interface CrossoverOperator {

	public void cross(List<AbstractIndividual> individuals, double probabilityOfCrossover);
}
