package com.ynhenc.gis.projection;

public class LatZoneList {
	private char[] letters = { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Z' };

	private int[] degrees = { -90, -84, -72, -64, -56, -48, -40, -32, -24, -16, -8, 0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 84 };

	private char[] negLetters = { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M' };

	private int[] negDegrees = { -90, -84, -72, -64, -56, -48, -40, -32, -24, -16, -8 };

	private char[] posLetters = { 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Z' };

	private int[] posDegrees = { 0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 84 };

	private int arrayLength = 22;

	public LatZoneList() {
	}

	public int getLatZoneDegree(String letter) {
		char ltr = letter.charAt(0);
		for (int i = 0; i < this.arrayLength; i++) {
			if (this.letters[i] == ltr) {
				return this.degrees[i];
			}
		}
		return -100;
	}

	public String getLatZone( Wgs wgs ) {
		int latIndex = -2;
		int lat = (int) wgs.getY() ;

		if (lat >= 0) {
			int len = this.posLetters.length;
			for (int i = 0; i < len; i++) {
				if (lat == this.posDegrees[i]) {
					latIndex = i;
					break;
				}

				if (lat > this.posDegrees[i]) {
					continue;
				} else {
					latIndex = i - 1;
					break;
				}
			}
		} else {
			int len = this.negLetters.length;
			for (int i = 0; i < len; i++) {
				if (lat == this.negDegrees[i]) {
					latIndex = i;
					break;
				}

				if (lat < this.negDegrees[i]) {
					latIndex = i - 1;
					break;
				} else {
					continue;
				}

			}

		}

		if (latIndex == -1) {
			latIndex = 0;
		}
		if (lat >= 0) {
			if (latIndex == -2) {
				latIndex = this.posLetters.length - 1;
			}
			return String.valueOf(this.posLetters[latIndex]);
		} else {
			if (latIndex == -2) {
				latIndex = this.negLetters.length - 1;
			}
			return String.valueOf(this.negLetters[latIndex]);

		}
	}

}