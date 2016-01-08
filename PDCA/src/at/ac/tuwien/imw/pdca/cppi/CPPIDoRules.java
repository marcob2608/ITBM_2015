package at.ac.tuwien.imw.pdca.cppi;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.DoRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIDoRules implements DoRules{
	
	private final static Logger log = LogManager.getLogger(CPPIDoRules.class);
	
	@Override
	public void applyDoRules() {
		// Because this process does not actually sells and buys it only creates an output of the new portfolio
		log.info("New portfolio arrangement: (risky assessed)" + CPPIService.getInstance().getCppiValues().getPartRiskyAsset().toPlainString() + "; (riskless assessed)" + CPPIService.getInstance().getCppiValues().getPartRisklessAsset().toPlainString());
	}

}
