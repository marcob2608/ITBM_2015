package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.CheckingRules;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPICheckingRules implements CheckingRules{
	private static Logger log = LogManager.getLogger(CPPICheckingRules.class);

	@Override
	public void applyCheckingRules() {
		CPPIService service = CPPIService.getInstance();
		CPPIValues values = service.getCppiValues();
		
		//service.setCppiValues(new CPPIValues(service.getPlanConfiguration()));
	}

}
