package limundo;

import java.io.FileWriter;
import java.util.logging.Logger;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	
	public static void main(String[] args) {
		
		try {
			
			FileWriter fw = new FileWriter("test-report.txt", true);
			fw.write("");
			fw.flush();
			fw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Result result = JUnitCore.runClasses(LimundoAllTests.class);
		
		Logger l = Logger.getLogger(TestRunner.class.toString());
		
		for(Failure f : result.getFailures())
			l.warning(f.toString());
		
		l.info("Broj izvrsenih testova: "+result.getRunCount());
		l.info("Vreme izvrsenih testova: "+result.getRunTime());
		l.info("Broj preskocenih testova: "+result.getIgnoreCount());
		l.info("Broj uspesno izvrsenih testova: " + (result.getRunCount() - result.getIgnoreCount() - result.getFailureCount() - result.getAssumptionFailureCount()));
		l.info("Broj neuspesnih testova: "+result.getFailureCount());
		l.info("Broj dinamicki preskocenih testova: "+result.getAssumptionFailureCount());
		
		if(result.wasSuccessful())
			l.info("Svi testovi su uspesni!");
		else
			l.warning("Postoje greske u testovima!");
	}

}
