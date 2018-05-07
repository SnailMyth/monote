package com.myth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterUtil {
    public static Object String2Other(String res, String typeName) throws ParseException {
        if (typeName.equals("String")){
            return res;
        }

        int i;
        long l;
        double d;
        boolean b;
        byte aByte;
        short aShort;
        Float aFloat;
        if (typeName.equals("Integer") || typeName.equals("int")) {
            i = Integer.parseInt(res);
            return i;
        }
        if (typeName.equals("Long") || typeName.equals("long")) {
            l = Long.valueOf(res);
            return l;
        }
        if (typeName.equals("Double") || typeName.equals("double")) {
            d = Double.valueOf(res);
            return d;
        }
        if (typeName.equals("Boolean") || typeName.equals("boolean")) {
            b = Boolean.valueOf(res);
            return b;
        }
        if (typeName.equals("Byte") || typeName.equals("byte")) {
            aByte = Byte.valueOf(res);
            return aByte;
        }
        if (typeName.equals("Short") || typeName.equals("short")) {
            aShort = Short.valueOf(res);
            return aShort;
        }
        if (typeName.equals("Float") || typeName.equals("float")) {
            aFloat = Float.valueOf(res);
            return aFloat;
        }

        if (typeName.equals("java.util.Date")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(res);
            return date;
        }
        return null;
    }


}
