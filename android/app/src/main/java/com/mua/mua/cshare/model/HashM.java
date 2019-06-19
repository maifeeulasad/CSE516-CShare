package com.mua.mua.cshare.model;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashM {

    private static String getFileChecksum(MessageDigest digest, File file)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            };
            fis.close();
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            return "null";
        }
    }

    public  static String getFileChecksum(File file)
    {
        try {
            return getFileChecksum(MessageDigest.getInstance("SHA-128"),file);
        } catch (NoSuchAlgorithmException e) {
            return "null";
        }
    }

}
