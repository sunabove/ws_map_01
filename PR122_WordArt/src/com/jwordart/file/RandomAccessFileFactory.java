package com.jwordart.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.*;

import com.jwordart.GisComLib;

public class RandomAccessFileFactory extends GisComLib {

	private RandomAccessFileFactory() {

	}

	public RandomAccessFile getRandomAccessFile_Local( File file ) throws Exception {

		String filePath = file.getCanonicalPath();

		RandomAccessFile raf = rafHash.get( filePath );

		if( raf == null ) {
			try {
				raf = new RandomAccessFile( file, "r" );
				rafHash.put( filePath , raf);
			} catch (FileNotFoundException e) {
				this.debug( "Cannot Open RandomAccess File : " + file );
				this.debug(e);
			}
		}

		return raf ;

	}

	public void closeFile( File file ) throws Exception {

		RandomAccessFile raf = this.getRandomAccessFile_Local( file );

		this.rafHash.remove( file );

		raf.close();

	}

	private Hashtable< String , RandomAccessFile > rafHash = new Hashtable< String , RandomAccessFile >();

	private static RandomAccessFileFactory rafFactory = new RandomAccessFileFactory();

	public static RandomAccessFileFactory getRandomAccessFileFactory() {
		return rafFactory;
	}

	public static RandomAccessFile getRandomAccessFile( File  file ) throws Exception {

		return getRandomAccessFileFactory().getRandomAccessFile_Local(file);
	}

	public static void closeRandoAccessFile( File file ) throws Exception {
		getRandomAccessFileFactory().closeFile( file );
	}

}
