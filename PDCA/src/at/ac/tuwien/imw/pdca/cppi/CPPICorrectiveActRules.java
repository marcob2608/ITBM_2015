package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.*;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPICorrectiveActRules implements CorrectiveActRules{

	private final static Logger log = LogManager.getLogger(CPPICorrectiveActRules.class);
	private CPPIService service;
	
	@Override
	public void applyActRules() {
		service = CPPIService.getInstance();
		CPPIValues values = service.getCppiValues();
		CPPIPlanConfiguration config = service.getPlanConfiguration();
		
		BigDecimal riskyAsset = config.getLaverage().multiply(values.getCushion()).min(values.getPortfolio());
		BigDecimal risklessAsset = values.getPortfolio().subtract(riskyAsset);
		
		values.setPartRiskyAsset(riskyAsset);
		values.setPartRisklessAsset(risklessAsset);
		
		//log.info("Period " + service.getCurrentPeriod() + " New act configuration: Hold " + riskyAsset.setScale(4, BigDecimal.ROUND_HALF_UP) + " risky assets and " + risklessAsset.setScale(4, BigDecimal.ROUND_HALF_UP) + " riskless assets");
		
	}

}
