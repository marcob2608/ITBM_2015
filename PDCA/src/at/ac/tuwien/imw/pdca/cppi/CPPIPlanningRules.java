package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanningRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIPlanningRules implements PlanningRules<Object>{

	@Override
	public Object applyPlanningRules() {
		CPPIService service = CPPIService.getInstance();
		CPPIPlanConfiguration conf = new CPPIPlanConfiguration();
		CPPIValues values = new CPPIValues(conf);
		service.setPlanConfiguration(conf);
		service.setCppiValues(values);
		return null;
	}

}
