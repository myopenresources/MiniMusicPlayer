package com.cjh.ui;

import java.io.File;
import java.util.UUID;

import javax.swing.JFileChooser;

import com.cjh.util.PhotoCompression;
/**
 * 添加皮肤选择框
 * @author cjh
 *
 */
public class AddSkinFileChooser {
	
	

	/**
	 * 构造方法
	 */
	public AddSkinFileChooser(){
		init();
	}
	
	/**
	 * 初始化方法
	 */
	private void init(){
		int selectState = 0;
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("选择图片");
        chooser.setApproveButtonText("确定");
        chooser.setApproveButtonToolTipText("选择图片");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//只选择文件
        selectState = chooser.showSaveDialog(null);
        if (selectState == JFileChooser.APPROVE_OPTION) {
            	//选择多个
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
