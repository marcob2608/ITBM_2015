package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import at.ac.tuwien.imw.pdca.MeasureRules;
import at.ac.tuwien.imw.pdca.MeasuredPerformanceValue;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIMeasureRules implements MeasureRules<Object>{

	@Override
	public MeasuredPerformanceValue measure() {
		CPPIService service = CPPIService.getInstance();
		CPPIValues values = service.getCppiValues();
		CPPIPlanConfiguration config = service.getPlanConfiguration();
		BigDecimal stockPricePrevious = service.getPreviousStockPrice();
		BigDecimal stockPriceCurrent = service.getCurrentStockPrice();
		
		//TSR
		MeasuredPerformanceValue<BigDecimal> oldTsr = service.getCurrentTSR();
		BigDecimal TSRNew = stockPriceCurrent.divide(stockPricePrevious, 6, BigDecimal.ROUND_UP).subtract(BigDecimal.ONE);
		CPPITSR cppiTsr = new CPPITSR(TSRNew);
		service.setCurrentTSR(cppiTsr);
		service.setTSRChange(new CPPIDeviation(TSRNew.subtract(oldTsr.getValue())));
		
		//Portfolio wealth
		BigDecimal risklessWealth = values.getPartRisklessAsset().multiply(BigDecimal.ONE.add(config.getRisklessAssetInterest()).pow(service.getCurrentPeriod()/config.getRisklessAssetLastDays()));
		BigDecimal riskfullWealth = values.getPartRiskyAsset().multiply(BigDecimal.ONE.add(cppiTsr.getValue()));
		BigDecimal totalWealth = risklessWealth.add(riskfullWealth);
		CPPIMeasuredPerformanceValue performance = new CPPIMeasuredPerformanceValue(totalWealth);
		
		return performance;
	}

}
