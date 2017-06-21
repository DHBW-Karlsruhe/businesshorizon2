package dhbw.ka.mwi.businesshorizon2.demo.saving;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface Exporter {

    void doExport(File file) throws IOException;
}
