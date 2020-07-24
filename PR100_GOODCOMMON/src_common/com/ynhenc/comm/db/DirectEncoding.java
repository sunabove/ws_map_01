package com.ynhenc.comm.db;

public class DirectEncoding extends Encoding {

    public DirectEncoding(String destCharSet) {
        this.destCharSet = destCharSet;
    }

    /**
     * �����ͺ��̽����� ���� ���ڿ��� ��ȯ�Ѵ�.
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
     * �����ͺ��̽��� �Է��� ���ڿ��� ��ȯ�Ѵ�.
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
