package com.cjh.config;

import java.io.File;
import java.util.ArrayList;


/**
 * 皮肤有列表读写
 * @author cjh
 *
 */
public class SkinListConfig {

	/**
	 * 单例
	 */
	private static SkinListConfig skinListConfig = new  SkinListConfig();
	
	
	/**
	 * 无参构造方法
	 */
	private SkinListConfig(){
		
	}
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static SkinListConfig getInstance(){
		if(null==skinListConfig){
			System.out.println("皮肤列表读写初始化出错！");
			return null;
		}else{
			return skinListConfig;
		}
	}
	
	/* 读取皮肤列表
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
			System.out.println("读取皮肤路径出错！");
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
	 * 读取皮肤列表
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
				System.out.println("images/theme目录不存在！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取皮肤路径出错！");
		}
		return skinList;
	}
}
