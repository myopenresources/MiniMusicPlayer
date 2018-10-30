package com.cjh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ��ʽ
 * @author cjh
 *
 */
public class StyleConfig {

	/**
	 * properties��
	 */
	private Properties pop = null;
	
	private  List<Properties> listPop = new ArrayList<Properties>();
	
	private static StyleConfig styleConfig = new StyleConfig();
	
	
	private StyleConfig(){
		init();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init() {
		
		for(int i=0;i<10;i++){
			File file = new File("style/style0"+(i+1)+".properties");
			if (!file.exists()) {
				System.out.println("��ʽ�����ļ���ʧ��");
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			try {
				pop = new Properties();
				// ��ȡ�����ļ�
				pop.load(new FileInputStream(file));
				listPop.add(pop);
			} catch (FileNotFoundException e) {
				System.out.println("��ʽ�����ļ�û���ҵ���");
			} catch (IOException e) {
				System.out.println("IO�쳣��");
			}
		}

	}
	
	
	/**
	 * ����б�
	 * @return
	 */
	public List<Properties> getListPop() {
		return listPop;
	}

	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static StyleConfig getInstance(){
		if(null==styleConfig){
			System.out.println("��ʼ����ʽ���������");
			return null;
		}
		
		return styleConfig;
	}
	

}
