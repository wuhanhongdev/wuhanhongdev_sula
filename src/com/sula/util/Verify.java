package com.sula.util;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verify {
    
    
    private static String mySign = "sula";
    
    public static boolean Sign(String nativeSign,String ...sign){
        sign = sort(sign);
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < sign.length;i++){
            sb.append(sign[i]);
        }
        sb.append(mySign);
        return MD5Util.encodeToMd5(sb.toString()).equals(nativeSign);
    }
    public static String[] sort(String ...sign){
        Arrays.sort(sign);
        return sign;
    }

    //^([0-9a-fA-F]{2})(([/\s-][0-9a-fA-F]{2}){5})$
    public static boolean isMac(String sign){
        Pattern macPattern = Pattern.compile("([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}");
        Matcher macMatcher = macPattern.matcher(sign);

        boolean result = macMatcher.find();

        return result;
    }
}
