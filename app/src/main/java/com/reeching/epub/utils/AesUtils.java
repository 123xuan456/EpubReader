package com.reeching.epub.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 绍轩 on 2017/11/29.
 */

public  class AesUtils {
    private static final String IV_STRING = "3D25C61F9B04A7E8";
    private final static String KEY = "4F9D236150A7E8CB";
    private  static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private  static final String AES = "AES";//AES 加密

    /*
        * 加密和解密的密钥必须一致，
        */
    public static String generateKey() {
        return IV_STRING;
    }
    /*
    * 加密
    */
    public static byte[] encrypt(String key, byte[] clear)  {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,ivParameterSpec);
            byte[] encrypted = cipher.doFinal(clear);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /*
     * 解密
     */
    public static byte[] decrypt(byte[] encrypted)  {
        byte[] raw = new byte[0];
        try {
            raw = KEY.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,ivParameterSpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return decrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }








}
