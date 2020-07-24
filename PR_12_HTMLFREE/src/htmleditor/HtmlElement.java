
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import java.awt.*;
import java.util.*;
import java.io.*;
import jcosmos.*;

public abstract class HtmlElement implements CharacterUtility {

   protected boolean debugFlag = true; // debug flag
   protected HtmlDocument parentDoc;
   protected String href = "";
   protected String target ;

   public abstract Vector createViews(Point scanPoint, Insets margin);

   public HtmlDocument getParentDocument() {

      return parentDoc;

   }

   public void setParentDocument(HtmlDocument parentDoc) {

      this.parentDoc = parentDoc;

   }

   public String getHref() {
      return href;
   }

   public String getTarget() {
      return target;
   }

   public void setHref(String href, String target) {
      this.href = href;
      this.target = target;
   }

   public static String startComment(String comment) {
       return "<!-- " + comment + " -->";
   }

   private final static String END_OF = "E ";

   public static String endComment(String comment ) {
       return startComment( END_OF + comment );
   }

   private static String commentTag(String tag, String comment ) {
       return startComment( comment ) + nl + tag + nl + endComment( comment );
   }

   public static  String comment(String tag, HtmlComment commentObj ) {
       return commentTag( tag, commentObj.commentText() );
   }

}