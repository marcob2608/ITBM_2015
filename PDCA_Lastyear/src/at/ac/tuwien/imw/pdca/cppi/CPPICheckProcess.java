package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.CheckProcess;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.ObjectiveSetting;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Created by Felix on 17.05.14.
 */
public class CPPICheckProcess extends CheckProcess {
    private final static Logger log = LogManager.getLogger(CPPICheckProcess.class);
    private int lastCheckedPeriod = 0;

    @Override
    public Deviation getCheckResult(ObjectiveSetting objective, MeasuredPerformanceValue performanceMeasureValue) {
        BigDecimal tsrChange = (BigDecimal) objective.getObjectiveSetting();
        if (tsrChange.compareTo(new BigDecimal(0)) != 0) {
            return new CPPIDeviation(tsrChange);
        } else {
            return new CPPIDeviation(new BigDecimal(0));
        }
    }

    public synchronized void check() throws InterruptedException {
        synchronized (CPPIService.getInstance().lock) {
            CPPIService.getInstance().lock.wait();
            CPPIMeasureRules measureRules = new CPPIMeasureRules();
            this.performanceValue = measureRules.measure();
            this.objectiveSetting = new CPPIObjectiveSetting<BigDecimal>();
            this.objectiveSetting.setObjectiveSetting(CPPIService.getInstance().getPlanConfiguration().getPortfolio());
            this.getCheckResult(objectiveSetting, performanceValue);
            CPPICheckingRules checkingRules = new CPPICheckingRules();
            checkingRules.applyCheckingRules();
            CPPIService.getInstance().lock.notifyAll();
        }
    }

    @Override
    public void run() {
        while (this.lastCheckedPeriod <= CPPIService.getInstance().getPlanConfiguration().getRisklessAssetLastDays()) {
            //  if (this.lastCheckedPeriod < CPPIService.getInstance().getCurrentPeriod()) {
            try {
                check();
                this.lastCheckedPeriod++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //  }
        }
    }
}
