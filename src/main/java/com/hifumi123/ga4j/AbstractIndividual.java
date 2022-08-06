package com.hifumi123.ga4j;

import com.hifumi123.ga4j.chromosome.Chromosome;

public abstract class AbstractIndividual implements Cloneable {

	private Chromosome chromosome;
	
	private double fitness;

	public AbstractIndividual(Chromosome chromosome) {
		this.chromosome = chromosome;
		
		fitness = 0;
	}
	
	protected abstract void customClone(AbstractIndividual newIndividual);

	@Override
	public Object clone() throws CloneNotSupportedException {
		AbstractIndividual ai = (AbstractIndividual) super.clone();
		ai.chromosome = (Chromosome) chromosome.clone();
		
		customClone(ai);
		
		return ai;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Chromosome getChromosome() {
		return chromosome;
	}
}
