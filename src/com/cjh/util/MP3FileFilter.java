package com.cjh.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * MP3�ļ�������
 * @author cjh
 */
public class MP3FileFilter extends FileFilter {

    public String getDescription() {
        return "*.mp3(�����ļ�)";
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
