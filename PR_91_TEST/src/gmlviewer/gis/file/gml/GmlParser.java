package gmlviewer.gis.file.gml;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;
import gmlviewer.gis.kernel.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.util.*;

public class GmlParser
    extends CommonLib {

  /** All output will use this encoding */
  static final String outputEncoding = "UTF-8";

  /** Output goes here */
  private PrintWriter out;

  /** Indent level */
  private int indent = 0;

  private Document root;

  private File inputFile;
  private ArrayList gmlList;
  private GmlObject prevGmlObject;
  private GmlObject currGmlObject;
  private boolean debugFlag = false;

  /** Indentation will be in multiples of basicIndent  */
  private final String basicIndent = "  ";

  /** Constants used for JAXP 1.2 */
  static final String JAXP_SCHEMA_LANGUAGE =
      "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  static final String W3C_XML_SCHEMA =
      "http://www.w3.org/2001/XMLSchema";
  static final String JAXP_SCHEMA_SOURCE =
      "http://java.sun.com/xml/jaxp/properties/schemaSource";

  public GmlParser() {
    this.gmlList = new ArrayList();
  }

  /**
   * Echo common attributes of a DOM2 Node and terminate output with an
   * EOL character.
   */

  private int recNo = 0;
  private String preAttName;

  private void processNodeElement(Node n) {

    print("ELEM:");

    String nodeName = n.getNodeName().trim();

    if (nodeName.equalsIgnoreCase("gml:featureMember")) {
      this.prevGmlObject = this.currGmlObject;

      if (this.prevGmlObject != null) {
        //System.out.println();
        this.debug(this.prevGmlObject);
      }

      this.preAttName = null;
      this.currGmlObject = new GmlObject();
      this.currGmlObject.setRecNo(this.recNo);
      this.recNo ++ ;
      this.gmlList.add(this.currGmlObject);

    }else {
      this.preAttName = nodeName;
    }



    if (  true  ) {
      // Print attributes if any.  Note: element attributes are not
      // children of ELEMENT_NODEs but are properties of their
      // associated ELEMENT_NODE.  For this reason, they are printed
      // with 2x the indent level to indicate this.
      NamedNodeMap atts = n.getAttributes();
      indent += 2;
      for (int i = 0; i < atts.getLength(); i++) {
        Node att = atts.item(i);
        processNodeRecursive(att);
      }
      indent -= 2;
    }

    if (false) {

      this.printlnCommonOld(n);

    }

  }

  private void processNodeText( Node n ) {

    print("TEXT:");

    String attVal = n.getNodeValue();

    if( preAttName == null || this.currGmlObject == null ) {
    }
    else if ( preAttName.equalsIgnoreCase("gml2:OBJECTID")) {
      // 오브젝트 아이디.....
      this.currGmlObject.setObjectId(attVal);
    }
    else if (preAttName.equalsIgnoreCase("gml2:BLG_NM")) {
      // 빌딩명
      this.currGmlObject.setBldName( this.encode( attVal ) );
    }
    else if (preAttName.equalsIgnoreCase("gml2:TELEPHONE")) {
      // 전화 번호
      this.currGmlObject.setTelephoneNo(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml:coordinates" ) ) {
      // 요놈만 특이하게 gml: 로 시작함( gml2: 가 아님)
      // 도형(폴리곤)
      this.currGmlObject.setCoordinates(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:ADDRESS1" ) ) {
      // 주소 1
      this.currGmlObject.setAddress1(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:ADDRESS2" ) ) {
      // 주소 2
      this.currGmlObject.setAddress2(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:EX" ) ) {
      // 특이 사항
      this.currGmlObject.setEx(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:HOMEPARG" ) ) {
      // 홈페이지
      this.currGmlObject.setHomepage(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:KEY" ) ) {
      // 레이어 키 값.....
      this.currGmlObject.setKey(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:O_X" ) ) {
      // 검색 여부.....
      this.currGmlObject.setOx(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:X_COORD" ) ) {
      // 중앙 x
      this.currGmlObject.setCenterX(attVal);
    }
    else if( preAttName.equalsIgnoreCase( "gml2:Y_COORD" ) ) {
      // 중앙 y
      this.currGmlObject.setCenterY(attVal);
    }




    this.preAttName = null;

    if( false ) {

      this.debug( "preAttName = " + preAttName );

      this.debug(n);

    }

  }

  private String encode( String text ) {
    try {
      if( true ) {
        return text;
      }
      return new String( text.getBytes(),  "UTF-8"  );
    }
    catch (UnsupportedEncodingException ex) {
      this.debug( ex );
      return text;
    }
  }

  private void processNodeOne(Node n) {

    if (false) {

      this.printlnCommonOld(n);

    }

  }

  private void debug(Node n) {

    String msg = "";

    msg += (" nodeName=\"" + n.getNodeName() + "\"" );

    String val = n.getNamespaceURI();
    if (val != null) {
      msg += (" uri=\"" + val + "\"");
    }

    val = n.getPrefix();
    if (val != null) {
      msg +=(" pre=\"" + val + "\"");
    }

    val = n.getLocalName();
    if (val != null) {
      msg +=(" local=\"" + val + "\"");
    }

    val = n.getNodeValue();
    if (val != null) {
      msg +=(" nodeValue=");
      if (val.trim().equals("")) {
        // Whitespace
        msg +=("[WS]");
      }
      else {
        msg +=("\"" + n.getNodeValue() + "\"");
      }
    }

    this.debug( msg );

  }

  public void debug( Object obj ) {

    if( this.debugFlag ) {
      super.debug(obj);
    }

  }


  private void printlnCommonOld(Node n) {

    print(" nodeName=\"" + n.getNodeName() + "\"");

    String val = n.getNamespaceURI();
    if (val != null) {
      print(" uri=\"" + val + "\"");
    }

    val = n.getPrefix();
    if (val != null) {
      print(" pre=\"" + val + "\"");
    }

    val = n.getLocalName();
    if (val != null) {
      print(" local=\"" + val + "\"");
    }

    val = n.getNodeValue();
    if (val != null) {
      print(" nodeValue=");
      if (val.trim().equals("")) {
        // Whitespace
        print("[WS]");
      }
      else {
        print("\"" + n.getNodeValue() + "\"");
      }
    }
    println();
  }

  private void print(String text) {
    if (out != null) {
      out.print(text);
    }
  }

  private void println() {
    if (out != null) {
      out.println();
    }
  }

  /**
   * Indent to the current level in multiples of basicIndent
   */
  private void outputIndentation() {
    for (int i = 0; i < indent; i++) {
      print(basicIndent);
    }
  }

  /**
   * Recursive routine to print out DOM tree nodes
   */
  private void processNodeRecursive(Node n) {
    // Indent to the current level before printing anything
    outputIndentation();

    int type = n.getNodeType();
    switch (type) {
      case Node.ATTRIBUTE_NODE:
        print("ATTR:");
        processNodeOne(n);
        break;
      case Node.CDATA_SECTION_NODE:
        print("CDATA:");
        processNodeOne(n);
        break;
      case Node.COMMENT_NODE:
        print("COMM:");
        processNodeOne(n);
        break;
      case Node.DOCUMENT_FRAGMENT_NODE:
        print("DOC_FRAG:");
        processNodeOne(n);
        break;
      case Node.DOCUMENT_NODE:
        print("DOC:");
        processNodeOne(n);
        break;
      case Node.DOCUMENT_TYPE_NODE:
        print("DOC_TYPE:");
        processNodeOne(n);

        // Print entities if any
        NamedNodeMap nodeMap = ( (DocumentType) n).getEntities();
        indent += 2;
        for (int i = 0; i < nodeMap.getLength(); i++) {
          Entity entity = (Entity) nodeMap.item(i);
          processNodeRecursive(entity);
        }
        indent -= 2;
        break;
      case Node.ELEMENT_NODE:

        // 엘리먼트 타입일 때.....
        processNodeElement(n);

        if (false) {
          // Print attributes if any.  Note: element attributes are not
          // children of ELEMENT_NODEs but are properties of their
          // associated ELEMENT_NODE.  For this reason, they are printed
          // with 2x the indent level to indicate this.
          NamedNodeMap atts = n.getAttributes();
          indent += 2;
          for (int i = 0; i < atts.getLength(); i++) {
            Node att = atts.item(i);
            processNodeRecursive(att);
          }
          indent -= 2;
        }

        break;
      case Node.ENTITY_NODE:
        print("ENT:");
        processNodeOne(n);
        break;
      case Node.ENTITY_REFERENCE_NODE:
        print("ENT_REF:");
        processNodeOne(n);
        break;
      case Node.NOTATION_NODE:
        print("NOTATION:");
        processNodeOne(n);
        break;
      case Node.PROCESSING_INSTRUCTION_NODE:
        print("PROC_INST:");
        processNodeOne(n);
        break;
      case Node.TEXT_NODE:
        processNodeText(n);
        break;
      default:
        print("UNSUPPORTED NODE: " + type);
        processNodeOne(n);
        break;
    }

    // Print children if any
    indent++;
    for (Node child = n.getFirstChild(); child != null;
         child = child.getNextSibling()) {
      processNodeRecursive(child);
    }
    indent--;
  }

  public void parse(File inputFile) throws Exception {

    this.setInputFile(inputFile);

    this.parse();

  }

  public void parse() throws Exception {

    File inputFile = this.getInputFile();

    if (inputFile == null) {

      System.err.println(
          "Error: 파싱할 파일이 없습니다."
          );

      return;
    }

    boolean dtdValidate = false;
    boolean xsdValidate = false;
    String schemaSource = null;

    boolean ignoreWhitespace = false;
    boolean ignoreComments = false;
    boolean putCDATAIntoText = false;
    boolean createEntityRefs = false;

    // Step 1: create a DocumentBuilderFactory and configure it
    DocumentBuilderFactory dbf =
        DocumentBuilderFactory.newInstance();

    // Set namespaceAware to true to get a DOM Level 2 tree with nodes
    // containing namesapce information.  This is necessary because the
    // default value from JAXP 1.0 was defined to be false.
    dbf.setNamespaceAware(true);

    // Set the validation mode to either: no validation, DTD
    // validation, or XSD validation
    dbf.setValidating(dtdValidate || xsdValidate);
    if (xsdValidate) {
      try {
        dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
      }
      catch (IllegalArgumentException x) {
        // This can happen if the parser does not support JAXP 1.2
        System.err.println(
            "Error: JAXP DocumentBuilderFactory attribute not recognized: "
            + JAXP_SCHEMA_LANGUAGE);
        System.err.println(
            "Check to see if parser conforms to JAXP 1.2 spec.");
        System.exit(1);
      }
    }

    // Set the schema source, if any.  See the JAXP 1.2 maintenance
    // update specification for more complex usages of this feature.
    if (schemaSource != null) {
      dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File(schemaSource));
    }

    // Optional: set various configuration options
    dbf.setIgnoringComments(ignoreComments);
    dbf.setIgnoringElementContentWhitespace(ignoreWhitespace);
    dbf.setCoalescing(putCDATAIntoText);
    // The opposite of creating entity ref nodes is expanding them inline
    dbf.setExpandEntityReferences(!createEntityRefs);

    // Step 2: create a DocumentBuilder that satisfies the constraints
    // specified by the DocumentBuilderFactory
    DocumentBuilder db = dbf.newDocumentBuilder();

    // Set an ErrorHandler before parsing
    OutputStreamWriter errorWriter =
        new OutputStreamWriter(System.err, outputEncoding);
    db.setErrorHandler(
        new MyErrorHandler(new PrintWriter(errorWriter, true)));

    // Step 3: parse the input file
    InputSource src = new InputSource(new FileReader(inputFile));
    //src.setEncoding("UTF-8");

    Document doc = db.parse(src);

    this.root = doc;

    this.processNodeRecursive(doc);

  }

  // Error handler to report errors and warnings
  private static class MyErrorHandler
      implements ErrorHandler {
    /** Error handler output goes here */
    private PrintWriter out;

    MyErrorHandler(PrintWriter out) {
      this.out = out;
    }

    /**
     * Returns a string describing parse exception details
     */
    private String getParseExceptionInfo(SAXParseException spe) {
      String systemId = spe.getSystemId();
      if (systemId == null) {
        systemId = "null";
      }
      String info = "URI=" + systemId +
          " Line=" + spe.getLineNumber() +
          ": " + spe.getMessage();
      return info;
    }

    // The following methods are standard SAX ErrorHandler methods.
    // See SAX documentation for more info.

    public void warning(SAXParseException spe) throws SAXException {
      out.println("Warning: " + getParseExceptionInfo(spe));
    }

    public void error(SAXParseException spe) throws SAXException {
      String message = "Error: " + getParseExceptionInfo(spe);
      throw new SAXException(message);
    }

    public void fatalError(SAXParseException spe) throws SAXException {
      String message = "Fatal Error: " + getParseExceptionInfo(spe);
      throw new SAXException(message);
    }
  }

  public File getInputFile() {
    return inputFile;
  }

  public ArrayList getGmlList() {
    return gmlList;
  }

  public void setInputFile(File inputFile) {
    this.inputFile = inputFile;
  }

  public void setOut(PrintWriter out) {
    this.out = out;
  }

  public void setDebugFlag(boolean debugFlag) {
    this.debugFlag = debugFlag;
  }

  public Lyr getLyr( File file ) throws Exception {

    if( file == null ) {

      return null;

    } else {

      String lyrName = FileUtil.getFileBodyName(file);

      GmlParser gmlParser = this;

      gmlParser.setDebugFlag( false );
      gmlParser.parse( file );

      Lyr lyr = new Lyr(lyrName, LyrType.AREA, 0);

      ArrayList gmlList = this.getGmlList();

      GmlArea gmlShape;
      GmlObject gmlObject;
      for (int i = 0; i < gmlList.size(); i++) {
        gmlObject = (GmlObject) gmlList.get(i);
        gmlShape = new GmlArea(gmlObject);
        lyr.addSpatialShape(gmlShape);
      }

      return lyr;
    }

  }

  public static void main(String[] args) throws Exception {

    GmlParser gmlParser = new GmlParser();

    String filename =
        "C:\\Documents and Settings\\Administrator\\바탕 화면\\gml\\HBuild.gml";

    File file = new File(filename);

    if ( true ) {

      gmlParser.setDebugFlag( true );
      gmlParser.setOut(new PrintWriter(new OutputStreamWriter(System.out,
          outputEncoding), true));
    }

    gmlParser.parse(file);

  }

}
