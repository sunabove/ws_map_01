package com.ynhenc.comm.web;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;

import com.ynhenc.comm.Server;
import com.ynhenc.comm.file.FileManager;


public class MultiPartRequest extends Request {
    public MultiPartRequest(HttpServletRequest srvletReq ) {
        super( srvletReq );

        DiskFileUpload diskFilUpload = new DiskFileUpload();

        this.diskFilUpload = diskFilUpload;

        final long mega = 1000000;

        diskFilUpload.setSizeMax( 10*mega );
        // maximum size that will be stored in memory
        diskFilUpload.setSizeThreshold(4096);
        // the location for saving data that is larger than getSizeThreshold()
        FileManager fileManager = Server.getFileManager();
        File tempDir = fileManager.getTempDir();
        if( tempDir != null ) {
            diskFilUpload.setRepositoryPath(tempDir.getAbsolutePath());
        }

        try {
            List allItems = this.diskFilUpload.parseRequest( srvletReq );
            List fileItems = new ArrayList< List >();
            Hashtable paraItems = new Hashtable();
            Iterator it = allItems.iterator();
            FileItem item;
            String itemName, pathName;
            while (it.hasNext()) {
                item = (FileItem) (it.next());
                if( item.isFormField() ) {
                    itemName = item.getFieldName();
                    if( itemName != null ) {
                        paraItems.put(itemName, item.getString());
                    } else {
                        debug.println( this, "Null named form field = " + item );
                    }
                } else {
                    pathName = item.getOrgFilePathName();
                    if( pathName != null && pathName.trim().length() > 0 ) {
                        fileItems.add(item);
                    } else {
                        debug.println(this, "Skipped a null file item = " + item);
                    }
                }
            }
            this.fileItems = fileItems;
            this.paraItems = paraItems;

        } catch (FileUploadException ex) {
            debug.println(this, ex);
            this.fileItems = new ArrayList<List>() ;
            this.paraItems = new Hashtable() ;
        }
    }

    @Override
	public String getParameter(String name) {
        Hashtable paraItems = this.paraItems;
        if (paraItems == null) {
            return null;
        } else {
            Object obj = paraItems.get( name );
            if( obj != null ) {
                if( obj instanceof String ) {
                    return (String) ( obj );
                } else {
                    return obj.toString();
                }
            } else {
                return null;
            }
        }
    }

    public List getFileItems() {
        return this.fileItems;
    }

    private DiskFileUpload diskFilUpload;
    private List fileItems;
    private Hashtable paraItems;
}
