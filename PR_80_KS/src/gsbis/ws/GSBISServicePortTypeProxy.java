package gsbis.ws;

public class GSBISServicePortTypeProxy implements gsbis.ws.GSBISServicePortType {
  private String _endpoint = null;
  private gsbis.ws.GSBISServicePortType gSBISServicePortType = null;
  
  public GSBISServicePortTypeProxy() {
    _initGSBISServicePortTypeProxy();
  }
  
  public GSBISServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initGSBISServicePortTypeProxy();
  }
  
  private void _initGSBISServicePortTypeProxy() {
    try {
      gSBISServicePortType = (new gsbis.ws.GSBISServiceLocator()).getGSBISServiceSOAP11port_http();
      if (gSBISServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)gSBISServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)gSBISServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (gSBISServicePortType != null)
      ((javax.xml.rpc.Stub)gSBISServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gsbis.ws.GSBISServicePortType getGSBISServicePortType() {
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType;
  }
  
  public gsbis.bean.xsd.EnvTO getGSBISEnv(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISEnv(bitID);
  }
  
  public gsbis.bean.xsd.ArrInfoTO[] getGSBISArrInfo(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISArrInfo(bitID);
  }
  
  public boolean setGSBISState(gsbis.bean.xsd.TermStateTO termstate) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.setGSBISState(termstate);
  }
  
  public boolean setGSBISCommand(gsbis.bean.xsd.CommandTO com) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.setGSBISCommand(com);
  }
  
  public boolean setGSBISZigBee(gsbis.bean.xsd.ZigBeeTO zigbee) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.setGSBISZigBee(zigbee);
  }
  
  public gsbis.bean.xsd.MsgTO getGSBISMsg(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISMsg(bitID);
  }
  
  public java.lang.String getTest(java.lang.String str) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getTest(str);
  }
  
  public gsbis.bean.xsd.NodeInfoTO[] getGSBISNodeInfo(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISNodeInfo(bitID);
  }
  
  public gsbis.bean.xsd.PubMsgTO[] getGSBISPubMsg(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISPubMsg(bitID);
  }
  
  public gsbis.bean.xsd.NewsTO getGSBISNews(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISNews(bitID);
  }
  
  public gsbis.bean.xsd.CommandTO getGSBISCommand(java.lang.String bitID) throws java.rmi.RemoteException{
    if (gSBISServicePortType == null)
      _initGSBISServicePortTypeProxy();
    return gSBISServicePortType.getGSBISCommand(bitID);
  }
  
  
}