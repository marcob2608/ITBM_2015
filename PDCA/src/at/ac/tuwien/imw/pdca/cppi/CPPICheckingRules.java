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
		
		//Floor
		BigDecimal timeExpired = new BigDecimal((service.getCurrentPeriod())).divide(new BigDecimal(values.getConf().getRisklessAssetLastDays()), 4, RoundingMode.HALF_UP);
		BigDecimal term = BigDecimal.ONE.subtract(timeExpired);
		BigDecimal planFloor = values.getFloor();
		double Rt = BigDecimal.ONE.add(values.getConf().getRisklessAssetInterest()).doubleValue();
		BigDecimal floor = planFloor.divide(new BigDecimal(Math.pow(Rt, term.doubleValue())), 4, RoundingMode.HALF_UP);
		//TODO floor irgendwo setzen????
				
		//Cushion
		BigDecimal cushion = values.getPortfolio().subtract(floor).max(BigDecimal.ZERO);
		//TODO cushion irgendwo setzen???
		
		log.info("Period " + service.getCurrentPeriod() + ": Floor " + floor.setScale(4, RoundingMode.HALF_UP) + "; Cushion " + cushion.setScale(4, RoundingMode.HALF_UP));
	}

}
