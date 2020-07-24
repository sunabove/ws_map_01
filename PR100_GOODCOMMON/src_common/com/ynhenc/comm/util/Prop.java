package com.ynhenc.comm.util;

import java.util.*;
import java.io.*;

import com.ynhenc.comm.DebugInterface;


public class Prop extends Properties implements DebugInterface, Comparator {

    public Prop(File file) {
        super();
        this.file = file;
        this.init(file);
    }

    public boolean isModified() {
        File file = this.file;
        if( file != null ) {
            long before = this.lastModified;
            long now = file.lastModified();
            //debug.debug( this, "BEFORE = " + before );
            //debug.debug( this, "NOW = " + now );
            return before != now;
        } else {
            return false;
        }
    }

    @Override
	public Object put(Object key, Object value) {
        if (key == null) {
            throw new NullPointerException();
        }
        return super.put( this.convertKey("" + key), "" + value);
    }

    private String convertKey( String key ) {
        return key.toUpperCase();
    }

    private String getParameter( String key ) {
        return (String) ( this.get( key ) );
    }

    @Override
	public String getProperty( String key ) {
        return (String) ( this.get( key ) );
    }

    public Object get(String key) {

        key = this.convertKey( key );

        Object obj = super.get(key);

        if ( obj == null ) {
            obj = this.getStarObj(key);
        }

        if( obj != null ) {
            String text = (String) obj;
            text = text.replaceAll( "\"", "" );
            text = text.trim();
            return text;
        } else {
            //debug.debug( this, "ENCOUNTERED NULL VALUE OF " + key );
            return null;
        }

    }

    private Object getStarObj(String key) {

        Object obj = super.get(key + "*");

        if (obj != null) {
            return obj;
        } else {
            int keyLen = key.length();
            if (keyLen > 1) {
                return this.getStarObj(key.substring(0, keyLen - 1));
            } else {
                return null;
            }
        }

    }

    public boolean reloadIfModified() {
        if( this.isModified() ) {
            this.init( this.file );
            return true;
        } else {
            return false;
        }
    }

    @Override
	public synchronized void load(InputStream inStream) throws IOException {
        File file = this.file;
        if( file != null ) {
            this.lastModified = file.lastModified();
        }
        //remove all at first
        Enumeration it = this.propertyNames();
        while( it.hasMoreElements() ) {
          this.remove( it.nextElement() ) ;
        }
        // end of removal all at first

        this.loadFromStream( inStream );
    }

    private void init(File file) {

        Prop p = this;

        try {
            if (file.exists()) {
                InputStream in = new FileInputStream(file);
                p.load(in);
                this.lastModified = file.lastModified();
            } else {
                //saveFile();
            }
        } catch (IOException ex) {
            debug.println(this, ex);
        }

    }

    public synchronized void saveFile() {

        try {
            File file = this.file;
            debug.println( this, "Saving a Prop File " + file.getAbsolutePath() );
            if (file != null) {
                Prop p = this;
                PrintStream out = new PrintStream(new FileOutputStream(file));
                p.store(out);
                out.close();
            }
        } catch (IOException ex) {
            debug.println(this, ex);
        }

    }

    public synchronized void store(OutputStream out) throws
            IOException {
        BufferedWriter awriter;
        awriter = new BufferedWriter(new OutputStreamWriter(out));

        Enumeration e = this.keys();
        Vector keyList = new Vector();
        while (e.hasMoreElements()) {
            keyList.add(e.nextElement());
        }
        Object[] keys = keyList.toArray();

        Arrays.sort(keys, this);

        for (int i = 0, len = keys.length; i < len; i++) {
            String key = (String) keys[i];
            String val = (String) this.get(key);

            writeln(awriter, key + "=" + val);
        }
        awriter.flush();
    }

    public int compare(Object a, Object b) {
        String at = (String) a, bt = (String) b;
        return at.compareTo(bt);
    }

    private static void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    public Integer getInteger(String name) {

        try {
            return Integer.valueOf(this.getParameter(name));
        } catch (NumberFormatException ex) {
            return null;
        }

    }

    public Integer getInteger(String name, Integer def) {

        Integer i = this.getInteger(name);
        if (i == null) {
            return def;
        } else {
            return i;
        }

    }

    public double getDouble(String name, double def) {

        try {
            return Double.valueOf(this.getParameter(name)).doubleValue() ;
        } catch (NumberFormatException ex) {
            return def;
        }

    }


