// $Id: ArgumentReader.java 935 2011-08-09 13:25:38Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.util.HelpUtil;
import uk.icat.cmd.util.MethodHelper;

public class ArgumentReader extends Command {

	MethodHelper methodHelper;

	@Override
	public void process(State state) throws Exception {
		if (state.getArgs().length == 0 || "-h".equals(state.getArgs()[0])) {
			HelpUtil.printHelp();
		} else if ("-l".equals(state.getArgs()[0])) {
			HelpUtil.printMethods(methodHelper.getMethods());
		} else {
			passToNext(state);
		}
	}

	public void setMethodHelper(MethodHelper methodHelper) {
		this.methodHelper = methodHelper;
	}

}
