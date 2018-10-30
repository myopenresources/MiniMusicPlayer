package com.cjh.util;

import java.awt.Color;

/**
 * ��ʽ����
 * @author 
 */
public class FormatUtils {

    /**
     * ��ʽ��ʱ��
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
     * ��ʽ����ʵ�ʱ��ת��Ϊlong��
     * time��������ʽΪ:(\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?)
     * @param time����ַ���ʱ��,����:(00:00.00)
     * @return ʱ���long��
     */
    public static int formatTime(String time, int offset) {
        try {
            int minute = Integer.valueOf(time.substring(0, 2)) * 60;
            int second = Integer.valueOf(time.substring(3, 5));
            int millisecond = 0;
            if (time.length() > 5) {
                //��������
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
     * ��src��ɫת��Ϊ��͸������ɫ
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
     * ��double������������
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
     * ���ַ���ת��ΪInt����,�����쳣
     * @param src�Ǳ�ʾ���ֵ��ַ���
     * @return Ŀ������
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
