package com.yzy.demo.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");

    private static final SimpleDateFormat yyyymm = new SimpleDateFormat("yyyyMM");

    private static final SimpleDateFormat yyyymmdd_hh = new SimpleDateFormat("yyyyMMdd_HH");

    private static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 将字符串类型转换为Date类型
     * @param str 字符串格式yyyyMMddHHmmss
     * @return 转换后的Date对象
     * @throws ParseException
     */
    public static Date string2Date(String str) throws ParseException {
        return sdf.parse(str);
    }

    /**
     * 将Date类型转换为字符串类型
     * @param date
     * @return 格式yyyy-MM-dd
     */
    public static String date2String(Date date) {
        return sdf2.format(date);
    }

    /**
     * 将Date类型转换为字符串类型
     * @param date
     * @return 格式yyyy-MM-dd HH:mm:ss
     */
    public static String datetime2String(Date date) {
        return sdf.format(date);
    }

    /**
     * 将Date类型转换为字符串类型
     * Author：yruidong
     * 2013-12-19 下午04:50:36
     * @param date
     * @return
     * @return String 格式yyyyMMddHHmmss
     */
    public static String date2LongStr(Date date){
        return yyyymmddhhmmss.format(date);
    }
    public static String timestamp2String(Timestamp stamp) {

        if (stamp == null)
            return null;

        return sdf.format(new Date(stamp.getTime()));
    }

    public static Timestamp String2Timestamp(String aString)
            throws ParseException {

        if (aString == null)
            return null;

        return new Timestamp(sdf.parse(aString).getTime());
    }

    /**
     *Description:将String类型的时间转化为Timestamp,yyyy-mm-dd
     *@param aString
     *@return Timestamp
     *@throws ParseException
     */
    public static Timestamp String2Timestampyyyy_mm_dd(String aString) throws ParseException
    {
        if (aString == null) return null;
        return new Timestamp(sdf2.parse(aString).getTime());
    }

    public static String DateToStringYYYYMMDD(Date date) {

        return yyyymmdd.format(date);
    }

    public static String DateToStringYYYYMM(Date date) {

        return yyyymm.format(date);
    }

    public static String DateToStringYYYYMMDD_HH(Date date) {

        return yyyymmdd_hh.format(date);
    }
}
