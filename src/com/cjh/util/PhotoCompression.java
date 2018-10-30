package com.cjh.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Í¼Æ¬Ñ¹Ëõ
 * @author cjh
 *
 */
public class PhotoCompression {

	
	/**
	 * Ñ¹ËõÍ¼Æ¬(ÎÞÂÛ´óÐ¡È«Ñ¹Ëõ)
	 * @param in
	 * @param outputDir
	 * @param width
	 * @param height
	 * @return
	 */
	public static int photoAllCompression(File file,String outputDir,int width,int height){
		try{
			//¶ÁÈ¡Í¼Æ¬
			Image img = ImageIO.read(file);
			create_img(width,height,img,outputDir);
			return 1;
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		
	}
	

	
	/**
	 * ´´½¨Í¼Æ¬
	 * @param width
	 * @param height
	 * @param img
	 * @param outDir
	 * @return
	 */
	private static int create_img(int width,int height,Image img,String outDir){
		try{
			if(null!=img){
			 BufferedImage bufferImg = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);  
			  bufferImg.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			  ImageIO.write(bufferImg, "PNG", new File(outDir));
			  return 1;
			}else{
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	

}
