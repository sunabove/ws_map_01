/**
 * GSBISServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gsbis.ws;

public interface GSBISServicePortType extends java.rmi.Remote {
    public gsbis.bean.xsd.EnvTO getGSBISEnv(java.lang.String bitID) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.ArrInfoTO[] getGSBISArrInfo(java.lang.String bitID) throws java.rmi.RemoteException;
    public boolean setGSBISState(gsbis.bean.xsd.TermStateTO termstate) throws java.rmi.RemoteException;
    public boolean setGSBISCommand(gsbis.bean.xsd.CommandTO com) throws java.rmi.RemoteException;
    public boolean setGSBISZigBee(gsbis.bean.xsd.ZigBeeTO zigbee) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.MsgTO getGSBISMsg(java.lang.String bitID) throws java.rmi.RemoteException;
    public java.lang.String getTest(java.lang.String str) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.NodeInfoTO[] getGSBISNodeInfo(java.lang.String bitID) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.PubMsgTO[] getGSBISPubMsg(java.lang.String bitID) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.NewsTO getGSBISNews(java.lang.String bitID) throws java.rmi.RemoteException;
    public gsbis.bean.xsd.CommandTO getGSBISCommand(java.lang.String bitID) throws java.rmi.RemoteException;
}
