package com.cjh.config;

import java.io.File;
import java.util.ArrayList;


/**
 * Ƥ�����б��д
 * @author cjh
 *
 */
public class SkinListConfig {

	/**
	 * ����
	 */
	private static SkinListConfig skinListConfig = new  SkinListConfig();
	
	
	/**
	 * �޲ι��췽��
	 */
	private SkinListConfig(){
		
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static SkinListConfig getInstance(){
		if(null==skinListConfig){
			System.out.println("Ƥ���б��д��ʼ������");
			return null;
		}else{
			return skinListConfig;
		}
	}
	
	/* ��ȡƤ���б�
	public ArrayList<String> readSkinToList(){
		ArrayList<String> skinList = new ArrayList<String>();
		FileReader frMusicPath = null;
		BufferedReader brMusicPath = null;
		try {
			frMusicPath = new FileReader("skin.cjh");
			brMusicPath = new BufferedReader(frMusicPath);
			String strTemp = null;
			File file = null;
			while ((strTemp = brMusicPath.readLine()) != null) {
				file = new File(strTemp);
				if(file.exists()){
					String path = file.getPath();
					 if (path.toLowerCase().endsWith(".png") || path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg")) {
						 skinList.add(path);
	                }
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ȡƤ��·������");
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
		return skinList;
	} */
	
	/**
	 * ��ȡƤ���б�
	 */
	public ArrayList<String> readSkinToList(){
		ArrayList<String> skinList = new ArrayList<String>();
		try {
			String root = "images/theme";
			File skinFile = new File(root);
			if(skinFile.exists()){
				  String [] skins = skinFile.list();
				  for(String path: skins){
					  if (path.toLowerCase().endsWith(".png") || path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg")) {
							 skinList.add(root+"/"+path);
		              }
				  }
			}else{
				System.out.println("images/themeĿ¼�����ڣ�");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ȡƤ��·������");
		}
		return skinList;
	}
}
