/**
 * ArrInfoTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.bean.xsd;

public class ArrInfoTO  implements java.io.Serializable {
    private java.lang.String arrTime;

    private java.lang.String bitID;

    private java.lang.String busLineKindCD;

    private java.lang.String busLineSide;

    private java.lang.String busNo;

    private java.lang.String carTermID;

    private java.lang.String lastBus;

    private java.lang.String outbreakTypeName;

    private java.lang.String stationGap;

    public ArrInfoTO() {
    }

    public ArrInfoTO(
           java.lang.String arrTime,
           java.lang.String bitID,
           java.lang.String busLineKindCD,
           java.lang.String busLineSide,
           java.lang.String busNo,
           java.lang.String carTermID,
           java.lang.String lastBus,
           java.lang.String outbreakTypeName,
           java.lang.String stationGap) {
           this.arrTime = arrTime;
           this.bitID = bitID;
           this.busLineKindCD = busLineKindCD;
           this.busLineSide = busLineSide;
           this.busNo = busNo;
           this.carTermID = carTermID;
           this.lastBus = lastBus;
           this.outbreakTypeName = outbreakTypeName;
           this.stationGap = stationGap;
    }


    /**
     * Gets the arrTime value for this ArrInfoTO.
     * 
     * @return arrTime
     */
    public java.lang.String getArrTime() {
        return arrTime;
    }


    /**
     * Sets the arrTime value for this ArrInfoTO.
     * 
     * @param arrTime
     */
    public void setArrTime(java.lang.String arrTime) {
        this.arrTime = arrTime;
    }


    /**
     * Gets the bitID value for this ArrInfoTO.
     * 
     * @return bitID
     */
    public java.lang.String getBitID() {
        return bitID;
    }


    /**
     * Sets the bitID value for this ArrInfoTO.
     * 
     * @param bitID
     */
    public void setBitID(java.lang.String bitID) {
        this.bitID = bitID;
    }


    /**
     * Gets the busLineKindCD value for this ArrInfoTO.
     * 
     * @return busLineKindCD
     */
    public java.lang.String getBusLineKindCD() {
        return busLineKindCD;
    }


    /**
     * Sets the busLineKindCD value for this ArrInfoTO.
     * 
     * @param busLineKindCD
     */
    public void setBusLineKindCD(java.lang.String busLineKindCD) {
        this.busLineKindCD = busLineKindCD;
    }


    /**
     * Gets the busLineSide value for this ArrInfoTO.
     * 
     * @return busLineSide
     */
    public java.lang.String getBusLineSide() {
        return busLineSide;
    }


    /**
     * Sets the busLineSide value for this ArrInfoTO.
     * 
     * @param busLineSide
     */
    public void setBusLineSide(java.lang.String busLineSide) {
        this.busLineSide = busLineSide;
    }


    /**
     * Gets the busNo value for this ArrInfoTO.
     * 
     * @return busNo
     */
    public java.lang.String getBusNo() {
        return busNo;
    }


    /**
     * Sets the busNo value for this ArrInfoTO.
     * 
     * @param busNo
     */
    public void setBusNo(java.lang.String busNo) {
        this.busNo = busNo;
    }


    /**
     * Gets the carTermID value for this ArrInfoTO.
     * 
     * @return carTermID
     */
    public java.lang.String getCarTermID() {
        return carTermID;
    }


    /**
     * Sets the carTermID value for this ArrInfoTO.
     * 
     * @param carTermID
     */
    public void setCarTermID(java.lang.String carTermID) {
        this.carTermID = carTermID;
    }


    /**
     * Gets the lastBus value for this ArrInfoTO.
     * 
     * @return lastBus
     */
    public java.lang.String getLastBus() {
        return lastBus;
    }


    /**
     * Sets the lastBus value for this ArrInfoTO.
     * 
     * @param lastBus
     */
    public void setLastBus(java.lang.String lastBus) {
        this.lastBus = lastBus;
    }


    /**
     * Gets the outbreakTypeName value for this ArrInfoTO.
     * 
     * @return outbreakTypeName
     */
    public java.lang.String getOutbreakTypeName() {
        return outbreakTypeName;
    }


    /**
     * Sets the outbreakTypeName value for this ArrInfoTO.
     * 
     * @param outbreakTypeName
     */
    public void setOutbreakTypeName(java.lang.String outbreakTypeName) {
        this.outbreakTypeName = outbreakTypeName;
    }


    /**
     * Gets the stationGap value for this ArrInfoTO.
     * 
     * @return stationGap
     */
    public java.lang.String getStationGap() {
        return stationGap;
    }


    /**
     * Sets the stationGap value for this ArrInfoTO.
     * 
     * @param stationGap
     */
    public void setStationGap(java.lang.String stationGap) {
        this.stationGap = stationGap;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrInfoTO)) return false;
        ArrInfoTO other = (ArrInfoTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.arrTime==null && other.getArrTime()==null) || 
             (this.arrTime!=null &&
              this.arrTime.equals(other.getArrTime()))) &&
            ((this.bitID==null && other.getBitID()==null) || 
             (this.bitID!=null &&
              this.bitID.equals(other.getBitID()))) &&
            ((this.busLineKindCD==null && other.getBusLineKindCD()==null) || 
             (this.busLineKindCD!=null &&
              this.busLineKindCD.equals(other.getBusLineKindCD()))) &&
            ((this.busLineSide==null && other.getBusLineSide()==null) || 
             (this.busLineSide!=null &&
              this.busLineSide.equals(other.getBusLineSide()))) &&
            ((this.busNo==null && other.getBusNo()==null) || 
             (this.busNo!=null &&
              this.busNo.equals(other.getBusNo()))) &&
            ((this.carTermID==null && other.getCarTermID()==null) || 
             (this.carTermID!=null &&
              this.carTermID.equals(other.getCarTermID()))) &&
            ((this.lastBus==null && other.getLastBus()==null) || 
             (this.lastBus!=null &&
              this.lastBus.equals(other.getLastBus()))) &&
            ((this.outbreakTypeName==null && other.getOutbreakTypeName()==null) || 
             (this.outbreakTypeName!=null &&
              this.outbreakTypeName.equals(other.getOutbreakTypeName()))) &&
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
        if (getArrTime() != null) {
            _hashCode += getArrTime().hashCode();
        }
        if (getBitID() != null) {
            _hashCode += getBitID().hashCode();
        }
        if (getBusLineKindCD() != null) {
            _hashCode += getBusLineKindCD().hashCode();
        }
        if (getBusLineSide() != null) {
            _hashCode += getBusLineSide().hashCode();
        }
        if (getBusNo() != null) {
            _hashCode += getBusNo().hashCode();
        }
        if (getCarTermID() != null) {
            _hashCode += getCarTermID().hashCode();
        }
        if (getLastBus() != null) {
            _hashCode += getLastBus().hashCode();
        }
        if (getOutbreakTypeName() != null) {
            _hashCode += getOutbreakTypeName().hashCode();
        }
        if (getStationGap() != null) {
            _hashCode += getStationGap().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ArrInfoTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "ArrInfoTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "arrTime"));
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
        elemField.setFieldName("busLineKindCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "busLineKindCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busLineSide");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "busLineSide"));
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
        elemField.setFieldName("carTermID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "carTermID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastBus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "lastBus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outbreakTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://bean.gsbis/xsd", "outbreakTypeName"));
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
