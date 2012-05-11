package uk.icat3.io.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import uk.icat3.entity.Dataset;
import uk.icat3.entity.IcatRole;
import uk.icat3.entity.Investigation;

public class EqualUtilTest {

	private EqualUtil equalUtil;

	@Before
	public void setUp() {
		equalUtil = new EqualUtil();
	}

	@Test
	public void shouldReturnFalseIfNullArgumentPassed() {
		assertFalse(equalUtil.areEqual(new Investigation(), null));
	}

	@Test
	public void shouldBeAbleToCompareInvestigations() {
		Investigation i1 = new Investigation();
		Investigation i2 = new Investigation();

		assertTrue(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnTrue_1() {
		Investigation i1 = new Investigation();
		i1.setId(123L);
		Investigation i2 = new Investigation();

		assertTrue(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnTrue_2() {
		Investigation i1 = new Investigation();
		i1.setIcatRole(new IcatRole("test"));
		Investigation i2 = new Investigation();

		assertTrue(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnTrue_3() {
		Investigation i1 = new Investigation();
		i1.setInvNumber("123");
		Investigation i2 = new Investigation();
		i2.setInvNumber("123");

		assertTrue(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnTrue_4() {
		Investigation i1 = new Investigation();
		Dataset d1 = new Dataset();
		d1.setName("dataset");
		i1.setDatasetCollection(Arrays.asList(d1));

		Investigation i2 = new Investigation();
		Dataset d2 = new Dataset();
		d2.setName("dataset");
		i1.setDatasetCollection(Arrays.asList(d2));

		assertTrue(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnTrue_5() {
		Investigation i1 = new Investigation();
		i1.setInvNumber("123");
		Dataset d1 = new Dataset();
		d1.setName("dataset");
		d1.setInvestigation(i1);

		Investigation i2 = new Investigation();
		i2.setInvNumber("123");
		Dataset d2 = new Dataset();
		d2.setName("dataset");
		d2.setInvestigation(i2);

		assertTrue(equalUtil.areEqual(d1, d2));
	}

	@Test
	public void shouldReturnFalse_1() {
		Investigation i1 = new Investigation();
		i1.setInvNumber("123");
		Investigation i2 = new Investigation();

		assertFalse(equalUtil.areEqual(i1, i2));
	}

	@Test
	public void shouldReturnFalse_2() {
		Investigation i1 = new Investigation();
		i1.setInvNumber("123");
		Dataset d1 = new Dataset();
		d1.setName("dataset");
		d1.setInvestigation(i1);

		Investigation i2 = new Investigation();
		i2.setInvNumber("456");
		Dataset d2 = new Dataset();
		d2.setName("dataset");
		d2.setInvestigation(i2);

		assertFalse(equalUtil.areEqual(d1, d2));
	}

	@Test
	public void shouldBeAbleToCompareDatasets() {
		Dataset d1 = new Dataset();
		Dataset d2 = new Dataset();

		assertTrue(equalUtil.areEqual(d1, d2));
	}

	@Test
	public void shouldBeAbleToCompareStrings() {
		String s1 = "test";
		String s2 = "test";

		assertTrue(equalUtil.areEqual(s1, s2));
	}

	@Test
	public void shouldBeAbleToDistinguishStrings() {
		String s1 = "test";
		String s2 = "other";

		assertFalse(equalUtil.areEqual(s1, s2));
	}

}
