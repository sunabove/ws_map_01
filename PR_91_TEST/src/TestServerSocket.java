import java.net.*;
import java.io.*;

public class TestServerSocket {

	public TestServerSocket() {
		int i = 0;
		int j = 0;
	}

	public void start() throws Exception {
		ServerSocket ss = new ServerSocket( 8080 );

		Socket s ;
		OutputStream o;

		while( ( s = ss.accept() ) != null ) {
			o = s.getOutputStream();
			o.write( "Hello".getBytes() );
			o.flush();
		}
	}

	public static void main( String args [] ) throws Exception {
		System.out.println( "Hello...."	);
		new TestServerSocket().start();
		System.out.println( "Good bye...."	);
	}

}
