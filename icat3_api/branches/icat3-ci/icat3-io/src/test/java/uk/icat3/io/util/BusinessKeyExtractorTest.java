package uk.icat3.io.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uk.icat3.entity.Dataset;
import uk.icat3.entity.InvestigationType;

public class BusinessKeyExtractorTest {

	private BusinessKeyExtractor businessKeyExtractor;

	@Before
	public void setUp() {
		businessKeyExtractor = new BusinessKeyExtractor();
	}

	@Test
	public void shouldExtractInvestigationTypeBusinessKey() {
		Set<String> properties = businessKeyExtractor.extractBusinessKey(InvestigationType.class);

		assertNotNull(properties);
		assertEquals(1, properties.size());
		assertEquals("name", properties.iterator().next());
	}

	@Test
	public void shouldExtractDataSetBusinessKey() {
		Set<String> properties = businessKeyExtractor.extractBusinessKey(Dataset.class);

		assertNotNull(properties);
		assertEquals(4, properties.size());
		assertTrue(properties.contains("datasetType"));
		assertTrue(properties.contains("name"));
		assertTrue(properties.contains("investigation"));
		assertTrue(properties.contains("sampleId"));
	}

}
