package com.jwordart.util;

public class ClassUtil {

	public static final boolean isAnonymousClass(Class klass) {

		return (klass.getName().indexOf('$') > -1);

	}

	public static final String getSimplifiedName(Class klass) {

		String name = ClassUtil.getClassName(klass);

		int dotIdx = name.lastIndexOf('.');

		if (dotIdx < 0) {
			return name;
		} else {
			return name.substring(dotIdx + 1);
		}

	}

	public static final String getClassName(Class klass) {
		return klass.getName();
	}

}
