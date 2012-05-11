// $Id: ParameterExtractorTest.java 937 2011-08-09 14:38:49Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.chain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import uk.icat.cmd.entity.State;
import uk.icat.cmd.exception.MissingMethodException;
import uk.icat.cmd.util.MethodHelper;
import uk.icat3.client.ICAT;
import uk.icat3.client.Investigation;

public class ParameterExtractorTest {

	private static final String METHOD_NAME = "createInvestigation";
	private ParameterExtractor parameterExtractor;
	private State state;
	private Command command;
	private MethodHelper methodHelper;
	private Method method;

	@Before
	public void setUp() throws SecurityException, NoSuchMethodException, MissingMethodException {
		state = new State();
		state.setMethod(ICAT.class.getMethod("createInvestigation", String.class, Investigation.class));
		state.setCreatedParameters(Arrays.asList("session_id", new Investigation()));

		parameterExtractor = new ParameterExtractor();

		command = mock(Command.class);
		parameterExtractor.setNext(command);

		methodHelper = new MethodHelper(ICAT.class);
		parameterExtractor.setMethodHelper(methodHelper);

		method = methodHelper.extractMethod(METHOD_NAME);
	}

	@Test
	public void shouldSetMethodName() throws Exception {
		state.setArgs(new String[] { METHOD_NAME });

		parameterExtractor.process(state);

		assertEquals(METHOD_NAME, state.getMethodName());
	}

	@Test
	public void shouldSetMethod() throws Exception {
		state.setArgs(new String[] { METHOD_NAME });

		parameterExtractor.process(state);

		assertEquals(method, state.getMethod());
	}

}
