package com.hifumi123.ga4j.chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * 二进制编码染色体。
 * 
 * @author Hifumi123
 *
 */
public class BinaryChromosome implements Chromosome {

	private boolean[] genes;
	
	private Random random;
	
	public BinaryChromosome(int length) {
		random = new Random();
		
		genes = new boolean[length];
		for (int i = 0; i < genes.length; i++)
			genes[i] = random.nextBoolean();
	}
	
	public BinaryChromosome(boolean[] genes) {
		this.genes = genes;
		
		random = new Random();
	}

	public String stringOfGenes() {
		StringBuilder sb = new StringBuilder(genes.length);
		
		for (int i = 0; i < genes.length; i++)
			sb.append(genes[i] ? 1 : 0);
		
		return sb.toString();
	}
	
	@Override
	public int decode(int begin, int end) {
		int r = 0;
		int o = 1;
		for (int i = end - 1; i >= begin; i--) {
			if (genes[i])
				r += o;
			
			o *= 2;
		}
		
		return r;
	}

	@Override
	public void cross(Chromosome other, int begin, int end) {
		BinaryChromosome bc = (BinaryChromosome) other;
		for (int i = begin; i < end; i++) {
			boolean b = genes[i];
			genes[i] = bc.genes[i];
			bc.genes[i] = b;
		}
	}

	@Override
	public void mutate(int indexOfPoint) {
		genes[indexOfPoint] = !genes[indexOfPoint];
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		BinaryChromosome bc = (BinaryChromosome) super.clone();
		bc.genes = Arrays.copyOf(genes, genes.length);
		bc.random = new Random();
		
		return bc;
	}
	
	@Override
	public int getLength() {
		return genes.length;
	}
	
	protected boolean getGene(int index) {
		return genes[index];
	}
}
