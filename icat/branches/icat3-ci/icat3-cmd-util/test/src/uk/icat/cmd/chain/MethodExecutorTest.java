// $Id: MethodExecutorTest.java 937 2011-08-09 14:38:49Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import uk.icat.cmd.entity.State;
import uk.icat3.client.ICAT;
import uk.icat3.client.Investigation;

public class MethodExecutorTest {

	private State state;
	private MethodExecutor methodExecutor;
	private Command command;
	private ICAT icat;

	@Before
	public void setUp() throws SecurityException, NoSuchMethodException {
		state = new State();
		state.setMethod(ICAT.class.getMethod("createInvestigation", String.class, Investigation.class));
		state.setCreatedParameters(Arrays.asList("session_id", new Investigation()));

		methodExecutor = new MethodExecutor();

		command = mock(Command.class);
		methodExecutor.setNext(command);

		icat = mock(ICAT.class);
		methodExecutor.setTargetService(icat);
	}

	@Test
	public void shouldExecuteMethod() throws Exception {

		methodExecutor.process(state);

//		verify(icat).createInvestigation(any(String.class), any(Investigation.class));
	}

}
