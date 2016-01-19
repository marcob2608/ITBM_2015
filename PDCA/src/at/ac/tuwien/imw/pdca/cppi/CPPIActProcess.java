package at.ac.tuwien.imw.pdca.cppi;

import java.math.BigDecimal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.ActProcess;
import at.ac.tuwien.imw.pdca.CorrectiveActOutput;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIActProcess extends ActProcess {
	
	private final static Logger log = LogManager.getLogger(CPPIActProcess.class);
	private CPPIService service;
	private CPPICorrectiveActRules correctiveActRules;
	private BigDecimal oldPartRiskyAsset = new BigDecimal(0);
	
	public CPPIActProcess(){
		this.service = CPPIService.getInstance();
		this.correctiveActRules = new CPPICorrectiveActRules();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			act(new CPPIDeviation(service.getDeviationValue()));
		}
	}


	@Override
	public CorrectiveActOutput act(Deviation deviation) {
		if (service.getCurrentPeriod() == 0){
			oldPartRiskyAsset = service.getCppiValues().getPartRiskyAsset();
		}
		correctiveActRules.applyActRules();
		BigDecimal newPartRiskyAsset = service.getCppiValues().getPartRiskyAsset();
		BigDecimal correctiveAssets = newPartRiskyAsset.subtract(oldPartRiskyAsset);
		if(deviation.getValue() != BigDecimal.ZERO){
			if(correctiveAssets.compareTo(BigDecimal.ZERO) > 0){
//				log.info("Period " + service.getCurrentPeriod() + " Buy: " + correctiveAssets.setScale(4, BigDecimal.ROUND_HALF_UP) + " risky assets");
				return new CPPICorrectiveActOutput(correctiveAssets);
			} else if (correctiveAssets.compareTo(BigDecimal.ZERO) < 0) {
//				log.info("Period " + service.getCurrentPeriod() + " Sell: " + correctiveAssets.abs().setScale(4, BigDecimal.ROUND_HALF_UP) + " risky assets");
				return new CPPICorrectiveActOutput(correctiveAssets);
			}
		}
//		log.info("Period " + service.getCurrentPeriod() + " no change");
		return new CPPICorrectiveActOutput(correctiveAssets);
	}
}
