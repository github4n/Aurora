package me.aurora.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 郑杰
 * @date 2018/08/23 11:54:10
 */
public class EmailUtil {

    public static boolean isEmail(String string) {
        if (string == null){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return string.matches(regEx1);
    }
}
