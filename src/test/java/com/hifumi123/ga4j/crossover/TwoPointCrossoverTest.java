package com.hifumi123.ga4j.crossover;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.hifumi123.ga4j.AbstractIndividual;
import com.hifumi123.ga4j.Population;
import com.hifumi123.ga4j.chromosome.BinaryChromosome;
import com.hifumi123.ga4j.chromosome.Chromosome;

public class TwoPointCrossoverTest {
	
	private class CustomIndividual extends AbstractIndividual {

		public CustomIndividual(Chromosome chromosome) {
			super(chromosome);
		}

		@Override
		protected void customClone(AbstractIndividual newIndividual) {}
	}
	
	private boolean isRight(BinaryChromosome oc1, BinaryChromosome oc2, BinaryChromosome nc1, BinaryChromosome nc2) {
		String os1 = oc1.stringOfGenes();
		String os2 = oc2.stringOfGenes();
		String ns1 = nc1.stringOfGenes();
		String ns2 = nc2.stringOfGenes();
		
		int index = 0;
		
		while (index < os1.length())
			if (ns1.charAt(index) != os1.charAt(index) || ns2.charAt(index) != os2.charAt(index))
				break;
			else
				index++;
		
		int index1 = index;
		
		if (index == 0)
			return false;
		
		while (index < os1.length())
			if (ns1.charAt(index) != os2.charAt(index) || ns2.charAt(index) != os1.charAt(index))
				break;
			else
				index++;
		
		if (index == index1 && index != os1.length())
			return false;
		
		while (index < os1.length())
			if (ns1.charAt(index) != os1.charAt(index) || ns2.charAt(index) != os2.charAt(index))
				break;
			else
				index++;
		
		if (index != os1.length())
			return false;
		
		return true;
	}

	@ParameterizedTest
	@MethodSource
	public void crossTest(BinaryChromosome oc1, BinaryChromosome oc2, double probabilityOfCrossover, int n) {
		try {
			for (int i = 0; i < n; i++) {
				Population population = new Population();
				
				BinaryChromosome nc1 = (BinaryChromosome) oc1.clone();
				BinaryChromosome nc2 = (BinaryChromosome) oc2.clone();
				
				CustomIndividual ci1 = new CustomIndividual(nc1);
				CustomIndividual ci2 = new CustomIndividual(nc2);
				
				population.addToEvolvingGroup(ci1);
				population.addToEvolvingGroup(ci2);
				
				TwoPointCrossover crossover = new TwoPointCrossover();
				crossover.cross(population, probabilityOfCrossover);
				
				Assertions.assertTrue(isRight(oc1, oc2, nc1, nc2));
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	private static Stream<Arguments> crossTest() {
		int length = 10;
		double probabilityOfCrossover = 1;
		int n = 10;
		
		BinaryChromosome oc1 = new BinaryChromosome(length);
		BinaryChromosome oc2 = new BinaryChromosome(length);
		
		return Stream.of(Arguments.of(oc1, oc2, probabilityOfCrossover, n));
	}
}
