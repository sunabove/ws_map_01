package gmlviewer.gis.comp;

import java.awt.*;

public interface SenseComp {

    public Shape [] getSensitiveShapes();

    public String [] getToolTipTexts();

    public void setToolTipText(String text);

}
