package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanProcess;

public class CPPIPlanProcess extends PlanProcess<CPPIPlanningRules> {
	private CPPIPlanningRules rules;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.plan();
	}

	@Override
	public void plan() {
		// TODO Auto-generated method stub
		this.rules.applyPlanningRules();
	}
	
	public CPPIPlanningRules getPlanningRules() {
		return this.rules;
	}
	
	public void setPlanningRules(CPPIPlanningRules rules) {
		this.rules = rules;
	}

}
