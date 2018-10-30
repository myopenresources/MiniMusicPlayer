package com.cjh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * �����������ļ�
 * 
 * @author cjh
 * 
 */
public class MusicPlayerConfig {

	/**
	 * properties��
	 */
	private Properties pop = null;
	
	/**
	 * ����
	 */
	private static MusicPlayerConfig musicPlayerConfig = new MusicPlayerConfig();
	

    /**
     * ˽�й��췽��
     */
	private MusicPlayerConfig() {
		init();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		File file = new File("config/musicPlayer.properties");

		if (!file.exists()) {
			System.out.println("���ֲ����������ļ���ʧ��");
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
		} catch (FileNotFoundException e) {
			System.out.println("���ֲ����������ļ�û���ҵ���");
		} catch (IOException e) {
			System.out.println("IO�쳣��");
		}

	}
	
	/**
	 * �����������
	 * @return
	 */
	public String getPopString(String key){
		if(null!=key && !"".equals(key)  && null!=pop){
			return pop.getProperty(key);
		}else{
			System.out.println("��������ʧ�ܣ�");
			return null;
		}
	}
	
	/**
	 * ��������
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
				pop.store(fos, "�������������ļ�");
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
			System.out.println("value����ȷ��");
		}
	}
	
	/**
	 * ���ʵ�� 
	 * @return
	 */
	public static MusicPlayerConfig getInstance(){
		if(null==musicPlayerConfig){
			System.out.println("��ʼ���������������");
			return null;
		}
		
		return musicPlayerConfig;
	}
}
