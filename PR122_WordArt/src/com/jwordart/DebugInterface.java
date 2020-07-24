package com.jwordart;

import java.io.*;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import com.jwordart.util.ClassUtil;

public interface DebugInterface {

	public static Debug debug = new Debug("DBG" );

	static class Debug {

		public void println(Class klass, String msg) {
			this.printMessage( klass, msg );
		}

		public void println(Object obj, String msg) {
			this.printMessage( obj.getClass() , msg );
		}

		private void printMessage( Class klass, String msg ) {
			if( true ) {
				System.out.println( this.getMessageHeader( klass ) + msg );
				System.out.flush();
			} else {
				this.getLogger( klass ).info( msg );
			}
		}

		public void println(Object obj, Exception e) {
			e.printStackTrace();
		}

		public void println(Object obj, Error e) {
			e.printStackTrace();
		}

		public void println(Exception e) {
			e.printStackTrace();
		}

		public void println(Error e) {
			e.printStackTrace();
		}

		private Logger getLogger( Class klass ) {
			if( ! loggerInit ) {
				BasicConfigurator.configure();
			}

			Logger logger = loggerHash.get( klass );
			if( logger == null ) {
				logger = Logger.getLogger( klass );
				loggerHash.put( klass, logger);
			}

			return logger;
		}

		public String getMessageHeader( Class klass ) {

			String bfr = "" ;

			if( true ) {

				bfr += this.no ;

				this.no++;
			}

			bfr += "[" + ClassUtil.getClassName(klass) + "]" ;

			return bfr;

		}

		public Debug(String msgTitle ) {
			this.msgTitle = msgTitle;
			this.no = 0;
		}

		private String msgTitle = "";

		private int no;

		private boolean loggerInit = false;

		private Hashtable< Class, Logger > loggerHash = new Hashtable< Class, Logger >();

	}
}
