package com.ynhenc.comm.db;

import java.io.*;

public class DbfEncoding extends DirectEncoding {

    private DbfEncoding() {
        super( dbfFileEncoding );
    }

    private static final String dbfFileEncoding = "8859_1" ;

    public static DbfEncoding dbfEncoding = new DbfEncoding();

}
