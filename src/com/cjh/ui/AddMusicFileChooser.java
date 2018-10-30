package com.cjh.ui;

import java.io.File;

import javax.swing.JFileChooser;

import com.cjh.music.MusicInfo;
import com.cjh.util.MP3FileFilter;
import com.cjh.util.WAVFileFilter;
/**
 * �������ѡ���
 * @author cjh
 *
 */
public class AddMusicFileChooser {

	/**
	 * ���췽��
	 */
	public AddMusicFileChooser(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		int selectState = 0;
        File chooserFile = null;
        JFileChooser chooser = new JFileChooser();
        MP3FileFilter mp3FileFilter = new MP3FileFilter();
        WAVFileFilter wavFileFilter = new WAVFileFilter();
        chooser.setDialogTitle("ѡ�������ļ�");
        chooser.setApproveButtonText("ȷ��");
        chooser.setApproveButtonToolTipText("ѡ�������ļ�");
        chooser.addChoosableFileFilter(wavFileFilter);
        chooser.addChoosableFileFilter(mp3FileFilter);
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//ֻѡ���ļ�
        selectState = chooser.showSaveDialog(null);
        if (selectState == JFileChooser.APPROVE_OPTION) {
            chooserFile = chooser.getSelectedFile();
            if (chooserFile.toString().toLowerCase().endsWith(".mp3")
                    || chooserFile.toString().toLowerCase().endsWith(".wav")) {
            	
            	//ѡ����
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
