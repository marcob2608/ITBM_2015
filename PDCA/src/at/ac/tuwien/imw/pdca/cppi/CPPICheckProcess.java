package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.CheckProcess;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.ObjectiveSetting;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPICheckProcess<T> extends CheckProcess<T> {
	private CPPICheckingRules rules;
	
	public CPPICheckProcess(){
		setCheckingRules(new CPPICheckingRules());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//getcheckResult();
	}

	@Override
	public Deviation getCheckResult(ObjectiveSetting<T> objective,
			MeasuredPerformanceValue<T> performanceMeasureValue) {
		BigDecimal objectivevalue = (BigDecimal) objective.getObjectiveSetting();
		BigDecimal measurevalue = (BigDecimal) performanceMeasureValue.getValue();
		return new CPPITSRChange(objectivevalue.subtract(measurevalue));
		}
	
	public CPPICheckingRules getCheckingRules(){
		return this.rules;
	}
	
	public void setCheckingRules(CPPICheckingRules rules){
		this.rules = rules;
	}

}
