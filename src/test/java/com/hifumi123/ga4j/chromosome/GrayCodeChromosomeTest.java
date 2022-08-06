package com.hifumi123.ga4j.chromosome;

public class GrayCodeChromosomeTest {

	public static void main(String[] args) {
		GrayCodeChromosome gcc = new GrayCodeChromosome(4);
		
		System.out.println(gcc.stringOfGenes());
		
		System.out.println("---");
		
		System.out.println(gcc.decode(0, 4));
	}
}
