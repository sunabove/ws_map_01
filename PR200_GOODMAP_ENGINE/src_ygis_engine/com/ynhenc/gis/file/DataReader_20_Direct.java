package com.ynhenc.gis.file;

import java.io.*;
import java.nio.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.ui.comp.*;

public abstract class DataReader_20_Direct extends GisComLib {

	protected int readIntLittle() throws IOException {
		return (ra.read() ) + (ra.read() << 8) + (ra.read() << 16) + (ra.read() << 24);
	}

	protected int readIntBig() throws IOException {
		return (ra.read() << 24) + (ra.read() << 16) + (ra.read() << 8) + (ra.read());
	}

	protected long readLongLittle() throws IOException {

		this.readeBytesFully(8);

		return (((long) buff[7] << 56) + ((long) (buff[6] & 255) << 48) + ((long) (buff[5] & 255) << 40)
				+ ((long) (buff[4] & 255) << 32) + ((long) (buff[3] & 255) << 24) + ((buff[2] & 255) << 16)
				+ ((buff[1] & 255) << 8) + ((buff[0] & 255)));
	}

	protected double readDoubleLittle() throws IOException {

		return Double.longBitsToDouble(this.readLongLittle());

	}

	private void readeBytesFully(int n) throws IOException {
		this.ra.readFully( buff, 0, n );
	}

	public long getPosition() {
		try {
			return this.ra.getFilePointer();
		} catch (IOException e) {
			this.debug(e);
			return -1;
		}
	}

	public DataReader_20_Direct( RandomAccessFile src) {
		this(src, -1);
	}

	public DataReader_20_Direct(RandomAccessFile src, long length) {
		this.ra = src;
		this.length = length;
		this.buff = new byte[8];
	}

	protected long length;
	private RandomAccessFile ra;
	private byte[] buff;

}
