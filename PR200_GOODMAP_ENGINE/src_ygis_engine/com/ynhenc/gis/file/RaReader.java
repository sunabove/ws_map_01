package com.ynhenc.gis.file;

import java.io.*;
import java.nio.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.RandomAccessFileFactory;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.ui.comp.*;

public abstract class RaReader extends GisComLib {

	public abstract int readIntLittle() throws Exception ;

	public abstract int readIntBig() throws Exception ;

	//public abstract long readLongBig() throws Exception ;

	public abstract long readLongLittle() throws Exception  ;

	public abstract double readDoubleLittle() throws Exception  ;

	public final void readeBytesFully(int n) throws IOException {
		this.raf.readFully( buff, 0, n );
	}

	public final void skipBytes( long skipByteNo ) throws Exception {
		this.raf.seek( this.getPosition() + skipByteNo );
	}

	public final void seek(long pos) throws Exception {
		this.raf.seek(pos);
	}

	public final long getPosition() {
		try {
			return this.raf.getFilePointer();
		} catch (Exception e) {
			this.debug( e );
			return -1 ;
		}
	}

	public final long getLength() {
		try {
			return this.raf.length();
		} catch (Exception e) {
			this.debug( e );
			return -1 ;
		}
	}

	public final void close() throws Exception {
		File file = this.getFile();
		if( file != null ) {
			RandomAccessFileFactory.closeRandoAccessFile( file );
		}
	}

	public File getFile() {
		return this.file;
	}

	public RaReader( File file ) throws Exception {
		this.file = file;
		this.raf = RandomAccessFileFactory.getRandomAccessFile(file);
		this.buff = new byte[8];
	}

	// member
	private File file;
	protected RandomAccessFile raf ;

	final protected byte[] buff;

}
