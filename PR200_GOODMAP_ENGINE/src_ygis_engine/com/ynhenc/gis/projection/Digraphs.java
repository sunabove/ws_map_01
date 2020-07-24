package com.ynhenc.gis.projection;

import java.util.Hashtable;
import java.util.Map;

public class Digraphs {

	private Hashtable<Integer, String> digraph1 = new Hashtable<Integer, String> ();

	private Hashtable<Integer, String>  digraph2 = new Hashtable<Integer, String> ();

	private String[] digraph1Array = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S",
			"T", "U", "V", "W", "X", "Y", "Z" };

	private String[] digraph2Array = { "V", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R",
			"S", "T", "U", "V" };

	public Digraphs() {
		this.digraph1.put(new Integer(1), "A");
		this.digraph1.put(new Integer(2), "B");
		this.digraph1.put(new Integer(3), "C");
		this.digraph1.put(new Integer(4), "D");
		this.digraph1.put(new Integer(5), "E");
		this.digraph1.put(new Integer(6), "F");
		this.digraph1.put(new Integer(7), "G");
		this.digraph1.put(new Integer(8), "H");
		this.digraph1.put(new Integer(9), "J");
		this.digraph1.put(new Integer(10), "K");
		this.digraph1.put(new Integer(11), "L");
		this.digraph1.put(new Integer(12), "M");
		this.digraph1.put(new Integer(13), "N");
		this.digraph1.put(new Integer(14), "P");
		this.digraph1.put(new Integer(15), "Q");
		this.digraph1.put(new Integer(16), "R");
		this.digraph1.put(new Integer(17), "S");
		this.digraph1.put(new Integer(18), "T");
		this.digraph1.put(new Integer(19), "U");
		this.digraph1.put(new Integer(20), "V");
		this.digraph1.put(new Integer(21), "W");
		this.digraph1.put(new Integer(22), "X");
		this.digraph1.put(new Integer(23), "Y");
		this.digraph1.put(new Integer(24), "Z");

		this.digraph2.put(new Integer(0), "V");
		this.digraph2.put(new Integer(1), "A");
		this.digraph2.put(new Integer(2), "B");
		this.digraph2.put(new Integer(3), "C");
		this.digraph2.put(new Integer(4), "D");
		this.digraph2.put(new Integer(5), "E");
		this.digraph2.put(new Integer(6), "F");
		this.digraph2.put(new Integer(7), "G");
		this.digraph2.put(new Integer(8), "H");
		this.digraph2.put(new Integer(9), "J");
		this.digraph2.put(new Integer(10), "K");
		this.digraph2.put(new Integer(11), "L");
		this.digraph2.put(new Integer(12), "M");
		this.digraph2.put(new Integer(13), "N");
		this.digraph2.put(new Integer(14), "P");
		this.digraph2.put(new Integer(15), "Q");
		this.digraph2.put(new Integer(16), "R");
		this.digraph2.put(new Integer(17), "S");
		this.digraph2.put(new Integer(18), "T");
		this.digraph2.put(new Integer(19), "U");
		this.digraph2.put(new Integer(20), "V");

	}

	public int getDigraph1Index(String letter) {
		for (int i = 0; i < this.digraph1Array.length; i++) {
			if (this.digraph1Array[i].equals(letter)) {
				return i + 1;
			}
		}

		return -1;
	}

	public int getDigraph2Index(String letter) {
		for (int i = 0; i < this.digraph2Array.length; i++) {
			if (this.digraph2Array[i].equals(letter)) {
				return i;
			}
		}

		return -1;
	}

	public String getDigraph1(int longZone, double easting) {
		int a1 = longZone;
		double a2 = 8 * ((a1 - 1) % 3) + 1;

		double a3 = easting;
		double a4 = a2 + ((int) (a3 / 100000)) - 1;
		return this.digraph1.get(new Integer((int) Math.floor(a4)));
	}

	public String getDigraph2(int longZone, double northing) {
		int a1 = longZone;
		double a2 = 1 + 5 * ((a1 - 1) % 2);
		double a3 = northing;
		double a4 = (a2 + ((int) (a3 / 100000)));
		a4 = (a2 + ((int) (a3 / 100000.0))) % 20;
		a4 = Math.floor(a4);
		if (a4 < 0) {
			a4 = a4 + 19;
		}
		return this.digraph2.get(new Integer((int) Math.floor(a4)));

	}

}