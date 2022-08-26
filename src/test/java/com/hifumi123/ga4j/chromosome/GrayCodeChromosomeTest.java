package com.hifumi123.ga4j.chromosome;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GrayCodeChromosomeTest {
	
	@ParameterizedTest
	@MethodSource
	public void decodeTest(boolean[] genes, int[] begins, int[] ends, int[] results) {
		GrayCodeChromosome chromosome = new GrayCodeChromosome(genes);
		
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
		int[] results1 = {6, 5};
		
		boolean[] genes2 = {true, true, false, false, true, false, true, true};
		int[] begins2 = {0, 4};
		int[] ends2 = {4, 8};
		int[] results2 = {8, 13};
		
		return Stream.of(Arguments.of(genes1, begins1, ends1, results1), Arguments.of(genes2, begins2, ends2, results2));
	}
}
