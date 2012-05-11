// $Id: ArgumentReader.java 935 2011-08-09 13:25:38Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.util.HelpUtil;
import uk.icat.cmd.util.MethodHelper;

public class ArgumentReader extends Command {

	private static final String LIST_FLAG = "-l";
	private static final String HELP_FLAG = "-h";
	private MethodHelper methodHelper;
	private HelpUtil helpUtil;

	@Override
	public void process(State state) throws Exception {
		if (state.getArgs().length == 0 || HELP_FLAG.equals(state.getArgs()[0])) {
			helpUtil.printHelp();
		} else if (LIST_FLAG.equals(state.getArgs()[0])) {
			helpUtil.printMethods(methodHelper.getMethods());
		} else if (state.getArgs().length == 2 && HELP_FLAG.equals(state.getArgs()[1])) {
			helpUtil.printEBBHelp(state.getArgs()[0]);
		} else {
			passToNext(state);
		}
	}

	public void setMethodHelper(MethodHelper methodHelper) {
		this.methodHelper = methodHelper;
	}
	
	public void setHelpUtil(HelpUtil helpUtil) {
		this.helpUtil = helpUtil;
	}

}