    public Integer getInteger(String name, int def) {

        return this.getInteger(name, new Integer(def));

    }

    public int getInt(String name, int def) {
        Integer i = this.getInteger(name);
        if (i == null) {
            return def;
        } else {
            return i.intValue();
        }
    }

    public Double getDouble(String name) {

        try {
            return Double.valueOf(this.getParameter(name));
        } catch (NumberFormatException ex) {
            return null;
        }

    }

    public String getString( String name ) {
        return this.getParameter( name );
    }

    public Boolean getBoolean(String name) {

        try {
            return new Boolean(this.getParameter(name));
        } catch (Exception e) {
            return null;
        }

    }

    // member
    private File file;
    private long lastModified;
    // end of member


    // code from source

    private static final String keyValueSeparators = "=: \t\r\n\f";

    private static final String strictKeyValueSeparators = "=:";

    private static final String specialSaveChars = "=: \t\r\n\f#!";

    private static final String whiteSpaceChars = " \t\r\n\f";


    public synchronized void loadFromStream(InputStream inStream) throws IOException {

        String charset = "MS949";

        BufferedReader in = new BufferedReader(new InputStreamReader( inStream  , charset ) );
        while (true) {
            // Get next line
            String line = in.readLine();
            if (line == null) {
				return;
			}

            if (line.length() > 0) {

                // Find start of key
                int len = line.length();
                int keyStart;
                for (keyStart=0; keyStart<len; keyStart++) {
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1) {
						break;
					}
				}

                // Blank lines are ignored
                if (keyStart == len) {
					continue;
				}

                // Continue lines that end in slashes if they are not comments
                char firstChar = line.charAt(keyStart);
                if ((firstChar != '#') && (firstChar != '!')) {
                    while (this.continueLine(line)) {
                        String nextLine = in.readLine();
                        if (nextLine == null) {
							nextLine = "";
						}
                        String loppedLine = line.substring(0, len-1);
                        // Advance beyond whitespace on new line
                        int startIndex;
                        for (startIndex=0; startIndex<nextLine.length(); startIndex++) {
							if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1) {
								break;
							}
						}
                        nextLine = nextLine.substring(startIndex,nextLine.length());
                        line = new String(loppedLine+nextLine);
                        len = line.length();
                    }

                    // Find separation between key and value
                    int separatorIndex;
                    for (separatorIndex=keyStart; separatorIndex<len; separatorIndex++) {
                        char currentChar = line.charAt(separatorIndex);
                        if (currentChar == '\\') {
							separatorIndex++;
						} else if (keyValueSeparators.indexOf(currentChar) != -1) {
							break;
						}
                    }

                    // Skip over whitespace after key if any
                    int valueIndex;
                    for (valueIndex=separatorIndex; valueIndex<len; valueIndex++) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1) {
							break;
						}
					}

                    // Skip over one non whitespace key value separators if any
                    if (valueIndex < len) {
						if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1) {
							valueIndex++;
						}
					}

                    // Skip over white space after other separators if any
                    while (valueIndex < len) {
                        if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1) {
							break;
						}
                        valueIndex++;
                    }
                    String key = line.substring(keyStart, separatorIndex);
                    String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

                    // Convert then store key and value
                    key = this.loadConvert(key);
                    value = this.loadConvert(value);
                    this.put(key, value);
                }
            }
        }
    }

    private String loadConvert(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x=0; x<len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value=0;
                    for (int i=0; i<4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                          case '0': case '1': case '2': case '3': case '4':
                          case '5': case '6': case '7': case '8': case '9':
                             value = (value << 4) + aChar - '0';
                             break;
                          case 'a': case 'b': case 'c':
                          case 'd': case 'e': case 'f':
                             value = (value << 4) + 10 + aChar - 'a';
                             break;
                          case 'A': case 'B': case 'C':
                          case 'D': case 'E': case 'F':
                             value = (value << 4) + 10 + aChar - 'A';
                             break;
                          default:
                              throw new IllegalArgumentException(
                                           "Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char)value);
                } else {
                    if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
                    outBuffer.append(aChar);
                }
            } else {
				outBuffer.append(aChar);
			}
        }
        return outBuffer.toString();
    }


    private boolean continueLine(String line) {
        int slashCount = 0;
        int index = line.length() - 1;
        while ((index >= 0) && (line.charAt(index--) == '\\')) {
			slashCount++;
		}
        return (slashCount % 2 == 1);
    }

    // end of code from source


}
