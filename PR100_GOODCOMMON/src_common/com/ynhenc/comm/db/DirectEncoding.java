package com.ynhenc.comm.db;

public class DirectEncoding extends Encoding {

    public DirectEncoding(String destCharSet) {
        this.destCharSet = destCharSet;
    }

    /**
     * 데이터베이스에서 읽은 문자열을 변환한다.
     * @param text String
     * @return String
     */
    @Override
	public String fromDb(String text) {
        try {
            return new String(text.getBytes(this.destCharSet));
        } catch (Exception ex) {
            return text;
        }
    }

    /**
     * 데이터베이스에 입력한 문자열을 변환한다.
     * @param text String
     * @return String
     */
    @Override
	public String toDb(String text) {
        try {
            return new String(text.getBytes(), this.destCharSet);
        } catch (Exception ex) {
            return text;
        }
    }

    private String destCharSet;

}
