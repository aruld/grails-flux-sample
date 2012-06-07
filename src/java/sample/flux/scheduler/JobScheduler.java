package sample.flux.scheduler;

import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import flux.Configuration;
import flux.Engine;
import flux.EngineException;
import flux.EngineHelper;
import flux.Factory;
import flux.FlowChart;
import flux.JavaAction;
import flux.TimerTrigger;

public class JobScheduler {
	private static Logger log = Logger.getLogger(JobScheduler.class);
	 
    private static JobScheduler JOB_SCHEDULER = new JobScheduler();
    private Engine scheduler = null;
    private Factory factory = Factory.makeInstance();
    private EngineHelper helper = factory.makeEngineHelper();
 
    public JobScheduler() {
    }
 
    public static JobScheduler getInstance() {
        return JOB_SCHEDULER;
    }
    
    public void startup() {
        try {
            // and start it off
        	InputStream is = getClass().getClassLoader().getResourceAsStream("flux.properties");
        	Configuration config = factory.makeConfigurationFromProperties(is);
            scheduler = factory.makeEngine(config);
            scheduler.start();
            log.info("Scheduler started!");
 
            // define the job and tie it to our HelloJob class
            FlowChart job = helper.makeFlowChart("job1");
            job.setListenerClasspath("target/classes");
            JavaAction action = job.makeJavaAction("java action");
            action.setListener(HelloJob.class);

            // Trigger a job that repeats every 20 seconds
            TimerTrigger trigger = job.makeTimerTrigger("trigger1");
            trigger.setTimeExpression("+20s");
            trigger.addFlow(action);
            action.addFlow(trigger);

            log.info("Starting Jobs");
            // Tell flux to schedule the job
            scheduler.put(job);

        } catch (EngineException se) {
            se.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
 
    public void shutdown() {
        try {
            scheduler.dispose();
        } catch (EngineException se) {
            se.printStackTrace();
        } catch (RemoteException re) {
        	re.printStackTrace();
        }
    }
    
}
