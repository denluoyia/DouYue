package com.denluoyia.douyue.utils;

import android.text.TextUtils;

import java.util.UUID;

/**
 * Created by denluoyia
 * Date 2018/07/03
 * DouYue
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str) || "".equals(str) || "null".equals(str);
    }

    /**
     * 生成随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * unicodeToUTF_8
     */
    public static String unicodeToUTF_8(String src) {
        if(TextUtils.isEmpty(src)) return src;
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if(i+6 < src.length() && c == '\\' && src.charAt(i+1) == 'u') {
                String hex = src.substring(i+2, i+6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch(NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i+6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}
