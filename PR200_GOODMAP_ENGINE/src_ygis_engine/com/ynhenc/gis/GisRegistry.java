package com.ynhenc.gis;

import java.awt.*;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.io.*;

import com.ynhenc.comm.*;
import com.ynhenc.comm.registry.ArrayListRegiItem;
import com.ynhenc.comm.registry.WindowRegistryUtil;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.ui.comp.*;

public class GisRegistry extends GisComLib {

	public Mbr getMbrGlobalDefault() {
		return this.DEFAULT_GLOBAL_MBR;
	}

	public String getAppFullName() {
		return this.APP_NAME + " build #" + this.BUILD_NUM;
	}

	public ArrayListRegiItem getWindowRegistry_GisProjectFilePathList() {
		return WindowRegistryUtil.getWindowRegistryUtil().getWindowRegistryValues("gisProject");
	}

	public void writeRegistry_GisProjectFilePath(File file) throws Exception {
		WindowRegistryUtil.getWindowRegistryUtil().writeWindowRegistry( "gisProject", file.getCanonicalPath() );
	}

	private Mbr DEFAULT_GLOBAL_MBR = new Mbr(0, 0, 1, 1);

	private String APP_NAME = "GoodMap¢â Editor V1.2.3";

	private String BUILD_NUM = "094";

	private String COPY_RIGHT = "(C) 2008-2009. YNHENC Co., Ltd.";
	private String ALL_RIGHTS_RESERVED = "All Rights Reserved.";

	public static GisRegistry getGisRegistry() {
		if (gisRegistry == null) {
			gisRegistry = new GisRegistry();
		}

		return gisRegistry;
	}

	private static GisRegistry gisRegistry;

}
