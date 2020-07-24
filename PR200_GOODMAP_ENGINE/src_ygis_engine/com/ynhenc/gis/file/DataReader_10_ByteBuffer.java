package com.ynhenc.gis.file;

import java.io.*;
import java.nio.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.RandomAccessFileFactory;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.ui.comp.*;

public abstract class DataReader_10_ByteBuffer extends GisComLib {

	protected int readIntLittle() throws Exception {

		ByteBuffer buff = this.getByteBuffer( 4 , ByteOrder.LITTLE_ENDIAN );

		return buff.getInt();
	}

	protected int readIntBig() throws Exception {
		ByteBuffer buff = this.getByteBuffer( 4 , ByteOrder.BIG_ENDIAN );

		return buff.getInt();
	}

	protected long readLongBig() throws Exception {

		ByteBuffer buff = this.getByteBuffer( 8 , ByteOrder.BIG_ENDIAN );

		return buff.getLong();

	}

	protected long readLongLittle() throws Exception {
		ByteBuffer buff = this.getByteBuffer( 8 , ByteOrder.LITTLE_ENDIAN );

		return buff.getLong();
	}

	protected double readDoubleLittle() throws Exception {

		ByteBuffer buff = this.getByteBuffer( 8 , ByteOrder.LITTLE_ENDIAN );

		return buff.getDouble();
	}

	public ByteBuffer getByteBuffer(int byteNo, ByteOrder byteOrder ) throws Exception {

		ByteBuffer buff = ByteBuffer.wrap( this.readBytes(byteNo) );
		buff.order( byteOrder );

		return buff;
	}

	protected byte[] readBytes(int available) throws Exception {

		byte[] bytes = new byte[available];

		this.getRandomAccessFile().readFully(bytes, 0, available );

		return bytes;
	}

	public void seek(long pos) throws Exception {
		this.getRandomAccessFile().seek(pos);
	}

	public long getPosition() {
		try {
			return this.getRandomAccessFile().getFilePointer();
		} catch (Exception e) {
			this.debug( e );
			return -1 ;
		}
	}

	public long getLength() {
		try {
			return this.getRandomAccessFile().length();
		} catch (Exception e) {
			this.debug( e );
			return -1 ;
		}
	}

	public RandomAccessFile getRandomAccessFile() throws Exception {
		if( this.ra == null ) {
			File file = this.getFile();
			this.ra = RandomAccessFileFactory.getRandomAccessFile(file);
		}
		return this.ra ;
	}

	public void close() throws Exception {
		File file = this.getFile();
		if( file != null ) {
			RandomAccessFileFactory.closeRandoAccessFile( file );
		}
	}

	public DataReader_10_ByteBuffer( File file ) throws Exception {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}

	// member
	private File file;
	private RandomAccessFile ra ;

}
