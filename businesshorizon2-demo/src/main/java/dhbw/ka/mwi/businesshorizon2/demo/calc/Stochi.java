package dhbw.ka.mwi.businesshorizon2.demo.calc;

import java.util.function.DoubleSupplier;
import java.util.stream.IntStream;

final class Stochi {

    private Stochi() {
    }

    static double[] doStochi(final int iterations, final DoubleSupplier supplier){
        return IntStream.range(0,iterations).parallel().mapToDouble(value -> supplier.getAsDouble()).toArray();
    }
}
