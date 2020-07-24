package com.ynhenc.comm.util;

public class ArrayUtil {

	/**
	 * 어레이 중에 오브젝트와 컨텐트가 일치하는 객체의 인덱스를 반환한다.
	 *
	 * @param array
	 * @param obj
	 * @return
	 */
	public static int indexOfWithEquals(Object[] array, Object obj) {

		for (int i = 0, len = array.length; i < len; i++) {

			if (array[i] == obj || array[i].equals(obj)) {
				return i;
			}

		}

		return -1;

	}

	public static ColRow getArrayDim(Object[][] objs) {

		int col = 0;

		Object[] rowList;

		int c;
		for (int i = 0, len = objs.length; i < len; i++) {
			rowList = objs[i];
			c = (rowList == null) ? 0 : rowList.length;
			col = (c > col) ? c : col;
		}

		return new ColRow(col, objs.length);

	}

}
