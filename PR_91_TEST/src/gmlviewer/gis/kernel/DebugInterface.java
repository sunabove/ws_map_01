package gmlviewer.gis.kernel;

import java.io.*;
import gmlviewer.gis.util.ClassUtil;

public interface DebugInterface {

    public static __Debug debug = new __Debug();

    static class __Debug {

        public __Debug() {
            this.no = 0;
            this.out = System.out;
            this.err = System.err;
            //this.out = System.err;
        }

        private void print(PrintStream out, String msg) {
            if (this.DEBUG_FLAG) {
                out.println(msg);
            }
        }

        public void debug(Class klass, String msg) {
            print(out, this.getMessageHeader(klass, 0, no) + msg);
        }

        public void debug(Object obj, String msg) {
            print(out, this.getMessageHeader(obj, 0, no) + msg);
        }

        public void warn(Class klass, String msg) {
            print(err, this.getMessageHeader(klass, 0, no) + msg);
        }

        public void warn(Object obj, String msg) {
            print(err, this.getMessageHeader(obj, 0, no) + msg);
        }

        public void debug(Object obj, Exception e) {
            e.printStackTrace();
        }

        public void debug(Exception e) {
            e.printStackTrace();
        }

        public void debug(Error e) {
            e.printStackTrace();
        }

        public StringBuffer getMessageHeader(Object obj, int processDepth,
                                             int msgNo) {

            Class klass = (obj instanceof Class) ? (Class) obj : obj.getClass();

            StringBuffer bfr = new StringBuffer();

            bfr.append( this.DEBUG_HEAD ) ;

            /*
             for (int i = 0, len = processDepth + 1; i < len; i++) {
                bfr.append("*");
                         }
             */

            if (this.MSG_NO_FLAG) {
                bfr.append(msgNo);
            }

            bfr.append(" [ " + ClassUtil.getSimplifiedName(klass) + " ] ");

            //bfr.append( "\r\n" );

            return bfr;

        }

        private PrintStream out;
        private PrintStream err;

        private static boolean MSG_NO_FLAG = false;

        private static int no;

        private static final String DEBUG_HEAD = "--------------";

        private static boolean DEBUG_FLAG = true ;

    }

}
