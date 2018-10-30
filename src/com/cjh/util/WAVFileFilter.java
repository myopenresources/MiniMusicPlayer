
package com.cjh.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * WAV�ļ�������
 * @author cjh
 */
public class WAVFileFilter extends FileFilter {

    public String getDescription() {
        return "*.wav(�����ļ�)";
    }

    public boolean accept(File file) {
        String name = file.getName();
        if (name.toLowerCase().endsWith(".wav") || !name.toLowerCase().contains(".")) {
            return true;
        } else {
            return false;
        }
    }
}
