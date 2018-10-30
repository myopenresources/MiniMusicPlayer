package com.cjh.ui;

import java.io.File;

import javax.swing.JFileChooser;

import com.cjh.music.MusicInfo;
import com.cjh.util.MP3FileFilter;
import com.cjh.util.WAVFileFilter;
/**
 * 添加音乐选择框
 * @author cjh
 *
 */
public class AddMusicFileChooser {

	/**
	 * 构造方法
	 */
	public AddMusicFileChooser(){
		init();
	}
	
	/**
	 * 初始化方法
	 */
	private void init(){
		int selectState = 0;
        File chooserFile = null;
        JFileChooser chooser = new JFileChooser();
        MP3FileFilter mp3FileFilter = new MP3FileFilter();
        WAVFileFilter wavFileFilter = new WAVFileFilter();
        chooser.setDialogTitle("选择音乐文件");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择音乐文件");
        chooser.addChoosableFileFilter(wavFileFilter);
        chooser.addChoosableFileFilter(mp3FileFilter);
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//只选择文件
        selectState = chooser.showSaveDialog(null);
        if (selectState == JFileChooser.APPROVE_OPTION) {
            chooserFile = chooser.getSelectedFile();
            if (chooserFile.toString().toLowerCase().endsWith(".mp3")
                    || chooserFile.toString().toLowerCase().endsWith(".wav")) {
            	
            	//选择多个
            	File[] musicFiles = chooser.getSelectedFiles();
            	for(File musicFile : musicFiles){
            		 MusicInfo musicInfo = new MusicInfo(musicFile.toString());
                     EastPanel.getInstance().addMusic(musicInfo);
                     EastPanel.getInstance().updateMusicListUI();
            	}
               
            }
        }
	}
	
}
