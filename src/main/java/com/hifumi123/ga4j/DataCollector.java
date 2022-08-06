package com.hifumi123.ga4j;

import java.util.List;

public interface DataCollector {

	public void collectData(List<AbstractIndividual> individuals, int generation);
}
