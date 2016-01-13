package at.ac.tuwien.imw.pdca.cppi;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.CheckProcess;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.ObjectiveSetting;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPICheckProcess extends CheckProcess<BigDecimal> implements Closeable {
	private CPPICheckingRules rules;
	private boolean running = true;

	public CPPICheckProcess(){
		setCheckingRules(new CPPICheckingRules());
	}

	@Override
	public void run() {
		while(CPPIService.getInstance().getPlanConfiguration().getRisklessAssetLastDays() >= CPPIService.getInstance().getCurrentPeriod()) {
			try {
				Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			CPPIService service = CPPIService.getInstance();

			//Measure process
			CPPIMeasureRules measurerules = new CPPIMeasureRules();
			MeasuredPerformanceValue<BigDecimal> performanceValue = measurerules.measure();

			//Check process
			ObjectiveSetting<BigDecimal> objective = new CPPIObjectiveSetting();
			objective.setObjectiveSetting(service.getCppiValues().getFloor());		
			rules.applyCheckingRules();
			Deviation<BigDecimal> deviation = getCheckResult(objective, performanceValue);
			service.setDeviationValue(deviation.getValue());
			
		}		
	}
	
	public void close() throws IOException {
		running = false;
	}


	public CPPICheckingRules getCheckingRules(){
		return this.rules;
	}

	public void setCheckingRules(CPPICheckingRules rules){
		this.rules = rules;
	}

	@Override
	public Deviation<BigDecimal> getCheckResult(ObjectiveSetting<BigDecimal> objective,
			MeasuredPerformanceValue<BigDecimal> performanceMeasureValue) {
		BigDecimal deviation = performanceMeasureValue.getValue().subtract(objective.getObjectiveSetting());
		return new CPPIDeviation(deviation);
	}

}
