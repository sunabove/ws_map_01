package com.ynhenc.kml;

import java.io.Writer;

import com.ynhenc.comm.ArrayListEx;
import com.ynhenc.comm.GisComLib;
import com.ynhenc.droute.render.*;

public abstract class KmlObject extends GisComLib {

	public void writeTag(Writer buff) throws Exception {

		this.openTag(buff);

		this.bodyTag(buff);

		this.closeTag(buff);

	}

	public void openTag(Writer buff) throws Exception {

		String tag = this.getTag();

		if (this.isGood(tag)) {
			buff.append("<" + tag);
			String attrData = this.getAttrData();
			if (this.isGood(attrData)) {
				buff.append(" " + attrData + " ");
			}
			buff.append(">");
		}

		String name = this.getName();

		if (this.isGood(name)) {
			KmlName nameTag = new KmlName();
			nameTag.openTag(buff);
			buff.write(name);
			nameTag.closeTag(buff);
		}

		String desc = this.getDesc();

		if (this.isGood(desc)) {
			KmlDescription descTag = new KmlDescription();
			descTag.openTag(buff);
			buff.write(desc);
			descTag.closeTag(buff);
		}

	}

	public final void bodyTag(Writer buff) throws Exception {

		if (this instanceof KmlDocument) {
			KmlDocument doc = (KmlDocument) this;
			KmlStyleContainer styleList = doc.getStyleList();
			if (styleList != null) {
				styleList.writeTag(buff);
			}
		}

		if (this instanceof KmlContainer) {
			KmlContainer container = (KmlContainer) this;
			KmlObject kmlObj;

			for (Object type : container.getCompList()) {
				kmlObj = (KmlObject) type;

				if( this instanceof KmlPlacemark ) {
					int i = 0;
					i ++ ;
				}

				kmlObj.writeTag(buff);
			}
		}

		if (this instanceof KmlFolder) {
			KmlFolder folder = (KmlFolder) this;

			KmlPlacemarkIterator it = folder.getPlacemarkIterator();

			KmlPlacemark placemark;
			while (it.hasNext()) {
				placemark = it.next();
				placemark.writeTag(buff);
			}
		}
	}

	public final void closeTag(Writer buff) throws Exception {
		// write tag close
		String tag = this.getTag();
		if (this.isGood(tag)) {
			buff.append("</" + tag + ">" + NL);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAttrData() {
		return attrData;
	}

	public void setAttrData(String attrData) {
		this.attrData = attrData;
	}

	public KmlObject(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}

	public abstract String getTag();

	private String name;
	private String desc;
	private String attrData;

}
