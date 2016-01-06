package at.ac.tuwien.imw.pdca.cppi;

import at.ac.tuwien.imw.pdca.ActProcess;
import at.ac.tuwien.imw.pdca.CorrectiveActOutput;
import at.ac.tuwien.imw.pdca.Deviation;
import at.ac.tuwien.imw.pdca.cppi.service.CPPIService;

public class CPPIActProcess extends ActProcess {
	
	
	public CPPIActProcess(){
	
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(CPPIService.CONTROL_INTERVAL*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.operate();
	}


	@Override
	public CorrectiveActOutput act(Deviation deviation) {
		// TODO Auto-generated method stub
		return null;
	}
}
