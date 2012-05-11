package uk.icat3.io.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import uk.icat3.entity.Datafile;
import uk.icat3.io.entity.ExtractedDataBean;
import uk.icat3.io.entity.FileId;

public class FileNameExtractor {

	private static final char PATH_SEPARATOR = '/';
	Set<FileId> files = new HashSet<FileId>();
	private Set<Object> processedEntities = new HashSet<Object>();
	
	private final List<?> objects;
	
	public FileNameExtractor(List<?> objects) {
		this.objects = objects;
	}
	
	Set<FileId> getFileNames() {
		for (Object o : objects) {
			processEntity(o);
		}
		return files;
	}

	private void processEntity(Object entity) {
		if (entity != null && isEntity(entity) && !processedEntities.contains(entity)) {
			processedEntities.add(entity);
			if (entity instanceof Datafile) {
				Datafile datafile = (Datafile) entity;
				String location = datafile.getLocation();

				files.add(new FileId(getFileName(location), getDirectory(location)));

			} else {
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field f : fields) {

					f.setAccessible(true);
					try {
						if (isOneToMany(f)) {
							Collection<?> collection = (Collection<?>) f.get(entity);
							for (Object derivedEntity : collection) {
								processEntity(derivedEntity);
							}
						}
						if (isManyToOne(f)) {
							processEntity(f.get(entity));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private String getDirectory(String location) {
		return location.substring(0, location.lastIndexOf(PATH_SEPARATOR));
	}

	private String getFileName(String location) {
		return location.substring(location.lastIndexOf('/')+1);
	}

	private boolean isManyToOne(Field f) {
		return f.getAnnotation(ManyToOne.class) != null;
	}

	private boolean isOneToMany(Field f) {
		return f.getAnnotation(OneToMany.class) != null;
	}

	private boolean isEntity(Object o) {
		return o.getClass().getAnnotation(Entity.class) != null;
	}

}
