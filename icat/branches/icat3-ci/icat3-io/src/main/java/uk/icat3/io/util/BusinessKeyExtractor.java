package uk.icat3.io.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class BusinessKeyExtractor {

	private static Map<Class<?>, Set<String>> businessKeyCache = Collections.synchronizedMap(new HashMap<Class<?>, Set<String>>());

	public Set<String> extractBusinessKey(Class<?> clazz) {
		synchronized (clazz) {
			if (businessKeyCache.containsKey(clazz)) {
				return businessKeyCache.get(clazz);
			}

			Set<String> result = getFieldNames(clazz);
			businessKeyCache.put(clazz, result);

			return result;
		}
	}

	private Set<String> getFieldNames(Class<?> clazz) {
		Set<String> uniqueConstraints = processUniqueConstraintsAnnotation(clazz);
		if (uniqueConstraints.size() > 0) {
			return uniqueConstraints;
		} else {
			return processIdAnnotation(clazz);
		}
	}

	private Set<String> processUniqueConstraintsAnnotation(Class<?> clazz) {
		Table tableAnnotation = clazz.getAnnotation(Table.class);
		if (tableAnnotation == null) {
			return new HashSet<String>();
		}

		Set<String> columnNames = new HashSet<String>();
		UniqueConstraint[] uniqueConstraints = tableAnnotation.uniqueConstraints();
		for (UniqueConstraint uniqueConstraint : uniqueConstraints) {
			columnNames.addAll(Arrays.asList(uniqueConstraint.columnNames()));
		}

		return extractPropertiesFromDbColumnNames(columnNames, clazz);
	}

	private Set<String> extractPropertiesFromDbColumnNames(Set<String> columnNames, Class<?> clazz) {
		Set<String> properties = new HashSet<String>();

		for (Field field : clazz.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			if ((column != null && columnNames.contains(column.name())) || (joinColumn != null && columnNames.contains(joinColumn.name()))) {
				properties.add(field.getName());
			}
		}

		return properties;
	}

	private Set<String> processIdAnnotation(Class<?> clazz) {
		Set<String> properties = new HashSet<String>();

		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(Id.class) != null) {
				properties.add(field.getName());
			}
		}

		return properties;
	}
}
