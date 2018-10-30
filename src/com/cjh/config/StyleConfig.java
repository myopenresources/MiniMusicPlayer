package com.cjh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 样式
 * @author cjh
 *
 */
public class StyleConfig {

	/**
	 * properties类
	 */
	private Properties pop = null;
	
	private  List<Properties> listPop = new ArrayList<Properties>();
	
	private static StyleConfig styleConfig = new StyleConfig();
	
	
	private StyleConfig(){
		init();
	}
	
	/**
	 * 初始化方法
	 */
	private void init() {
		
		for(int i=0;i<10;i++){
			File file = new File("style/style0"+(i+1)+".properties");
			if (!file.exists()) {
				System.out.println("样式配置文件丢失！");
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
				listPop.add(pop);
			} catch (FileNotFoundException e) {
				System.out.println("样式配置文件没有找到！");
			} catch (IOException e) {
				System.out.println("IO异常！");
			}
		}

	}
	
	
	/**
	 * 获得列表
	 * @return
	 */
	public List<Properties> getListPop() {
		return listPop;
	}

	
	/**
	 * 获得实例 
	 * @return
	 */
	public static StyleConfig getInstance(){
		if(null==styleConfig){
			System.out.println("初始化样式属性类出错！");
			return null;
		}
		
		return styleConfig;
	}
	

}
