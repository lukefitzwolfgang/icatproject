package uk.icat.cmd.custom;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import uk.icat3.client.EntityBaseBean;

public class CreateProcessorTest {

	@Test
	public void test() throws IOException {
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		Resource[] resources = resolver.getResources("classpath:uk/icat3/client/*.class");
//		assertNotNull(resources);
//		
//		System.out.println(Arrays.toString(resources));
//	
//		
//		AssignableTypeFilter filter = new AssignableTypeFilter(EntityBaseBean.class);
//		
//		filter.match(metadataReader, metadataReaderFactory)
//		
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(EntityBaseBean.class));
		Set<BeanDefinition> candidates = provider.findCandidateComponents("uk.icat3.client");
		for (BeanDefinition bd : candidates) {
			System.out.println(bd.getBeanClassName());
		}
//		assertTrue(containsBeanClass(candidates, StubFooDao.class));

		
	}

}
