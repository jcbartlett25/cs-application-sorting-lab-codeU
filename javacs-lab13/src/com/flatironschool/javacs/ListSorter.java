/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {

		int size = list.size();

		// Base Case
		if (size <= 1) {
			return list;
		}

		// Divide and conquer
		List<T> sublist1 = new LinkedList<T>(list.subList(0, size/2));
		List<T> sublist2 = new LinkedList<T>(list.subList(size/2, size));

		List<T> firstHalf = mergeSort(sublist1, comparator);
		List<T> secondHalf = mergeSort(sublist2, comparator);

		// Merge them back
		List<T> sorted = new LinkedList<T>();

		for (int i = 0; i < size; i++) {

			List<T> bigger = bigger(firstHalf, secondHalf, comparator);
			sorted.add(bigger.remove(0));
		}

		return sorted;
	}

	private List<T> bigger(List<T> firstList, List<T> secondList, Comparator<T> comparator) {

		
		// Easy to pick if they're empty
		if (firstList.size() == 0) 
			return secondList;		

		if (secondList.size() == 0) 
			return firstList;

		int result = comparator.compare(firstList.get(0), secondList.get(0));

		if (result > 0) 
			return secondList;
		
		if (result < 0) 
			return firstList;

		return firstList;
	} 

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {

		int size = list.size();
        PriorityQueue<T> heap = new PriorityQueue<T>(size, comparator);
		heap.addAll(list);

		list.clear();

		for(int i = 0; i < size; i++) {

			list.add(heap.poll());
		}
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        
        int size = list.size();

        PriorityQueue<T> heap = new PriorityQueue<T>(size, comparator);

        heap.addAll(list);

        List<T> topElements = new LinkedList<T>();

        for(int i=0; i<size-k; i++) {
        	heap.poll();
        }

        while(!heap.isEmpty()) {
        	topElements.add(heap.poll());
        }

        return topElements;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
