/**
 * ZigBeeTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class ZigBeeTO  implements java.io.Serializable {
    private java.lang.String bitID;

    private java.lang.String carTermID;

    private java.lang.String rssi;

    public ZigBeeTO() {
    }

    public ZigBeeTO(
           java.lang.String bitID,
           java.lang.String carTermID,
           java.lang.String rssi) {
           this.bitID = bitID;
           this.carTermID = carTermID;
           this.rssi = rssi;
    }


    /**
     * Gets the bitID value for this ZigBeeTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this ZigBeeTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the carTermID value for this ZigBeeTO.
     * 
     * @return carTermID
     */
    public java.lang.String getCarTermID() {
        return carTermID;
    }


    /**
     * Sets the carTermID value for this ZigBeeTO.
     * 
     * @param carTermID
     */
    public void setCarTermID(java.lang.String carTermID) {
        this.carTermID = carTermID;
    }


    /**
     * Gets the rssi value for this ZigBeeTO.
     * 
     * @return rssi
     */
    public java.lang.String getRssi() {
        return rssi;
    }


    /**
     * Sets the rssi value for this ZigBeeTO.
     * 
     * @param rssi
     */
    public void setRssi(java.lang.String rssi) {
        this.rssi = rssi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZigBeeTO)) return false;
        ZigBeeTO other = (ZigBeeTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bitID==null && other.getBitID()==null) || 
             (this.bitID!=null &&
              this.bitID.equals(other.getBitID()))) &&
            ((this.carTermID==null && other.getCarTermID()==null) || 
             (this.carTermID!=null &&
              this.carTermID.equals(other.getCarTermID()))) &&
            ((this.rssi==null && other.getRssi()==null) || 
             (this.rssi!=null &&
              this.rssi.equals(other.getRssi())));
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
        if (getBitID() != null) {
            _hashCode += getBitID().hashCode();
        }
        if (getCarTermID() != null) {
            _hashCode += getCarTermID().hashCode();
        }
        if (getRssi() != null) {
            _hashCode += getRssi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZigBeeTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "ZigBeeTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "bitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carTermID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "carTermID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rssi");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "rssi"));
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
