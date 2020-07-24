
package com.jwordart.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilter_03_Dbf extends FileFilter
{

    public FileFilter_03_Dbf()
    {
    }

    @Override
	public String getDescription()
    {
        return "dBase and FoxPro DBF files";
    }

    @Override
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
