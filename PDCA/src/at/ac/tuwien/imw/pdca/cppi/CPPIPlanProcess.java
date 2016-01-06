package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanProcess;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIPlanProcess extends PlanProcess<CPPIPlanningRules> {
	private CPPIPlanningRules rules;
	
	public CPPIPlanProcess(){
		setPlanningRules(new CPPIPlanningRules());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.plan();
	}

	@Override
	public void plan() {
		this.rules.applyPlanningRules();
	}
	
	public CPPIPlanningRules getPlanningRules() {
		return this.rules;
	}
	
	public void setPlanningRules(CPPIPlanningRules rules) {
		this.rules = rules;
	}

}
