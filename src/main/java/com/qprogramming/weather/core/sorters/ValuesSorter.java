package com.qprogramming.weather.core.sorters;

import java.util.Comparator;

import com.qprogramming.weather.core.Values;

public class ValuesSorter implements Comparator<Values> {

	private boolean isDescending;

	public ValuesSorter(boolean isDescending) {
		this.isDescending = isDescending;
	}

	@Override
	public int compare(Values v1, Values v2) {
		int result = 0;
		if (v1.getTimestamp().isBefore(v2.getTimestamp())) {
			result = -1;
		} else if (v1.getTimestamp().isEqual(v2.getTimestamp())) {
			result = 0;
		} else {
			result = -1;
		}
		return isDescending ? -result : result;
	}

}
