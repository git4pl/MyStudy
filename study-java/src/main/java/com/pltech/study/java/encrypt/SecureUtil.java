package com.v2ray.ang.helper;


import android.text.TextUtils;
import android.util.Base64;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureUtil {

    public static void main(String[] args) throws Exception {
        // 原文
        String input = "Hello Word";
        // 使用des进行加密，密钥必须是8个字节
        String key = "12345678";
        // 获取Cipher对象的算法
        String transformation = "DES";
        // 指定获取密钥的算法
        String algorithm = "DES";

        String encrypt = encrypt(input, key, transformation, algorithm);
        System.out.println("加密:" + encrypt);
        String decrypt = decrypt(encrypt, key, transformation, algorithm);
        System.out.println("解密:" + decrypt);

    }

    /**
     * 加密数据
     */
    public static String encrypt(String input, String key, String transformation, String algorithm) throws Exception {
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定秘钥规则：传入密钥的字节数组与指定的算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 初始化加密对象: 指定加密模式与秘钥规则
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // 开始加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 打印密文,由于bytes字节数字中存在负数，即ascii码有负数，解析不出来，将输出乱码
        System.out.println("bytes = " + new String(bytes));
        // 对字节数字进行Base64编码
        return Arrays.toString(Base64.encode(bytes, 0));
    }

    /**
     * 解密数据
     */
    public static String decrypt(String input, String key, String transformation, String algorithm) throws Exception {
        // 获取Cipher对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定秘钥规则：传入密钥的字节数组与指定的算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 初始化解密对象: 指定解密模式与秘钥规则
        cipher.init(Cipher.DECRYPT_MODE, sks);
        // 解密
        byte[] bytes = cipher.doFinal(Base64.decode(input, 0));
        return new String(bytes);
    }


    /**
     * 算法定义
     */
    private static final String AES_ALGORITHM = "AES";
    /**
     * 指定填充方式
     */
    private static final String CIPHER_PADDING = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_CBC_PADDING = "AES/CBC/PKCS5Padding";
    //private static final String CIPHER_CBC_PADDING = "aes-256-cbc";
    /**
     * 偏移量(CBC中使用，增强加密算法强度)
     */
    private static final String IV_SEED = "1234567812345678";
    private static final String ENCODING = "UTF-8";

    /**
     * AES加密
     *
     * @param content 待加密内容
     * @param aesKey  密码
     * @return
     */
    public static String encrypt(String content, String aesKey) {
        if (TextUtils.isEmpty(content)) {
            //LOGGER.info("AES encrypt: the content is null!");
            return null;
        }
        //判断秘钥是否为16位
        if (!TextUtils.isEmpty(aesKey) && aesKey.length() == 16) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置加密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择加密
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                //根据待加密内容生成字节数组
                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
                //返回base64字符串
                return Base64.encodeToString(encrypted, 0);
            } catch (Exception e) {
                //LOGGER.info("AES encrypt exception:" + e.getMessage());
                throw new RuntimeException(e);
            }

        } else {
            //LOGGER.info("AES encrypt: the aesKey is null or error!");
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param aesKey  密码
     * @return
     */
    public static String decrypt(String content, String aesKey) {
        if (TextUtils.isEmpty(content)) {
            //LOGGER.info("AES decrypt: the content is null!");
            return null;
        }
        //判断秘钥是否为16位
        if (!TextUtils.isEmpty(aesKey) && aesKey.length() == 32) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);

                //先进行Base64解码
                byte[] decodeBase64 = Base64.decode(content, 0);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted, ENCODING);
            } catch (Exception e) {
                //LOGGER.info("AES decrypt exception:" + e.getMessage());
                throw new RuntimeException(e);
            }

        } else {
            //LOGGER.info("AES decrypt: the aesKey is null or error!");
            return null;
        }
    }

    /**
     * AES_CBC加密
     *
     * @param content 待加密内容
     * @param aesKey  密码
     * @return
     */
//    public static String encryptCBC(String content, String aesKey) {
//        if (StringUtils.isBlank(content)) {
//            LOGGER.info("AES_CBC encrypt: the content is null!");
//            return null;
//        }
//        //判断秘钥是否为16位
//        if (StringUtils.isNotBlank(aesKey) && aesKey.length() == 16) {
//            try {
//                //对密码进行编码
//                byte[] bytes = aesKey.getBytes(ENCODING);
//                //设置加密算法，生成秘钥
//                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
//                // "算法/模式/补码方式"
//                Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
//                //偏移
//                IvParameterSpec iv = new IvParameterSpec(IV_SEED.getBytes(ENCODING));
//                //选择加密
//                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//                //根据待加密内容生成字节数组
//                byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
//                //返回base64字符串
//                return Base64Utils.encodeToString(encrypted);
//            } catch (Exception e) {
//                LOGGER.info("AES_CBC encrypt exception:" + e.getMessage());
//                throw new RuntimeException(e);
//            }
//
//        } else {
//            LOGGER.info("AES_CBC encrypt: the aesKey is null or error!");
//            return null;
//        }
//    }

    /**
     * AES_CBC解密
     *
     * @param content 待解密内容
     * @param aesKey  密码
     * @return
     */
    public static String decryptCBC(String content, String aesKey, String ivSeed) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        //判断秘钥是否为32位
        if (!TextUtils.isEmpty(aesKey) && aesKey.length() == 32) {
            try {
                //对密码进行编码
                byte[] bytes = aesKey.getBytes(ENCODING);
                //设置解密算法，生成秘钥
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
                //偏移
                IvParameterSpec iv = new IvParameterSpec(ivSeed.getBytes(ENCODING));
                // "算法/模式/补码方式"
                Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
                //选择解密
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

                //先进行Base64解码
                byte[] decodeBase64 = Base64.decode(content, 0);

                //根据待解密内容进行解密
                byte[] decrypted = cipher.doFinal(decodeBase64);
                //将字节数组转成字符串
                return new String(decrypted);
            } catch (Exception e) {
                //LOGGER.info("AES_CBC decrypt exception:" + e.getMessage());
                throw new RuntimeException(e);
            }

        } else {
            //LOGGER.info("AES_CBC decrypt: the aesKey is null or error!");
            return null;
        }
    }
}
