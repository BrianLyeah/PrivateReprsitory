package com.itheima.ssm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //字符串转换成日期string2Date
    public static Date string2date(String dateString,String patt) throws Exception {
        //创建一个日期对象
        DateFormat dateFormat=new SimpleDateFormat(patt);
        Date date = dateFormat.parse(dateString);
        return date;
    }

    //日期转换成字符串
    public static String date2String(Date date,String patt){
        //创建一个日期对象
        DateFormat dateFormat=new SimpleDateFormat(patt);
        String format = dateFormat.format(date);
        return format;
    }
}
