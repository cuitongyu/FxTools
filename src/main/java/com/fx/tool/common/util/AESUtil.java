package com.fx.tool.common.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String KEY = "k6UpC9igjDrTRI9UtmK1";
    private static final int EN_CODE = 1;
    private static final int DE_CODE = 2;
    private static final String DES_ALGORITHM = "AES";

    public AESUtil() {
    }

    public static String encrypt(String data) {
        return doAES(data, "k6UpC9igjDrTRI9UtmK1", 1);
    }

    public static String decrypt(String data) {
        return doAES(data, "k6UpC9igjDrTRI9UtmK1", 2);
    }

    private static String doAES(String data, String key, int model) {
        try {
            data = data == null ? "" : data;
            key = key == null ? "" : key;
            if (!"".equalsIgnoreCase(data) && !"".equalsIgnoreCase(key)) {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128, new SecureRandom(key.getBytes("UTF-8")));
                SecretKey secretKey = generateKey(key);
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(model, keySpec);
                byte[] content;
                byte[] result;
                switch(model) {
                    case 1:
                        content = data.getBytes("UTF-8");
                        result = cipher.doFinal(content);
                        return parseByte2HexStr(result);
                    case 2:
                        content = parseHexStr2Byte(data);
                        result = cipher.doFinal(content);
                        return new String(result, "UTF-8");
                    default:
                        throw new RuntimeException("The Encrypt Model Is Wrong!");
                }
            } else {
                return null;
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secretKey.getBytes());
            KeyGenerator kg = null;

            try {
                kg = KeyGenerator.getInstance("AES");
            } catch (NoSuchAlgorithmException var4) {
            }

            kg.init(secureRandom);
            return kg.generateKey();
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }
}
