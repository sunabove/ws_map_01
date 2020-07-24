package com.ynhenc.comm.util;

import java.io.*;

import com.ynhenc.comm.GisComLib;

public class InputStreamUtil extends GisComLib {

	private void readeBytesFully(byte[] b, int n) throws IOException {
		int offset = 0;
		do {
			offset += this.in.read(b, offset, n - offset);
		} while (offset < n);

		this.position += offset;
	}

	protected byte[] readBytes(int available) throws IOException {
		byte[] bytes = new byte[available];
		int readNum = 0;
		int n;
		try {
			for (; readNum < available;) {
				n = in.read(bytes, readNum, available - readNum);
				readNum += n;
			}
			this.position += readNum;
		} catch (IndexOutOfBoundsException e) {
			this.debug(e);
		}
		return bytes;
	}

	public InputStreamUtil(InputStream in) {
		super();
		this.in = in;
	}

	private InputStream in;
	private long position;

}
