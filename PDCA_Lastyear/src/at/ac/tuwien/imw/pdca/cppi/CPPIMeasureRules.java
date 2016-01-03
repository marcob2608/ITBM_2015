package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.MeasureRules;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Huggy on 17.05.2014.
 */
public class CPPIMeasureRules implements MeasureRules {
    private final static Logger log = LogManager.getLogger(CPPIMeasureRules.class);

    @Override
    public MeasuredPerformanceValue measure() {
        MeasuredPerformanceValue<BigDecimal> oldTSR = CPPIService.getInstance().getCurrentTSR();
        BigDecimal currentStockPrice = CPPIService.getInstance().getCurrentStockPrice();
        BigDecimal oldStockPrice = CPPIService.getInstance().getPreviousStockPrice();
        BigDecimal tsrbd = currentStockPrice.divide(oldStockPrice, 15, RoundingMode.HALF_UP).subtract(new BigDecimal(1));
        CPPIMeasuredPerformanceValue tsr = new CPPIMeasuredPerformanceValue(tsrbd);
        CPPIService.getInstance().setCurrentTSR(tsr);
        // log.info("Setze aktuellen TSR auf" + tsrbd.round(MathContext.DECIMAL32));
        //  log.info("Aktueller Stock Preis" + CPPIService.getInstance().getCurrentStockPrice());
        //   log.info("ALTER Stock Preis" + CPPIService.getInstance().getPreviousStockPrice());
        BigDecimal tsrChange = tsrbd.subtract(oldTSR.getValue());
        CPPIService.getInstance().setTSRChange(new CPPIDeviation(tsrChange));
        return tsr;
    }
}
