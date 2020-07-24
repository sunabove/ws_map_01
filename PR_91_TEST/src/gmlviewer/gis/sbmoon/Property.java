package gmlviewer.gis.sbmoon;

/**
 * ������� �������� ������ �Ķ���Ƽ Ŭ�����̴�.
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Property {

    /**
     * ������
     * @param key String
     * ������
     * @param value String
     * ������
     */

    public Property() {
      this( null, null );
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * �������� ��ȯ�Ѵ�.
     * @return String
     * ������
     */
    public String getKey() {
        return key;
    }

    /**
     * �������� ��ȯ�Ѵ�.
     * @return String
     * ������
     */
    public String getValue() {
        return value;
    }

    /**
     * ������ ���ο� �������� �����Ѵ�.
     * @param value String
     * ������ ������
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * �ؽ�Ʈ ���ͷ� �Լ��̴�.
     * @return String
     * ��ü�� �ؽ�Ʈ ���ͷ�
     */
    public String toString() {
        return key + " = " + value;
    }

    /**
     * ������
     */
    private String key;

    /**
     * ������
     */
    private String value;

}
