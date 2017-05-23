package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Arrays;

class SlidingWindow {
	private int pos;
	private final double[] data;

	SlidingWindow(final int size){
		data = new double[size];
	}

	SlidingWindow(final double[] initData){
		data = Arrays.copyOf(initData, initData.length);
		pos = initData.length;
	}

	void put(final double value){
		data[pos % data.length] = value;
		pos++;
	}

	double[] getData(){
		final double[] res = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			res[i] = data[(i + pos) % data.length];
		}
		return res;
	}

}
