package com.hifumi123.ga4j.chromosome;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BinaryChromosomeTest {
	
	@ParameterizedTest
	@MethodSource
	public void stringOfGenesTest(boolean[] genes, String result) {
		BinaryChromosome chromosome = new BinaryChromosome(genes);
		
		Assertions.assertEquals(result, chromosome.stringOfGenes());
	}
	
	private static Stream<Arguments> stringOfGenesTest() {
		boolean[] genes1 = {true, false, true, true, true, true};
		String result1 = "101111";
		
		boolean[] genes2 = {true, true, false, false, true, false, true, true};
		String result2 = "11001011";
		
		return Stream.of(Arguments.of(genes1, result1), Arguments.of(genes2, result2));
	}
	
	@ParameterizedTest
	@MethodSource
	public void decodeTest(boolean[] genes, int[] begins, int[] ends, int[] results) {
		BinaryChromosome chromosome = new BinaryChromosome(genes);
		
		for (int i = 0; i < begins.length; i++) {
			int begin = begins[i];
			int end = ends[i];
			int result = results[i];
			
			Assertions.assertEquals(result, chromosome.decode(begin, end));
		}
	}
	
	private static Stream<Arguments> decodeTest() {
		boolean[] genes1 = {true, false, true, true, true, true};
		int[] begins1 = {0, 3};
		int[] ends1 = {3, 6};
		int[] results1 = {5, 7};
		
		boolean[] genes2 = {true, true, false, false, true, false, true, true};
		int[] begins2 = {0, 4};
		int[] ends2 = {4, 8};
		int[] results2 = {12, 11};
		
		return Stream.of(Arguments.of(genes1, begins1, ends1, results1), Arguments.of(genes2, begins2, ends2, results2));
	}
	
	@ParameterizedTest
	@MethodSource
	public void crossTest(boolean[] genes1, boolean[] genes2, int begin, int end, String[] result) {
		BinaryChromosome c1 = new BinaryChromosome(genes1);
		BinaryChromosome c2 = new BinaryChromosome(genes2);
		
		c1.cross(c2, begin, end);
		
		String[] strs = {c1.stringOfGenes(), c2.stringOfGenes()};
		
		Assertions.assertArrayEquals(result, strs);
	}
	
	private static Stream<Arguments> crossTest() {
		boolean[] genes1 = {false, true, true, false, false, true, false, false};
		boolean[] genes2 = {false, false, false, true, true, false, false, true};
		
		int begin = 2;
		int end = 7;
		
		String[] result = {"01011000", "00100101"};
		
		return Stream.of(Arguments.of(genes1, genes2, begin, end, result));
	}
	
	@ParameterizedTest
	@MethodSource
	public void mutateTest(boolean[] genes, int indexOfPoint, String result) {
		BinaryChromosome chromosome = new BinaryChromosome(genes);
		chromosome.mutate(indexOfPoint);
		
		Assertions.assertEquals(result, chromosome.stringOfGenes());
	}
	
	private static Stream<Arguments> mutateTest() {
		boolean[] genes1 = {false, true, true, false, false, true, false, false};
		int indexOfPoint1 = 3;
		String result1 = "01110100";
		
		boolean[] genes2 = {false, false, false, true, true, false, false, true};
		int indexOfPoint2 = 6;
		String result2 = "00011011";
		
		return Stream.of(Arguments.of(genes1, indexOfPoint1, result1), Arguments.of(genes2, indexOfPoint2, result2));
	}
}
