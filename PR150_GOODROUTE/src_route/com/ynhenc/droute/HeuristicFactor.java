package com.ynhenc.droute;

public class HeuristicFactor {

	public double getFactor(SrchOption srchOption) {
		if( false ) {
			return 0;
		}
		int srchType = srchOption.getSrchType();
		if( srchType == 0 ) {
			return 4.00;
		}
		return factorList[ srchType ];
	}

	private HeuristicFactor() {
	}

	public static HeuristicFactor getHeuristicFactor() {
		return heuristicFactor;
	}

	private double[] factorList = { 1.99, 1, 1 };

	private static final HeuristicFactor heuristicFactor = new HeuristicFactor();

}
