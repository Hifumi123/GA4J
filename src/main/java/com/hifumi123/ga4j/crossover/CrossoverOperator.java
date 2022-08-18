package com.hifumi123.ga4j.crossover;

import com.hifumi123.ga4j.Population;

public interface CrossoverOperator {
	
	public void cross(Population population, double probabilityOfCrossover);
}
