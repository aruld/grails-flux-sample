package sample.flux.scheduler;

import java.util.Date;

import flux.ActionListener;
import flux.KeyFlowContext;

public class HelloJob implements ActionListener {

	@Override
	public Object actionFired(KeyFlowContext arg0) throws Exception {
		System.out.println("HelloJob is executing. " + new Date());
		return null;
	}
	
}
