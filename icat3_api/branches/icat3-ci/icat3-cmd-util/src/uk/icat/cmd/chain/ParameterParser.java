// $Id: ParameterParser.java 935 2011-08-09 13:25:38Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import java.util.ArrayList;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.util.IcatUtil;
import uk.icat.cmd.util.ParameterUtil;

public class ParameterParser extends Command {

	IcatUtil icatUtil;

	@Override
	public void process(State state) throws Exception {
		ArrayList<Object> createdParams = new ArrayList<Object>();
		createdParams.add(icatUtil.getSid());
		for (int i = 1; i < state.getParameters().size(); i++) { // first parameter is sessionId
			Object parameter = ParameterUtil.createParameterInstance(state.getParameters().get(i), state.getCommandLine().getOptions(), i);
			createdParams.add(parameter);
		}

		state.setCreatedParameters(createdParams);

		passToNext(state);
	}

	public void setIcatUtil(IcatUtil icatUtil) {
		this.icatUtil = icatUtil;
	}

}
