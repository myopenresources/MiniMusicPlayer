package com.cjh.util;

/**
 * 歌词的结构体
 * 
 */
public class LRCSentence implements Comparable<LRCSentence> {

    private int time = 0;
    private String lrcString = "";

    public LRCSentence() {
    }

    public LRCSentence(int time, String lrcString) {
        this.time = time;
        this.lrcString = lrcString;
    }

    public String getLrcString() {
        return lrcString;
    }

    public void setLrcString(String lrcString) {
        this.lrcString = lrcString;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int compareTo(LRCSentence lrcs) {
        if (this.time > lrcs.time) {
            return 1;
        } else if (this.time < lrcs.time) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "时间 "+ this.time + "；歌词：" + this.lrcString + "\n";
    }

}
