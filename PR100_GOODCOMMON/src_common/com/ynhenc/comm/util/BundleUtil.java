package com.ynhenc.comm.util;

import java.util.*;

public class BundleUtil {

	private BundleUtil() {
	}

	ResourceBundle getResourceBundle_Imple(String bundleFileName, boolean useCache) {
		if (useCache) {
			ResourceBundle bundle = this.list.get(bundleFileName);
			if (bundle == null) {
				bundle = ResourceBundle.getBundle(bundleFileName);
				this.list.put(bundleFileName, bundle);
			}
			return bundle;
		} else {
			return ResourceBundle.getBundle(bundleFileName);
		}
	}

	private Hashtable<String, ResourceBundle> list = new Hashtable<String, ResourceBundle>();

	private static BundleUtil getBundleUtil() {
		return bundleUtil;
	}

	public static ResourceBundle getBundle(String bundleFileName, boolean useCache ) {
		return getBundleUtil().getResourceBundle_Imple(bundleFileName, useCache );
	}

	private static final BundleUtil bundleUtil = new BundleUtil();

}
