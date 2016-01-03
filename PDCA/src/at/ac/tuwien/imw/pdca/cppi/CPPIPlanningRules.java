package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanningRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIPlanningRules<T> implements PlanningRules<T>{

	@Override
	public T applyPlanningRules() {
		CPPIService service = CPPIService.getInstance();
		CPPIPlanConfiguration conf = new CPPIPlanConfiguration();
		service.setPlanConfiguration(conf);
		return null;
	}

}
