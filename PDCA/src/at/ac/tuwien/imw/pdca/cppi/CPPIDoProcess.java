package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.DoProcess;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIDoProcess extends DoProcess {
	private CPPIDoRules rules;
	
	public CPPIDoProcess(){
		this.setDoRules(new CPPIDoRules());
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.operate();
	}

	@Override
	public void operate() {
		rules.applyDoRules();
	}
	
	public void setDoRules(CPPIDoRules rules){
		this.rules = rules;
	}
	
	public CPPIDoRules getDoRules(){
		return this.rules;
	}
}
