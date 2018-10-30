package com.cjh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 播放器配置文件
 * 
 * @author cjh
 * 
 */
public class MusicPlayerConfig {

	/**
	 * properties类
	 */
	private Properties pop = null;
	
	/**
	 * 单例
	 */
	private static MusicPlayerConfig musicPlayerConfig = new MusicPlayerConfig();
	

    /**
     * 私有构造方法
     */
	private MusicPlayerConfig() {
		init();
	}

	/**
	 * 初始化方法
	 */
	private void init() {
		File file = new File("config/musicPlayer.properties");

		if (!file.exists()) {
			System.out.println("音乐播放器配置文件丢失！");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			pop = new Properties();
			// 读取属性文件
			pop.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("音乐播放器配置文件没有找到！");
		} catch (IOException e) {
			System.out.println("IO异常！");
		}

	}
	
	/**
	 * 获得属性配置
	 * @return
	 */
	public String getPopString(String key){
		if(null!=key && !"".equals(key)  && null!=pop){
			return pop.getProperty(key);
		}else{
			System.out.println("属性配置失败！");
			return null;
		}
	}
	
	/**
	 * 设置属性
	 * @param key
	 * @param value
	 */
	public void setPopString(String key,String value){
		if(null!=value && !"".equals(value)){
			pop.setProperty(key, value);
			FileOutputStream fos=null;
			try {
				 File directory = new File("config/musicPlayer.properties"); 
				 fos= new FileOutputStream(directory.getCanonicalPath()); 
				pop.store(fos, "爱听音乐配置文件");
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null!=fos){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			System.out.println("value不正确！");
		}
	}
	
	/**
	 * 获得实例 
	 * @return
	 */
	public static MusicPlayerConfig getInstance(){
		if(null==musicPlayerConfig){
			System.out.println("初始化音乐属性类出错！");
			return null;
		}
		
		return musicPlayerConfig;
	}
}
