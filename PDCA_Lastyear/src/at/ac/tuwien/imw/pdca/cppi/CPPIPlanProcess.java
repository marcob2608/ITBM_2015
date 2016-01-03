package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.PlanProcess;
import at.ac.tuwien.imw.pdca.PlanningRules;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by Felix on 17.05.14.
 */
public class CPPIPlanProcess extends PlanProcess {
    private final static Logger log = LogManager.getLogger(CPPIPlanProcess.class);

    @Override
    public void plan() {
        PlanningRules planningRules = new CPPIPlanningRules();
        planningRules.applyPlanningRules();
    }


    @Override
    public void run() {
        plan();
        log.info("Terminate Thread CPPIPlanProcess");
    }
}
