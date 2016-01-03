package at.ac.tuwien.imw.pdca.cppi.service;

import at.ac.tuwien.imw.pdca.cppi.CPPICheckProcess;
import at.ac.tuwien.imw.pdca.cppi.CPPIPlanProcess;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CPPISimulation {

    private final static Logger log = LogManager.getLogger(CPPISimulation.class);

    // TODO Implement me
    // private static CPPIxyProcess xpProcess;
    // ...

    // TODO Implement me
    // private static Thread xyProcessThread;
    // ...

    public static void main(String[] args) {

        CPPIService.getInstance().init();

        //xyProcess = new CPPITSRxy();
        //xyProcessThread = new Thread(xyProcess);
        //xyProcessThread.start();

        //...
        new Thread(new CPPIPlanProcess()).start();
        new Thread(new CPPIStockPriceGenerator()).start();
        new Thread(new CPPICheckProcess()).start();
    }
}
