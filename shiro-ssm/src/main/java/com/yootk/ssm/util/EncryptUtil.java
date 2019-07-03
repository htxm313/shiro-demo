package com.yootk.ssm.util;

public class EncryptUtil {
    private static final int REPEAT = 6 ; // 加密次数
    public static String encode(String password) {
        String newPass = EncryptSalt.encrypt(password) ; // Base64加密
        MD5Code md5 = new MD5Code() ; // 获取MD5加密对象实例
        for (int x = 0 ; x < REPEAT ; x ++) {
            newPass = md5.getMD5ofStr(newPass) ;
        }
        return newPass ;
    }
}
