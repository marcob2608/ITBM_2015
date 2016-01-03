package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;

import java.math.BigDecimal;

/**
 * Total shareholder return
 *
 * @author ivanstojkovic
 */
public class CPPITSR extends MeasuredPerformanceValue<BigDecimal> {

    public CPPITSR(BigDecimal value) {
        super(value);
    }
}
