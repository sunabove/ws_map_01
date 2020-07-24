import java.net.*;
import java.io.*;

public class TestTilde {

	public TestTilde() {
	}

	public void start() throws Exception {
		int i = 10;
		int j = ~ i;
		System.out.println( i + j );
	}

	public static void main( String args [] ) throws Exception {
		System.out.println( "Hello...."	);
		new TestTilde().start();
		System.out.println( "Good bye...."	);
	}

}
