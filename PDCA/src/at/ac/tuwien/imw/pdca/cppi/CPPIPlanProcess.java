package at.ac.tuwien.imw.pdca.cppi;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.PlanProcess;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import at.ac.tuwien.imw.pdca.cppi.service.CPPISimulation;

public class CPPIPlanProcess extends PlanProcess<Object> {
	private CPPIPlanningRules rules;
	private final static Logger log = LogManager.getLogger(PlanProcess.class);
	
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
		log.info("Planing Rules applied");
	}
	
	public CPPIPlanningRules getPlanningRules() {
		return this.rules;
	}
	
	public void setPlanningRules(CPPIPlanningRules rules) {
		this.rules = rules;
	}

}
