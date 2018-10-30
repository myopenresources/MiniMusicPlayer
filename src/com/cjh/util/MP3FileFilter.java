package com.cjh.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * MP3文件过滤器
 * @author cjh
 */
public class MP3FileFilter extends FileFilter {

    public String getDescription() {
        return "*.mp3(音乐文件)";
    }

    public boolean accept(File file) {
        String name = file.getName();
        if (name.toLowerCase().endsWith(".mp3") || !name.toLowerCase().contains(".")) {
            return true;
        } else {
            return false;
        }
    }
    
}
