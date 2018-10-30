package com.cjh.ui;

import java.io.File;

import javax.swing.JFileChooser;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;
import com.cjh.util.FileUpload;
/**
 * 添加歌词选择框
 * @author cjh
 *
 */
public class AddLrcFileChooser {
	
	

	/**
	 * 构造方法
	 * @param m 
	 */
	public AddLrcFileChooser(MusicInfo m){
		init(m);
	}
	
	/**
	 * 初始化方法
	 */
	private void init(MusicInfo m){
		int selectState = 0;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("选择lrc歌词");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择lrc歌词");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//只选择文件
        selectState = chooser.showSaveDialog(null);
        if (selectState == JFileChooser.APPROVE_OPTION) {
            	File file = chooser.getSelectedFile();
            	
            	if(file.toString().toLowerCase().endsWith(".lrc")){
            		String fileNmae = m.getMusicPath().substring(0,m.getMusicPath().indexOf("."));
                    String tempFilePath="";
                    String tempFileName="";
                    int index=fileNmae.lastIndexOf("\\");
                    if(-1==index){
                    	index=fileNmae.lastIndexOf("/");
                    }
                    tempFilePath=fileNmae.substring(0,index)+File.separator+MusicPlayerConfig.getInstance().getPopString("lyric");
                    tempFileName=fileNmae.substring(index,fileNmae.length())+".lrc";
            		FileUpload.fileUpload(file,tempFilePath,tempFileName);
            	}
            	
               
        }
	}
	
}
