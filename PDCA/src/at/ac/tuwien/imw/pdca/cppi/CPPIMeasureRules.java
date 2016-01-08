package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.MeasureRules;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIMeasureRules implements MeasureRules<Object>{

	@Override
	public MeasuredPerformanceValue measure() {
		CPPIService service = CPPIService.getInstance();
		BigDecimal e = service.getCppiValues().getExposure();
		return new CPPIMeasuredPerformanceValue(e);
	}

}
