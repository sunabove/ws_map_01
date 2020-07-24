package com.ynhenc.comm.util;

import java.util.*;

public class TextUtil {

	public static String quote(String text) {

		return '\'' + text + '\'';

	}

	public static int countOf(String txt, char c) {

		int count = 0;

		char[] chars = txt.toCharArray();

		for (int i = 0, len = chars.length; i < len; i++) {

			if (chars[i] == c) {
				count += 1;
			}

		}

		return count;

	}

	public static String[] parseString(String text, String delim) {

		LinkedList tknList = new LinkedList();

		for (;;) {

			int i = text.indexOf(delim);

			if (i < 0) {

				if (text.length() > 0) {

					tknList.add(text);

				}

				break;

			}

			tknList.add(text.substring(0, i));

			if (i < text.length() - 1) {

				text = text.substring(i + delim.length());

			} else {

				break;

			}

		}

		Iterator it = tknList.iterator();

		int len = tknList.size();

		if (len < 1) {

			return null;

		}

		String[] tokens = new String[len];

		for (int i = 0; i < len; i++) {

			tokens[i] = (String) (it.next());

		}

		return tokens;

	}

}
