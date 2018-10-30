package com.cjh.ui;

import java.io.File;
import java.util.UUID;

import javax.swing.JFileChooser;

import com.cjh.util.PhotoCompression;
/**
 * ���Ƥ��ѡ���
 * @author cjh
 *
 */
public class AddSkinFileChooser {
	
	

	/**
	 * ���췽��
	 */
	public AddSkinFileChooser(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		int selectState = 0;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("ѡ��ͼƬ");
        chooser.setApproveButtonText("ȷ��");
        chooser.setApproveButtonToolTipText("ѡ��ͼƬ");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//ֻѡ���ļ�
        selectState = chooser.showSaveDialog(null);
        if (selectState == JFileChooser.APPROVE_OPTION) {
            	//ѡ����
            	File[] skinFiles = chooser.getSelectedFiles();
            	for(File skinFile : skinFiles){
            		 String name = skinFile.toString();
            		 if ( name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg")  ) {
            		  String fileNmae = UUID.randomUUID().toString();
            		  PhotoCompression.photoAllCompression(skinFile, "images/theme/"+fileNmae+".png", 750, 500);
            		 }
            	}
               
        }
	}
	
}
