
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.ynhenc.comm.util;

public class CryptoGraph {

    public static byte [] encrypt(byte [] bytes) {
         byte [] eb = new byte[bytes.length]; // ecrypted bytes

         int N = eb.length;

         for(int i = 0; i < N; i ++ ) {
             eb[ i ] = (byte) ( - bytes[N - 1 - i] );
         }

         return eb;
    }

    public static byte [] decrypte( byte [] bytes ) {
        return encrypt( bytes );
    }

}