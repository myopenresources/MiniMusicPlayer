package com.cjh.ui;

import java.io.File;

import javax.swing.JFileChooser;

import com.cjh.config.MusicPlayerConfig;
import com.cjh.music.MusicInfo;
import com.cjh.util.FileUpload;
/**
 * ��Ӹ��ѡ���
 * @author cjh
 *
 */
public class AddLrcFileChooser {
	
	

	/**
	 * ���췽��
	 * @param m 
	 */
	public AddLrcFileChooser(MusicInfo m){
		init(m);
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(MusicInfo m){
		int selectState = 0;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("ѡ��lrc���");
        chooser.setApproveButtonText("ȷ��");
        chooser.setApproveButtonToolTipText("ѡ��lrc���");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//ֻѡ���ļ�
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
