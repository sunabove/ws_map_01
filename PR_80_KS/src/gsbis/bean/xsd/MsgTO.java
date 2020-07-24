/**
 * MsgTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class MsgTO  implements java.io.Serializable {
    private java.lang.String appBgDate;

    private java.lang.String appEdDate;

    private java.lang.String bitID;

    private java.lang.String exprEdTime;

    private java.lang.String exprStTime;

    private java.lang.String msg;

    public MsgTO() {
    }

    public MsgTO(
           java.lang.String appBgDate,
           java.lang.String appEdDate,
           java.lang.String bitID,
           java.lang.String exprEdTime,
           java.lang.String exprStTime,
           java.lang.String msg) {
           this.appBgDate = appBgDate;
           this.appEdDate = appEdDate;
           this.bitID = bitID;
           this.exprEdTime = exprEdTime;
           this.exprStTime = exprStTime;
           this.msg = msg;
    }


    /**
     * Gets the appBgDate value for this MsgTO.
     * 
     * @return appBgDate
     */
    public java.lang.String getAppBgDate() {
        return appBgDate;
    }


    /**
     * Sets the appBgDate value for this MsgTO.
     * 
     * @param appBgDate
     */
    public void setAppBgDate(java.lang.String appBgDate) {
        this.appBgDate = appBgDate;
    }


    /**
     * Gets the appEdDate value for this MsgTO.
     * 
     * @return appEdDate
     */
    public java.lang.String getAppEdDate() {
        return appEdDate;
    }


    /**
     * Sets the appEdDate value for this MsgTO.
     * 
     * @param appEdDate
     */
    public void setAppEdDate(java.lang.String appEdDate) {
        this.appEdDate = appEdDate;
    }


    /**
     * Gets the bitID value for this MsgTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this MsgTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the exprEdTime value for this MsgTO.
     * 
     * @return exprEdTime
     */
    public java.lang.String getExprEdTime() {
        return exprEdTime;
    }


    /**
     * Sets the exprEdTime value for this MsgTO.
     * 
     * @param exprEdTime
     */
    public void setExprEdTime(java.lang.String exprEdTime) {
        this.exprEdTime = exprEdTime;
    }


    /**
     * Gets the exprStTime value for this MsgTO.
     * 
     * @return exprStTime
     */
    public java.lang.String getExprStTime() {
        return exprStTime;
    }


    /**
     * Sets the exprStTime value for this MsgTO.
     * 
     * @param exprStTime
     */
    public void setExprStTime(java.lang.String exprStTime) {
        this.exprStTime = exprStTime;
    }


    /**
     * Gets the msg value for this MsgTO.
     * 
     * @return msg
     */
    public java.lang.String getMsg() {
        return msg;
    }


    /**
     * Sets the msg value for this MsgTO.
     * 
     * @param msg
     */
    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MsgTO)) return false;
        MsgTO other = (MsgTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appBgDate==null && other.getAppBgDate()==null) || 
             (this.appBgDate!=null &&
              this.appBgDate.equals(other.getAppBgDate()))) &&
            ((this.appEdDate==null && other.getAppEdDate()==null) || 
             (this.appEdDate!=null &&
              this.appEdDate.equals(other.getAppEdDate()))) &&
            ((this.bitID==null && other.getBitID()==null) || 
             (this.bitID!=null &&
              this.bitID.equals(other.getBitID()))) &&
            ((this.exprEdTime==null && other.getExprEdTime()==null) || 
             (this.exprEdTime!=null &&
              this.exprEdTime.equals(other.getExprEdTime()))) &&
            ((this.exprStTime==null && other.getExprStTime()==null) || 
             (this.exprStTime!=null &&
              this.exprStTime.equals(other.getExprStTime()))) &&
            ((this.msg==null && other.getMsg()==null) || 
             (this.msg!=null &&
              this.msg.equals(other.getMsg())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAppBgDate() != null) {
            _hashCode += getAppBgDate().hashCode();
        }
        if (getAppEdDate() != null) {
            _hashCode += getAppEdDate().hashCode();
        }
        if (getBitID() != null) {
            _hashCode += getBitID().hashCode();
        }
        if (getExprEdTime() != null) {
            _hashCode += getExprEdTime().hashCode();
        }
        if (getExprStTime() != null) {
            _hashCode += getExprStTime().hashCode();
        }
        if (getMsg() != null) {
            _hashCode += getMsg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MsgTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "MsgTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appBgDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "appBgDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appEdDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "appEdDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "bitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exprEdTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "exprEdTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exprStTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "exprStTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "msg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
