package uk.icat3.io.util;

import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EqualUtil {

	private static final Logger logger = LoggerFactory.getLogger(EqualUtil.class);

	BusinessKeyExtractor businessKeyExtractor = new BusinessKeyExtractor();

	public <T> boolean areEqual(T elem1, T elem2) {
		if (elem1 == null && elem2 == null) {
			return true;
		}
		if ((elem1 == null) ^ (elem2 == null)) {
			return false;
		}

		Class<?> clazz = elem1 != null ? elem1.getClass() : elem2.getClass();
		if (isPojo(clazz)) {
			return elem1.equals(elem2);
		}

		Set<String> propertiesToVerify = businessKeyExtractor.extractBusinessKey(elem1.getClass());

		try {
			for (String property : propertiesToVerify) {
				boolean result = areEqual(PropertyUtils.getProperty(elem1, property), PropertyUtils.getProperty(elem2, property));
				if (!result) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("Unable to compare instances of type {}", clazz, e);
			return false;
		}

		return true;
	}

	private <T> boolean isPojo(Class<?> clazz) {
		return clazz.getAnnotation(Entity.class) == null;
	}

}
