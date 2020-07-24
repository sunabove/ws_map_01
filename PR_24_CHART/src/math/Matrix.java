// 이 프로젝트의 모든 소스와 실행파일은 대한민국 저작권법 및 국제법에 의해 보호됩니다.
//======================================================================================
//	PROJECT		: JCalc
//	CLASS		: FMinverse
//	DESCRIPT	: Mathematics Function
//	CREATE DATE	: 2001-11-08
//	AUTHOR		: kisik
//======================================================================================

package math;

public class Matrix {

	public static double [][] getInverseMatrix(double[][] array) {

		int size = array.length;

		if (size == 1) return new double [][] { { (1/array[0][0]) } };

		double[][] E = new double[size][size];   // 항등행렬..
		initE(E, size);

		for (int i=0; i < size; i++) {
			for (int j=0; j < size; j++) {
				if (i==j) {
					// i th row will be effected..
					makeOne(array, E, size, j, i);
				} else {
					// i th row will be effected..
					makeZero(array, E, size, j, i);
				}
			}
		}

		return E;

	}

	static void makeOne(double[][] array, double[][] E, int size, int row, int column) {
		double value = array[row][column];
		if (value == 1) return;
		for (int i=0; i < size; i++) {
			array[row][i] /= value;
			E[row][i] /= value;
		}

	}

//	static void makeZero(double[][] array, double[][] E, int size, int row, int column) {
//
//		double value = array[row][column];
//		if (value == 0) return;
//		double tmp = array[column][column];  // 0 일리가 없으므로..
//		value = - tmp/value;
//		for (int i=0; i < size; i++) {
//			array[row][i] += array[column][i]/value;
//			E[row][i] += E[column][i]/value;
//		}
//
//	}

//	static void makeZero(double[][] array, double[][] E, int size, int row, int column) {
//
//		double value = array[row][column];
//
//		if (value == 0) return;
//
//		double tmp = array[column][column];  // 0 일리가 없으므로..
//
//		for (int i=0; i < size; i++) {
//			array[row][i] -= array[column][i]*value/tmp;
//			E[row][i] -= E[column][i]*value/tmp;
//		}
//
//	}

	static void makeZero(double[][] array, double[][] E, int size, int row, int column) {

		double value = array[row][column];

		if (value == 0) return;

		double tmp = array[column][column];  // 0 일리가 없으므로..

		for (int i=0; i < size; i++) {
			array[row][i] -= ((array[column][i]/tmp)*value);
			E[row][i] -= ((E[column][i]/tmp)*value);
		}

	}

	static void initE(double[][] E, int size) {

		// size 만큼의 정방행렬을 항등행렬로 초기화...

		for (int i=0; i < size; i++) {
			for (int j=0; j < size; j++) {
				E[i][j] = ( i == j ) ? 1 : 0;
			}
		}

	}

	public static double [][] getMultipliedMatrix(double [][] AA, double [][] BB) {

	     int rowLen = AA.length;
	     int colLen = BB[0].length;

	     double [][] CC = new double[ rowLen ][ colLen ];

	     for( int r = 0; r < rowLen; r ++ ) {

	       for( int c = 0; c < colLen ; c ++ ) {

		    CC[ r ] [ c ] = getMatrixCellMultiplied( AA, BB, r, c );

	       }

	     }

	     return CC;

	}

	private static double getMatrixCellMultiplied( double [][] a, double [][] b,
	      int row, int col ) {

	     double [] aRow = a[ row ];

	     int n = aRow.length;

	     double cell = 0;

	     for( int i = 0; i < n; i ++ ) {

		 cell += ( aRow[i]*(b[i][col]) );

	     }

	     return cell;

	}

	public static double [][] getTransposeMatrix(double[][] array) {

	    int n = array.length;
	    int m = array[0].length;

	    double transpose [][] = new double[ m ][ n ];

	    for( int r = 0; r < n ; r ++ ) {

	      double row [] = array[ r ];

	      for( int k = 0; k < m; k ++ ) {

		transpose[ k ] [ r ] = row[ k ];

	      }

	    }

	    return transpose;

	}

}