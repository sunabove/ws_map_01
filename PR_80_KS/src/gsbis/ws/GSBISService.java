/**
 * GSBISService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.ws;

public interface GSBISService extends javax.xml.rpc.Service {
    public java.lang.String getGSBISServiceSOAP11port_httpAddress();

    public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP11port_http() throws javax.xml.rpc.ServiceException;

    public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP11port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getGSBISServiceSOAP12port_httpAddress();

    public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP12port_http() throws javax.xml.rpc.ServiceException;

    public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP12port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
