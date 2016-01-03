package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.DoProcess;

public class CPPIDoProcess extends DoProcess {
	private CPPIDoRules rules;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		operate();
	}

	@Override
	public void operate() {
		// TODO Auto-generated method stub
		rules.applyDoRules();
	}
	
	public void setDoRules(CPPIDoRules rules){
		this.rules = rules;
	}
	
	public CPPIDoRules getDoRules(){
		return this.rules;
	}
}
