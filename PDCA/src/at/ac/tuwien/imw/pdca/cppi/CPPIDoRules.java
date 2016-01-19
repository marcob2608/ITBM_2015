package at.ac.tuwien.imw.pdca.cppi;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.DoRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

import java.math.BigDecimal;

public class CPPIDoRules implements DoRules{
	
	private final static Logger log = LogManager.getLogger(CPPIDoRules.class);
	
	@Override
	public void applyDoRules() {
		CPPIService service = CPPIService.getInstance();
		// Because this process does not actually sells and buys it only creates an output of the new portfolio
//		log.info("Period " + CPPIService.getInstance().getCurrentPeriod() + " New portfolio arrangement after transaction: (risky assessed)" + CPPIService.getInstance().getCppiValues().getPartRiskyAsset().setScale(4, BigDecimal.ROUND_HALF_UP) + "; (riskless assessed)" + CPPIService.getInstance().getCppiValues().getPartRisklessAsset().setScale(4, BigDecimal.ROUND_HALF_UP));
		service.setCppiValues(new CPPIValues(service.getPlanConfiguration()));
	}

}
