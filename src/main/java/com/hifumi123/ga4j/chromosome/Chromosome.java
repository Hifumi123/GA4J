package com.hifumi123.ga4j.chromosome;

public interface Chromosome extends Cloneable {

	public int decode(int begin, int end);
	
	public void cross(Chromosome other, int begin, int end);
	
	public void mutate(int indexOfPoint);
	
	public Object clone() throws CloneNotSupportedException;
	
	public int getLength();
}
