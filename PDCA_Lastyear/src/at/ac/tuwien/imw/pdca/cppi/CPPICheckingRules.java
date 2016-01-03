package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.CheckingRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by Huggy on 17.05.2014.
 */
public class CPPICheckingRules implements CheckingRules {
    private final static Logger log = LogManager.getLogger(CPPICheckingRules.class);

    @Override
    public void applyCheckingRules() {
        CPPIValues values = new CPPIValues(CPPIService.getInstance().getPlanConfiguration());
     //   values.logEntry();
    }
}
