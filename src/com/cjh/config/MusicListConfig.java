package com.cjh.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.cjh.music.MusicInfo;
import com.cjh.ui.EastPanel;

/**
 * �����б��д
 * @author cjh
 *
 */
public class MusicListConfig {

	/**
	 * ����
	 */
	private static MusicListConfig musicListConfig = new  MusicListConfig();
	
	
	/**
	 * �޲ι��췽��
	 */
	private MusicListConfig(){
		
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static MusicListConfig getInstance(){
		if(null==musicListConfig){
			System.out.println("�����б��д��ʼ������");
			return null;
		}else{
			return musicListConfig;
		}
	}
	
	/**
	 * д�����ֲ����б�txt
	 */
	public void  saveMusicToText() {
		FileWriter fwMusicPath = null;
		BufferedWriter bfMusicPath = null;
		Vector<MusicInfo> musicVector = EastPanel.getInstance().getMusicVector();
		try {
			File directory = new File("config/musicPath.cjh"); 
			fwMusicPath = new FileWriter(directory.getCanonicalPath());
			bfMusicPath = new BufferedWriter(fwMusicPath);
			for (MusicInfo m: musicVector) {
				bfMusicPath.write(m.getMusicPath());
				bfMusicPath.newLine();
			}
			bfMusicPath.flush();
			bfMusicPath.close();
			fwMusicPath.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("д������·������");
		}finally{
			if(null!=bfMusicPath){
				try {
					bfMusicPath.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=fwMusicPath){
				try {
					fwMusicPath.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ��ȡ���ֵ��б�
	 */
	public void readMusicToList(){
		FileReader frMusicPath = null;
		BufferedReader brMusicPath = null;
		try {
			File directory = new File(getLocationPath("config/musicPath.cjh")); 
			frMusicPath = new FileReader(directory.getCanonicalPath());
			brMusicPath = new BufferedReader(frMusicPath);
			String strTemp = null;
			File file = null;
			while ((strTemp = brMusicPath.readLine()) != null) {
				file = new File(strTemp);
				if(file.exists()){
					String path = file.getPath();
					 if (path.toLowerCase().endsWith(".mp3") || path.toLowerCase().endsWith(".wav")) {
	                	 MusicInfo musicInfo = new MusicInfo(path);
	                	 EastPanel.getInstance().addMusic(musicInfo);
	                     EastPanel.getInstance().updateMusicListUI();
	                }
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ȡ����·������");
		}finally{
			if(null!=brMusicPath){
				try {
					brMusicPath.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=frMusicPath){
				try {
					frMusicPath.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getLocationPath(String filePath){
		String jarPath=this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		return jarPath.substring(0,jarPath.lastIndexOf("/"))+File.separator+filePath;
	}
	
}
