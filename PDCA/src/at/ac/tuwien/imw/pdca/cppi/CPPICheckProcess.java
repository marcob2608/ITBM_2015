package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.CheckProcess;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.ObjectiveSetting;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPICheckProcess extends CheckProcess<BigDecimal> {
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
		CPPIService service = CPPIService.getInstance();
		CPPIMeasureRules measurerules = new CPPIMeasureRules();
		ObjectiveSetting<BigDecimal> objective = new CPPIObjectiveSetting();
		
		objective.setObjectiveSetting(service.getCppiValues().getFloor());
		
		MeasuredPerformanceValue<BigDecimal> value = measurerules.measure(); 
		
		Deviation<BigDecimal> deviation = getCheckResult(objective, value);
		
		service.setDeviationValue(deviation.getValue());
		
		//getcheckResult();
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
