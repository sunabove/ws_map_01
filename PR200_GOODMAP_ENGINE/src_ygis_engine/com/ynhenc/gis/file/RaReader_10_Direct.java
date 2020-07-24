package com.ynhenc.gis.file;

import java.io.File;
import java.io.IOException;

public class RaReader_10_Direct extends RaReader {

	@Override
	public int readIntLittle() throws IOException {
		return (raf.read() ) + (raf.read() << 8) + (raf.read() << 16) + (raf.read() << 24);
	}

	@Override
	public int readIntBig() throws IOException {
		return (raf.read() << 24) + (raf.read() << 16) + (raf.read() << 8) + (raf.read());
	}

	@Override
	public long readLongLittle() throws IOException {
		this.readeBytesFully(8);
		return (((long) buff[7] << 56) + ((long) (buff[6] & 255) << 48) + ((long) (buff[5] & 255) << 40)
				+ ((long) (buff[4] & 255) << 32) + ((long) (buff[3] & 255) << 24) + ((buff[2] & 255) << 16)
				+ ((buff[1] & 255) << 8) + ((buff[0] & 255)));
	}

	@Override
	public double readDoubleLittle() throws IOException {
		return Double.longBitsToDouble(this.readLongLittle());

	}

	public RaReader_10_Direct(File file) throws Exception {
		super(file);
	}

}
