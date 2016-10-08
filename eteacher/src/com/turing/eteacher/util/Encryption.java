package com.turing.eteacher.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 密码MD5加密工具类
 * @author caojian
 */
public class Encryption
{
    /**
     * 
     * <li>功能描述：用MD5方式加密给定字符串。
     * 
     * @param encryptionString String 给定字符串
     * @return String 加密后的字符串
     * @author caojian
     */
    public static String encryption(String encryptionString)
    {
        MessageDigest messageDigest = null;

        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(encryptionString.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e)
        {
            System.exit(-1);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
            {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else
            {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

}
