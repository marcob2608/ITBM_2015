package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanningRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CPPIPlanningRules<T> implements PlanningRules {
    private final static Logger log = LogManager.getLogger(CPPIPlanningRules.class);

    public void importValues() {
        CPPIService.getInstance().setPlanConfiguration(new CPPIPlanConfiguration());
        //log.info("STARTWERT" +CPPIService.getInstance().getCppiValues().getPortfolioValue());
    }


    @Override
    public T applyPlanningRules() {
        this.importValues();
        return null;
    }
}
