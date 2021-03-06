package com.cjh.util;

import java.awt.Color;

/**
 * 格式化类
 * @author 
 */
public class FormatUtils {

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String formatTime(double time) {
        StringBuffer sb = new StringBuffer();
        int minutes = (int) (time) / 60;
        int seconds = (int) (time) % 60;
        if (minutes >= 10) {
            sb.append(minutes);
        } else {
            sb.append("0" + minutes);
        }
        sb.append(":");
        if (seconds >= 10) {
            sb.append(seconds);
        } else {
            sb.append("0" + seconds);
        }
        return sb.toString();
    }

    /**
     * 格式化歌词的时间转化为long型
     * time的正则表达式为:(\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?)
     * @param time歌词字符串时间,例如:(00:00.00)
     * @return 时间的long型
     */
    public static int formatTime(String time, int offset) {
        try {
            int minute = Integer.valueOf(time.substring(0, 2)) * 60;
            int second = Integer.valueOf(time.substring(3, 5));
            int millisecond = 0;
            if (time.length() > 5) {
                //四舍五入
                double temp = Double.valueOf("0." + time.substring(6, time.length()));
                double tempOffset = offset * 1.0 / 1000;
                double total = temp + tempOffset;
                if (total > 0.5) {
                    millisecond = (int) total + 1;
                } else {
                    millisecond = (int) total;
                }
            }
            return minute + second + millisecond;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 将src颜色转化为带透明的颜色
     * @param src
     * @param transparency
     * @return
     */
    public static Color formatColorTransparent(Color src, int transparency) {
        Color newColor = Color.white;
        if (transparency >= 0 && transparency <= 255) {
            newColor = new Color(src.getRed(), src.getGreen(), src.getBlue(), transparency);
        }
        return newColor;
    }

    /**
     * 对double进行四舍五入
     */
    public static int roundDouble(double src) {
        int temp = (int) src;
        double decimals = src - temp;
        if (decimals > 0.5) {
            return temp + 1;
        }
        return temp;
    }

    /**
     * 将字符串转化为Int类型,考虑异常
     * @param src是表示数字的字符串
     * @return 目标整数
     */
    public static int formatStringToInt(String src) {
        int dest = 0;
        try {
            dest = Integer.parseInt(src);
        } catch (Exception e) {
        }
        return dest;
    }

}
