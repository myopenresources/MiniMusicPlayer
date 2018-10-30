package com.cjh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * �ļ��ϴ�
 * @author cjh
 *
 */
public class FileUpload {

	/**
	 * �ļ��ϴ�
	 * @param file
	 * @param outDir
	 * @param fileName
	 */
	public static void fileUpload(File file,String tempFilePath,String fileName){
		InputStream is = null;
		OutputStream os = null;
		try {

			is = new FileInputStream(file);// ���������

			File destPath = new File(tempFilePath);// Ŀ��Ŀ¼
			if(!destPath.exists()){
				destPath.mkdirs();
			}
			
			File destFile = new File(tempFilePath+fileName);// Ŀ���ļ�

			os = new FileOutputStream(destFile);// ��������

			byte[] buffer = new byte[512];// �ֽ�����

			int length = 0;

			while (-1 != (length = is.read(buffer))) {// ����������
				os.write(buffer, 0, length);// д���������
			}

			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
