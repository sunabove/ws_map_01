package gmlviewer.gis.sbmoon;

/**
 * 변수명과 변수값을 가지는 파라퍼티 클래스이다.
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
     * 생성자
     * @param key String
     * 변수명
     * @param value String
     * 변수값
     */

    public Property() {
      this( null, null );
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 변수명을 반환한다.
     * @return String
     * 변수명
     */
    public String getKey() {
        return key;
    }

    /**
     * 변수값을 반환한다.
     * @return String
     * 변수값
     */
    public String getValue() {
        return value;
    }

    /**
     * 변수명에 새로운 변수값을 설정한다.
     * @param value String
     * 설정한 변수값
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 텍스트 리터럴 함수이다.
     * @return String
     * 객체의 텍스트 리터럴
     */
    public String toString() {
        return key + " = " + value;
    }

    /**
     * 변수명
     */
    private String key;

    /**
     * 변수값
     */
    private String value;

}
