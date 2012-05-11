// $Id: ParameterExtractor.java 937 2011-08-09 14:38:49Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.util.MethodHelper;
import uk.icat.cmd.util.ParameterUtil;

public class ParameterExtractor extends Command {

	MethodHelper methodHelper;

	@Override
	public void process(State state) throws Exception {
		state.setMethodName(state.getArgs()[0]);
		state.setMethod(methodHelper.extractMethod(state.getMethodName()));
		state.setParameters(ParameterUtil.extractParameters(state.getMethod()));

		passToNext(state);
	}

	public void setMethodHelper(MethodHelper methodHelper) {
		this.methodHelper = methodHelper;
	}

}
