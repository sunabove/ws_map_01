
package com.ynhenc.gis.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilter_03_Dbf extends FileFilter
{

    public FileFilter_03_Dbf()
    {
    }

    public String getDescription()
    {
        return "dBase and FoxPro DBF files";
    }

    public boolean accept(File file)
    {
        if(file.isDirectory())
        {
            return true;
        } else
        {
            String s = file.getName().toUpperCase();
            return s.endsWith(".DBF");
        }
    }
}
