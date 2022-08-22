package com.hifumi123.ga4j;

public interface Evaluator extends Cloneable {

	public void evaluate(AbstractIndividual individual);
	
	public Object clone() throws CloneNotSupportedException;
}
