package com.mua.mua.cshare.model;

import java.io.File;

public class Util {

    static Util util=null;

    File currentFile=null;
    String currentFileLink=null;



    public static Util getUtil() {
        if(util==null)
            util=new Util();
        return util;
    }



    public static File getCurrentFile()
    {
        return getUtil().currentFile;
    }

    public static String getCurrentFileLink()
    {
        return getUtil().currentFileLink;
    }




    public static void setCurrentFile(File file)
    {
        getUtil().currentFile=file;
    }

    public static void setCurrentFileLink(String fileLink)
    {
        getUtil().currentFileLink=fileLink;
    }

}
