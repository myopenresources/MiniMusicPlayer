package com.cjh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;
import com.cjh.music.MusicPlayer;

/**
 * 歌词解析
 */
public class LRCParse {
    private static LRCParse lrcParse = new LRCParse();
    private Set<LRCSentence> lrcStringSet = new TreeSet<LRCSentence>();//Set可以自动排序和消除重 
    private MusicInfo currentMusic = null;
    private String title = "";
    private String artist = "";
    private String album = "";
    private String by = "";
    private int offset = 0;

    private LRCParse() {
    }

    /**
     * 加载歌词
     */
    public void LoadLRC() {
        currentMusic = MusicPlayer.getCurrentMusic();
        if (currentMusic == null) {
            return;
        }
        String musicPath = currentMusic.getMusicPath();
        File parent = new File(musicPath).getParentFile();
        File lrcFile = new File(parent+File.separator+MusicPlayerConfig.getInstance().getPopString("lyric"), currentMusic.getMusicOrignName() + ".lrc");
        if (!lrcFile.exists()) {
            lrcStringSet.clear();
            return;
        }
        try {
            FileInputStream lrcIs = new FileInputStream(lrcFile);//得到歌词文件
            byte lrcByte[] = new byte[lrcIs.available()];
            lrcIs.read(lrcByte);
            lrcIs.close();
            String lrcString = new String(lrcByte);
            String lrcArray[] = lrcString.split("\r\n");
            if (lrcArray.length == 1) {
                lrcArray = lrcArray[0].split("\n");
            }
            parseLRC(lrcArray);
        } catch (IOException e) {
        }
    }

    /**
     * 解析歌词
     * @param lrcArray
     */
    private void parseLRC(String[] lrcArray) {
        lrcStringSet.clear();
        int length = lrcArray.length;
        for (int i = 0; i < length; i++) {
            if (lrcArray[i].length() == 0) {
                continue;
            }
            if (!lrcArray[i].matches("(\\[\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?\\])+(.)*(\\s)*")) {
                if (lrcArray[i].startsWith("[ti:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        title = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[ar:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        artist = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[al:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        album = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[by:")) {
                    if (lrcArray[i].length() - 2 > 4) {
                        by = lrcArray[i].substring(4, lrcArray[i].length() - 2);
                    }
                } else if (lrcArray[i].startsWith("[offset:")) {
                    if (lrcArray[i].length() - 2 > 8) {
                        offset = FormatUtils.formatStringToInt(lrcArray[i].substring(8, lrcArray[i].length() - 2));
                    }
                }
            } else {
                String tempArray[] = lrcArray[i].trim().split("]");
                int tempLength = tempArray.length;
                if (tempArray[tempLength - 1].matches("(\\[\\d{2}:\\d{2}(\\.\\d{2}(\\d)?)?)")) {
                    for (int j = 0; j < tempLength; j++) {
                        lrcStringSet.add(new LRCSentence(FormatUtils.formatTime(tempArray[j].substring(1), offset), ""));
                    }

                } else {
                    for (int j = 0; j < tempLength - 1; j++) {
                        int tempTime = FormatUtils.formatTime(tempArray[j].substring(1), offset);
                        //考虑到有些时间是�?��的，这样就把空格替换
                        LRCSentence tempLrc = new LRCSentence(tempTime, "");
                        if (lrcStringSet.contains(tempLrc)) {
                            lrcStringSet.remove(tempLrc);
                        }
                        lrcStringSet.add(new LRCSentence(tempTime, tempArray[tempLength - 1]));
                    }
                }
            }
        }
        //System.out.println(lrcStringSet);
    }

    public static LRCParse getInstance() {
        return lrcParse;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getBy() {
        return by;
    }

    public Set<LRCSentence> getLrcStringSet() {
        return lrcStringSet;
    }

    public String getTitle() {
        return title;
    }
}
