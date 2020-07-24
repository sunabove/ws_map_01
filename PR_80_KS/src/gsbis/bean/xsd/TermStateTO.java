/**
 * TermStateTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class TermStateTO  implements java.io.Serializable {
    private java.lang.String bitID;

    private java.lang.String coolerStatYN;

    private java.lang.String doorOpenCnt;

    private java.lang.String doorStatYN;

    private java.lang.String humidity;

    private java.lang.String lcdSenceStatYN;

    private java.lang.String ledVoltValue;

    private java.lang.String temperature;

    public TermStateTO() {
    }

    public TermStateTO(
           java.lang.String bitID,
           java.lang.String coolerStatYN,
           java.lang.String doorOpenCnt,
           java.lang.String doorStatYN,
           java.lang.String humidity,
           java.lang.String lcdSenceStatYN,
           java.lang.String ledVoltValue,
           java.lang.String temperature) {
           this.bitID = bitID;
           this.coolerStatYN = coolerStatYN;
           this.doorOpenCnt = doorOpenCnt;
           this.doorStatYN = doorStatYN;
           this.humidity = humidity;
           this.lcdSenceStatYN = lcdSenceStatYN;
           this.ledVoltValue = ledVoltValue;
           this.temperature = temperature;
    }


    /**
     * Gets the bitID value for this TermStateTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this TermStateTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the coolerStatYN value for this TermStateTO.
     * 
     * @return coolerStatYN
     */
    public java.lang.String getCoolerStatYN() {
        return coolerStatYN;
    }


    /**
     * Sets the coolerStatYN value for this TermStateTO.
     * 
     * @param coolerStatYN
     */
    public void setCoolerStatYN(java.lang.String coolerStatYN) {
        this.coolerStatYN = coolerStatYN;
    }


    /**
     * Gets the doorOpenCnt value for this TermStateTO.
     * 
     * @return doorOpenCnt
     */
    public java.lang.String getDoorOpenCnt() {
        return doorOpenCnt;
    }


    /**
     * Sets the doorOpenCnt value for this TermStateTO.
     * 
     * @param doorOpenCnt
     */
    public void setDoorOpenCnt(java.lang.String doorOpenCnt) {
        this.doorOpenCnt = doorOpenCnt;
    }


    /**
     * Gets the doorStatYN value for this TermStateTO.
     * 
     * @return doorStatYN
     */
    public java.lang.String getDoorStatYN() {
        return doorStatYN;
    }


    /**
     * Sets the doorStatYN value for this TermStateTO.
     * 
     * @param doorStatYN
     */
    public void setDoorStatYN(java.lang.String doorStatYN) {
        this.doorStatYN = doorStatYN;
    }


    /**
     * Gets the humidity value for this TermStateTO.
     * 
     * @return humidity
     */
    public java.lang.String getHumidity() {
        return humidity;
    }


    /**
     * Sets the humidity value for this TermStateTO.
     * 
     * @param humidity
     */
    public void setHumidity(java.lang.String humidity) {
        this.humidity = humidity;
    }


    /**
     * Gets the lcdSenceStatYN value for this TermStateTO.
     * 
     * @return lcdSenceStatYN
     */
    public java.lang.String getLcdSenceStatYN() {
        return lcdSenceStatYN;
    }


    /**
     * Sets the lcdSenceStatYN value for this TermStateTO.
     * 
     * @param lcdSenceStatYN
     */
    public void setLcdSenceStatYN(java.lang.String lcdSenceStatYN) {
        this.lcdSenceStatYN = lcdSenceStatYN;
    }


    /**
     * Gets the ledVoltValue value for this TermStateTO.
     * 
     * @return ledVoltValue
     */
    public java.lang.String getLedVoltValue() {
        return ledVoltValue;
    }


    /**
     * Sets the ledVoltValue value for this TermStateTO.
     * 
     * @param ledVoltValue
     */
    public void setLedVoltValue(java.lang.String ledVoltValue) {
        this.ledVoltValue = ledVoltValue;
    }


    /**
     * Gets the temperature value for this TermStateTO.
     * 
     * @return temperature
     */
    public java.lang.String getTemperature() {
        return temperature;
    }


    /**
     * Sets the temperature value for this TermStateTO.
     * 
     * @param temperature
     */
    public void setTemperature(java.lang.String temperature) {
        this.temperature = temperature;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TermStateTO)) return false;
        TermStateTO other = (TermStateTO) obj;
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
            ((this.coolerStatYN==null && other.getCoolerStatYN()==null) || 
             (this.coolerStatYN!=null &&
              this.coolerStatYN.equals(other.getCoolerStatYN()))) &&
            ((this.doorOpenCnt==null && other.getDoorOpenCnt()==null) || 
             (this.doorOpenCnt!=null &&
              this.doorOpenCnt.equals(other.getDoorOpenCnt()))) &&
            ((this.doorStatYN==null && other.getDoorStatYN()==null) || 
             (this.doorStatYN!=null &&
              this.doorStatYN.equals(other.getDoorStatYN()))) &&
            ((this.humidity==null && other.getHumidity()==null) || 
             (this.humidity!=null &&
              this.humidity.equals(other.getHumidity()))) &&
            ((this.lcdSenceStatYN==null && other.getLcdSenceStatYN()==null) || 
             (this.lcdSenceStatYN!=null &&
              this.lcdSenceStatYN.equals(other.getLcdSenceStatYN()))) &&
            ((this.ledVoltValue==null && other.getLedVoltValue()==null) || 
             (this.ledVoltValue!=null &&
              this.ledVoltValue.equals(other.getLedVoltValue()))) &&
            ((this.temperature==null && other.getTemperature()==null) || 
             (this.temperature!=null &&
              this.temperature.equals(other.getTemperature())));
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
        if (getCoolerStatYN() != null) {
            _hashCode += getCoolerStatYN().hashCode();
        }
        if (getDoorOpenCnt() != null) {
            _hashCode += getDoorOpenCnt().hashCode();
        }
        if (getDoorStatYN() != null) {
            _hashCode += getDoorStatYN().hashCode();
        }
        if (getHumidity() != null) {
            _hashCode += getHumidity().hashCode();
        }
        if (getLcdSenceStatYN() != null) {
            _hashCode += getLcdSenceStatYN().hashCode();
        }
        if (getLedVoltValue() != null) {
            _hashCode += getLedVoltValue().hashCode();
        }
        if (getTemperature() != null) {
            _hashCode += getTemperature().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TermStateTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "TermStateTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bitID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "bitID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coolerStatYN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "coolerStatYN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doorOpenCnt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "doorOpenCnt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doorStatYN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "doorStatYN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("humidity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "humidity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lcdSenceStatYN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "lcdSenceStatYN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ledVoltValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "ledVoltValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "temperature"));
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
