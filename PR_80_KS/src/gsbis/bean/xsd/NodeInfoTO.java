/**
 * NodeInfoTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class NodeInfoTO  implements java.io.Serializable {
    private java.lang.String bitID;

    private java.lang.String busNo;

    private java.lang.String busStopName;

    private java.lang.String stationGap;

    public NodeInfoTO() {
    }

    public NodeInfoTO(
           java.lang.String bitID,
           java.lang.String busNo,
           java.lang.String busStopName,
           java.lang.String stationGap) {
           this.bitID = bitID;
           this.busNo = busNo;
           this.busStopName = busStopName;
           this.stationGap = stationGap;
    }


    /**
     * Gets the bitID value for this NodeInfoTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this NodeInfoTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the busNo value for this NodeInfoTO.
     * 
     * @return busNo
     */
    public java.lang.String getBusNo() {
        return busNo;
    }


    /**
     * Sets the busNo value for this NodeInfoTO.
     * 
     * @param busNo
     */
    public void setBusNo(java.lang.String busNo) {
        this.busNo = busNo;
    }


    /**
     * Gets the busStopName value for this NodeInfoTO.
     * 
     * @return busStopName
     */
    public java.lang.String getBusStopName() {
        return busStopName;
    }


    /**
     * Sets the busStopName value for this NodeInfoTO.
     * 
     * @param busStopName
     */
    public void setBusStopName(java.lang.String busStopName) {
        this.busStopName = busStopName;
    }


    /**
     * Gets the stationGap value for this NodeInfoTO.
     * 
     * @return stationGap
     */
    public java.lang.String getStationGap() {
        return stationGap;
    }


    /**
     * Sets the stationGap value for this NodeInfoTO.
     * 
     * @param stationGap
     */
    public void setStationGap(java.lang.String stationGap) {
        this.stationGap = stationGap;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NodeInfoTO)) return false;
        NodeInfoTO other = (NodeInfoTO) obj;
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
            ((this.busNo==null && other.getBusNo()==null) || 
             (this.busNo!=null &&
              this.busNo.equals(other.getBusNo()))) &&
            ((this.busStopName==null && other.getBusStopName()==null) || 
             (this.busStopName!=null &&
              this.busStopName.equals(other.getBusStopName()))) &&
            ((this.stationGap==null && other.getStationGap()==null) || 
             (this.stationGap!=null &&
              this.stationGap.equals(other.getStationGap())));
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
        if (getBusNo() != null) {
            _hashCode += getBusNo().hashCode();
        }
        if (getBusStopName() != null) {
            _hashCode += getBusStopName().hashCode();
        }
        if (getStationGap() != null) {
            _hashCode += getStationGap().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NodeInfoTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "NodeInfoTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "bitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "busNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busStopName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "busStopName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stationGap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "stationGap"));
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
