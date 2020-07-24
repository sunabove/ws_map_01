/**
 * CommandTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class CommandTO  implements java.io.Serializable {
    private java.lang.String bitID;

    private java.lang.String ctlInstructCD;

    private java.lang.String serialNo;

    public CommandTO() {
    }

    public CommandTO(
           java.lang.String bitID,
           java.lang.String ctlInstructCD,
           java.lang.String serialNo) {
           this.bitID = bitID;
           this.ctlInstructCD = ctlInstructCD;
           this.serialNo = serialNo;
    }


    /**
     * Gets the bitID value for this CommandTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this CommandTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the ctlInstructCD value for this CommandTO.
     * 
     * @return ctlInstructCD
     */
    public java.lang.String getCtlInstructCD() {
        return ctlInstructCD;
    }


    /**
     * Sets the ctlInstructCD value for this CommandTO.
     * 
     * @param ctlInstructCD
     */
    public void setCtlInstructCD(java.lang.String ctlInstructCD) {
        this.ctlInstructCD = ctlInstructCD;
    }


    /**
     * Gets the serialNo value for this CommandTO.
     * 
     * @return serialNo
     */
    public java.lang.String getSerialNo() {
        return serialNo;
    }


    /**
     * Sets the serialNo value for this CommandTO.
     * 
     * @param serialNo
     */
    public void setSerialNo(java.lang.String serialNo) {
        this.serialNo = serialNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommandTO)) return false;
        CommandTO other = (CommandTO) obj;
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
            ((this.ctlInstructCD==null && other.getCtlInstructCD()==null) || 
             (this.ctlInstructCD!=null &&
              this.ctlInstructCD.equals(other.getCtlInstructCD()))) &&
            ((this.serialNo==null && other.getSerialNo()==null) || 
             (this.serialNo!=null &&
              this.serialNo.equals(other.getSerialNo())));
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
        if (getCtlInstructCD() != null) {
            _hashCode += getCtlInstructCD().hashCode();
        }
        if (getSerialNo() != null) {
            _hashCode += getSerialNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommandTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "CommandTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "bitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctlInstructCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "ctlInstructCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "serialNo"));
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
