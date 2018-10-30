package com.cjh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件上传
 * @author cjh
 *
 */
public class FileUpload {

	/**
	 * 文件上传
	 * @param file
	 * @param outDir
	 * @param fileName
	 */
	public static void fileUpload(File file,String tempFilePath,String fileName){
		InputStream is = null;
		OutputStream os = null;
		try {

			is = new FileInputStream(file);// 获得输入流

			File destPath = new File(tempFilePath);// 目标目录
			if(!destPath.exists()){
				destPath.mkdirs();
			}
			
			File destFile = new File(tempFilePath+fileName);// 目标文件

			os = new FileOutputStream(destFile);// 获得输出流

			byte[] buffer = new byte[512];// 字节数组

			int length = 0;

			while (-1 != (length = is.read(buffer))) {// 读到数组中
				os.write(buffer, 0, length);// 写到输出流中
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
