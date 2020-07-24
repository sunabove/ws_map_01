/**
 * GSBISServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.ws;

import gsbis.bean.xsd.ArrInfoTO;
import gsbis.bean.xsd.EnvTO;

public class GSBISServiceLocator extends org.apache.axis.client.Service
		implements gsbis.ws.GSBISService {

	public GSBISServiceLocator() {
	}

	public GSBISServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public GSBISServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for GSBISServiceSOAP11port_http
	private java.lang.String GSBISServiceSOAP11port_http_address = "http://59.25.178.103:8080/axis2/services/GSBISService";

	public java.lang.String getGSBISServiceSOAP11port_httpAddress() {
		return GSBISServiceSOAP11port_http_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String GSBISServiceSOAP11port_httpWSDDServiceName = "GSBISServiceSOAP11port_http";

	public java.lang.String getGSBISServiceSOAP11port_httpWSDDServiceName() {
		return GSBISServiceSOAP11port_httpWSDDServiceName;
	}

	public void setGSBISServiceSOAP11port_httpWSDDServiceName(
			java.lang.String name) {
		GSBISServiceSOAP11port_httpWSDDServiceName = name;
	}

	public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP11port_http()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(GSBISServiceSOAP11port_http_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getGSBISServiceSOAP11port_http(endpoint);
	}

	public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP11port_http(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			gsbis.ws.GSBISServiceSOAP11BindingStub _stub = new gsbis.ws.GSBISServiceSOAP11BindingStub(
					portAddress, this);
			_stub.setPortName(getGSBISServiceSOAP11port_httpWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setGSBISServiceSOAP11port_httpEndpointAddress(
			java.lang.String address) {
		GSBISServiceSOAP11port_http_address = address;
	}

	// Use to get a proxy class for GSBISServiceSOAP12port_http
	private java.lang.String GSBISServiceSOAP12port_http_address = "http://59.25.178.103:8080/axis2/services/GSBISService";

	public java.lang.String getGSBISServiceSOAP12port_httpAddress() {
		return GSBISServiceSOAP12port_http_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String GSBISServiceSOAP12port_httpWSDDServiceName = "GSBISServiceSOAP12port_http";

	public java.lang.String getGSBISServiceSOAP12port_httpWSDDServiceName() {
		return GSBISServiceSOAP12port_httpWSDDServiceName;
	}

	public void setGSBISServiceSOAP12port_httpWSDDServiceName(
			java.lang.String name) {
		GSBISServiceSOAP12port_httpWSDDServiceName = name;
	}

	public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP12port_http()
			throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(GSBISServiceSOAP12port_http_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getGSBISServiceSOAP12port_http(endpoint);
	}

	public gsbis.ws.GSBISServicePortType getGSBISServiceSOAP12port_http(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			gsbis.ws.GSBISServiceSOAP12BindingStub _stub = new gsbis.ws.GSBISServiceSOAP12BindingStub(
					portAddress, this);
			_stub.setPortName(getGSBISServiceSOAP12port_httpWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setGSBISServiceSOAP12port_httpEndpointAddress(
			java.lang.String address) {
		GSBISServiceSOAP12port_http_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown. This
	 * service has multiple ports for a given interface; the proxy
	 * implementation returned may be indeterminate.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (gsbis.ws.GSBISServicePortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				gsbis.ws.GSBISServiceSOAP11BindingStub _stub = new gsbis.ws.GSBISServiceSOAP11BindingStub(
						new java.net.URL(GSBISServiceSOAP11port_http_address),
						this);
				_stub
						.setPortName(getGSBISServiceSOAP11port_httpWSDDServiceName());
				return _stub;
			}
			if (gsbis.ws.GSBISServicePortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				gsbis.ws.GSBISServiceSOAP12BindingStub _stub = new gsbis.ws.GSBISServiceSOAP12BindingStub(
						new java.net.URL(GSBISServiceSOAP12port_http_address),
						this);
				_stub
						.setPortName(getGSBISServiceSOAP12port_httpWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("GSBISServiceSOAP11port_http".equals(inputPortName)) {
			return getGSBISServiceSOAP11port_http();
		} else if ("GSBISServiceSOAP12port_http".equals(inputPortName)) {
			return getGSBISServiceSOAP12port_http();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://ws.gsbis", "GSBISService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://ws.gsbis",
					"GSBISServiceSOAP11port_http"));
			ports.add(new javax.xml.namespace.QName("http://ws.gsbis",
					"GSBISServiceSOAP12port_http"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("GSBISServiceSOAP11port_http".equals(portName)) {
			setGSBISServiceSOAP11port_httpEndpointAddress(address);
		} else if ("GSBISServiceSOAP12port_http".equals(portName)) {
			setGSBISServiceSOAP12port_httpEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

	public static void main(String[] args) throws Exception {

		GSBISServiceLocator locator = new GSBISServiceLocator();

		GSBISServicePortType portType = locator
				.getGSBISServiceSOAP12port_http();

		String bitID = "361000092";

		ArrInfoTO[] arrInfoTO_List = portType.getGSBISArrInfo(bitID);
		
		System.out.println( "Array SIZE = " + arrInfoTO_List.length );

		for (ArrInfoTO arrInfoTO : arrInfoTO_List) {

			System.out.println("ArrInfoTO getArrTime = " + arrInfoTO.getArrTime());
			System.out.println("ArrInfoTO getBusNo = " + arrInfoTO.getBusNo() );

		}

		if (false) {

			EnvTO envTo = portType.getGSBISEnv("361000092");

			System.out.println("RESULT = " + envTo.getAppStTime());

		}
	}

}
