package at.ac.tuwien.imw.pdca.cppi.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author ivanstojkovic
 */
public class CPPIStockPriceGenerator implements Runnable {

    private final static Logger log = LogManager.getLogger(CPPIStockPriceGenerator.class.toString());


    public CPPIStockPriceGenerator() {
        super();
    }

    private synchronized void update() {
        synchronized (CPPIService.getInstance().lock) {
            CPPIService.getInstance().lock.notifyAll();
            try {
                if (CPPIService.getInstance().getCurrentPeriod() > 0) {
                    CPPIService.getInstance().lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CPPIService.getInstance().updateActualStockPrice();
    }


    public void run() {
        log.info("CPPIStockPriceGenerator process started");
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            update();
        }
    }

}
