package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.MeasureRules;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CPPIMeasureRules implements MeasureRules<Object>{
	private static Logger log = LogManager.getLogger(CPPIMeasureRules.class);

	@Override
	public MeasuredPerformanceValue measure() {
		CPPIService service = CPPIService.getInstance();
		CPPIValues values = service.getCppiValues();
		CPPIPlanConfiguration config = service.getPlanConfiguration();
		BigDecimal stockPricePrevious = service.getPreviousStockPrice();
		BigDecimal stockPriceCurrent = service.getCurrentStockPrice();
		
		//TSR
		MeasuredPerformanceValue<BigDecimal> oldTsr = service.getCurrentTSR();
		BigDecimal TSRNew = stockPriceCurrent.divide(stockPricePrevious, 10, BigDecimal.ROUND_UP).subtract(BigDecimal.ONE);
		CPPITSR cppiTsr = new CPPITSR(TSRNew);
		service.setCurrentTSR(cppiTsr);
		service.setTSRChange(new CPPIDeviation(TSRNew.subtract(oldTsr.getValue())));
		log.info("Period " + service.getCurrentPeriod() + " current Tsr: " + TSRNew);
		
		//Portfolio wealth
		BigDecimal risklessWealth = (values.getPartRisklessAsset()).multiply((BigDecimal.ONE.add(config.getRisklessAssetInterest())).pow(1/config.getRisklessAssetLastDays()));
		BigDecimal riskfullWealth = values.getPartRiskyAsset().multiply(BigDecimal.ONE.add(TSRNew));
		BigDecimal totalWealth = risklessWealth.add(riskfullWealth);
		CPPIMeasuredPerformanceValue performance = new CPPIMeasuredPerformanceValue(totalWealth);

		//if (service.getCurrentPeriod() > 0) {
			service.getPlanConfiguration().setPortfolio(totalWealth);
		//}
		return performance;
	}

}
