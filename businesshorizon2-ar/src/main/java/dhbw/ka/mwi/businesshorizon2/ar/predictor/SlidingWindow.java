package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Arrays;

/**
 * Datentyp der ein feste Anzahl an Werten speichern kann
 * Dies funktioniert über ein Array, welches einen künstlichen Anfang hat(pos)
 */
class SlidingWindow {
    /**
     * Position des eigentlichen Anfangs
     */
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

    /**
     * Gibt einem alle Werte vom index pos bis zum Ende des Arrays
     * und vom Anfang des Arrays bis zum index pos-1 zurück
     * @return Die Werte des SlidingWindows
     */
	double[] getData(){
		final double[] res = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			res[i] = data[(i + pos) % data.length];
		}
		return res;
	}

}
