package com.hifumi123.ga4j;

import java.util.ArrayList;
import java.util.List;

public class ParallelList<E> {

	List<List<E>> listOfList;

	public ParallelList(int n) {
		listOfList = new ArrayList<List<E>>();
		for (int i = 0; i < n; i++)
			listOfList.add(new ArrayList<E>());
	}
	
	private List<E> getListWithLeastElements() {
		List<E> result = listOfList.get(0);
		for (int i = 1; i < listOfList.size(); i++) 
			if (listOfList.get(i).size() < result.size())
				result = listOfList.get(i);
		
		return result;
	}
	
	public void add(E e) {
		getListWithLeastElements().add(e);
	}
	
	public int sizeOfList() {
		return listOfList.size();
	}
	
	public List<E> getList(int index) {
		return listOfList.get(index);
	}
	
	public void distributeElementsEvenly() {
		//TODO
	}
}
