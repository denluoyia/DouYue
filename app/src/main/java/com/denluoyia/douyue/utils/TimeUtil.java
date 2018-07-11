package com.denluoyia.douyue.utils;

import android.content.Context;

import com.denluoyia.douyue.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class TimeUtil {

    public static final long MINUTE_MILLIS = 60 * 1000;
    public static final long HOUR_MILLIS = 60 * 60 * 1000;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    public static final long MONTH_MILLIS = 30 * DAY_MILLIS;
    public static final long YEAR_MILLIS = 365 * DAY_MILLIS;
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm" );
    public static final SimpleDateFormat DATE_FORMAT_DATE_1 = new SimpleDateFormat(" HH : mm ");


    public static String getTime(long timeMillis, SimpleDateFormat dateFormat){
        return dateFormat.format(new Date(timeMillis));
    }


    public static String getTime(long timeMillis){
        return getTime(timeMillis, DEFAULT_DATE_FORMAT);
    }


    //从大到小进行判断
    public static String intervalTime(long timeMillis, long nowMillis, Context context){
        if (context == null) return "";
        long diff = nowMillis - timeMillis;

        if (diff > YEAR_MILLIS){
            int year = (int) (diff / YEAR_MILLIS);
            return context.getString(R.string.before_year, year);
        }
        if (diff > MONTH_MILLIS){
            int month = (int) (diff / MONTH_MILLIS);
            return context.getString(R.string.before_month, month);
        }

        if (diff > DAY_MILLIS){
            int day = (int) (diff / DAY_MILLIS);
            return context.getString(R.string.before_day, day);
        }

        if (diff > HOUR_MILLIS){
            int hour = (int) (diff / HOUR_MILLIS);
            return context.getString(R.string.before_hour, hour);
        }

        if (diff > MINUTE_MILLIS){
            int minute = (int) (diff / MINUTE_MILLIS);
            return context.getString(R.string.before_minute, minute);
        }

        return "刚刚";
    }

    /**
     *获取当前系统时间 单位 毫秒
     */
    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     *获取当前的时间(用字符串形式)
     */
    public static String getCurrentTimeInString(){
        return getTime(getCurrentTime());
    }

    /**
     * 自定义格式进行获取时间显示
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat){
        return getTime(getCurrentTime(), dateFormat);
    }

    public static String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String formatPlayTime(int milliseconds){
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        return timeFormat.format(milliseconds);
    }
}
