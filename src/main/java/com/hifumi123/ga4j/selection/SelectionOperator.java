package com.hifumi123.ga4j.selection;

import java.util.List;

import com.hifumi123.ga4j.AbstractIndividual;

public interface SelectionOperator {

	public List<AbstractIndividual> select(List<AbstractIndividual> individuals);
}
