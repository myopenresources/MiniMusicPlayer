package com.cjh.ui;

import java.io.File;

import javax.swing.JFileChooser;

import com.cjh.music.MusicInfo;


/**
 * ���Ŀ¼����ѡ���
 * @author cjh
 *
 */
public class AddMuiscPathChooser {

	
	public AddMuiscPathChooser(){
		init();
	}
	
	private void init(){
		 int selectState = 0;
	        File chooserFolder = null;
	        JFileChooser chooser = new JFileChooser();
	        chooser.setDialogTitle("ѡ�������ļ���");
	        chooser.setApproveButtonText("ȷ��");
	        chooser.setApproveButtonToolTipText("ѡ�������ļ���");
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//ֻѡ���ļ���
	        selectState = chooser.showOpenDialog(null);
	        if (selectState == JFileChooser.APPROVE_OPTION) {
	            chooserFolder = chooser.getSelectedFile();
	            String musicPath[] = chooserFolder.list();
	            
	            for (String p: musicPath) {
	                if (p.toLowerCase().endsWith(".mp3") || p.toLowerCase().endsWith(".wav")) {
	                	 MusicInfo musicInfo = new MusicInfo(chooserFolder + File.separator + p);
	                	 EastPanel.getInstance().addMusic(musicInfo);
	                     EastPanel.getInstance().updateMusicListUI();
	                }
	            }
	        }
	}
}
