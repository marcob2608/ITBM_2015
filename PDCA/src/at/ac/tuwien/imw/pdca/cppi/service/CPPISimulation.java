package at.ac.tuwien.imw.pdca.cppi.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import at.ac.tuwien.imw.pdca.cppi.CPPIActProcess;
import at.ac.tuwien.imw.pdca.cppi.CPPICheckProcess;
import at.ac.tuwien.imw.pdca.cppi.CPPIDoProcess;
import at.ac.tuwien.imw.pdca.cppi.CPPIPlanProcess;

public class CPPISimulation {

	private final static Logger log = LogManager.getLogger(CPPISimulation.class);

	private static CPPIPlanProcess planProcess;
	private static CPPIDoProcess doProcess;
	private static CPPICheckProcess checkProcess;
	private static CPPIActProcess actProcess;

	private static Thread planThread;
	private static Thread doThread;
	private static Thread checkThread;
	private static Thread actThread;

	public static void main(String[] args) {

		BasicConfigurator.configure();
		CPPIService.getInstance().init();
		try {
			new Thread(new CPPIStockPriceGenerator()).start();

			planProcess = new CPPIPlanProcess();
			planThread = new Thread(planProcess);
			planThread.start();
			Thread.sleep(50);
			log.info("PlanProcess started");

			checkProcess = new CPPICheckProcess();
			checkThread = new Thread(checkProcess);
			checkThread.start();
			Thread.sleep(50);
			log.info("CheckProcess started");

			actProcess = new CPPIActProcess();
			actThread = new Thread(actProcess);
			actThread.start();
			Thread.sleep(50);
			log.info("ActProcess started");

			doProcess = new CPPIDoProcess();
			doThread = new Thread(doProcess);
			doThread.start();
			log.info("DoProcess started");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
