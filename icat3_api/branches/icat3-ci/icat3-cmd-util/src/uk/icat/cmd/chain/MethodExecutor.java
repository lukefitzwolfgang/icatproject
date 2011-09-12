// $Id: MethodExecutor.java 935 2011-08-09 13:25:38Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import java.lang.reflect.Method;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.exception.MethodCallException;
import uk.icat.cmd.util.HelpUtil;

public class MethodExecutor extends Command {

	private Object targetService;

	@Override
	public void process(State state) throws Exception {
		state.setResult(executeMethod(state.getMethod(), state.getCreatedParams().toArray()));
		passToNext(state);
	}

	public Object executeMethod(Method method, Object... args) throws MethodCallException {
		try {
			return method.invoke(targetService, args);
		} catch (Exception e) {
			HelpUtil.printMethodSignature(method);
			System.err.println("Unable to invoke method: " + e.getCause().getMessage());
			throw new MethodCallException("Unable to execute: " + method.getName());
		}
	}

	public void setTargetService(Object targetService) {
		this.targetService = targetService;
	}

}
