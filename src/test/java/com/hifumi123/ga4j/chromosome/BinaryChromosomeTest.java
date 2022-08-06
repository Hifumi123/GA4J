package com.hifumi123.ga4j.chromosome;

import java.util.Random;

public class BinaryChromosomeTest {

	public static void main(String[] args) {
		BinaryChromosome bc1 = new BinaryChromosome(6);
		BinaryChromosome bc2 = new BinaryChromosome(6);
		
		System.out.println(bc1.stringOfGenes());
		System.out.println(bc2.stringOfGenes());
		
		System.out.println("---");
		
		System.out.print(bc1.decode(0, 6) + ", ");
		System.out.print(bc1.decode(0, 3) + ", ");
		System.out.println(bc1.decode(3, 6));
		System.out.print(bc2.decode(0, 6) + ", ");
		System.out.print(bc2.decode(0, 3) + ", ");
		System.out.println(bc2.decode(3, 6));
		
		System.out.println("---");
		
		Random random = new Random();
		
		int begin = random.nextInt(5) + 1;
		System.out.println(begin);
		
		System.out.println(bc1.stringOfGenes());
		System.out.println(bc2.stringOfGenes());
		bc1.cross(bc2, begin, 6);
		System.out.println(bc1.stringOfGenes());
		System.out.println(bc2.stringOfGenes());
		
		System.out.println("---");
		
		int indexOfPoint = random.nextInt(6);
		System.out.println(indexOfPoint);
		
		System.out.println(bc1.stringOfGenes());
		System.out.println(bc2.stringOfGenes());
		bc1.mutate(indexOfPoint);
		bc2.mutate(indexOfPoint);
		System.out.println(bc1.stringOfGenes());
		System.out.println(bc2.stringOfGenes());
	}
}
