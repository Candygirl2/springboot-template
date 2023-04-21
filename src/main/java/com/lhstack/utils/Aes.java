package com.lhstack.utils;

import java.security.Security;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StringUtils;

public class Aes {

    private static final String CBC = "AES/CBC/PKCS7Padding";

    private static final String ECB = "AES/ECB/PKCS7Padding";

    static {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        String name = bouncyCastleProvider.getName();
        if(Security.getProvider(name) == null){
            Security.addProvider(bouncyCastleProvider);
        }
    }
    
    public static byte[] decrypt(String key,String iv,byte[] encryptBytes){
        try{
            Cipher cipher = StringUtils.hasText(iv) ? Cipher.getInstance(CBC) : Cipher.getInstance(ECB);
            if(StringUtils.hasText(iv)){
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes()));
            }else {
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            }
            return cipher.doFinal(encryptBytes);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(String key,String iv,byte[] bytes){
        try{
            Cipher cipher = StringUtils.hasText(iv) ? Cipher.getInstance(CBC) : Cipher.getInstance(ECB);
            if(StringUtils.hasText(iv)){
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes()));
            }else {
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            }
            return cipher.doFinal(bytes);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
