package com.cjh.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 快照
 * 
 * @author cjh
 * 
 */
public class SnapShot {

    /**
     * 	创建快照
     * @param r
     * @param path
     * @param fileName
     * @param imageFormat
     */
	public static  boolean  createSnapShot(Rectangle r,String path,String fileName ,String imageFormat) {
		try {
			
			BufferedImage screenshot = (new Robot())
			.createScreenCapture(r);

			String name =path+ fileName + "." + imageFormat;
			File f = new File(name);
			
			ImageIO.write(screenshot,imageFormat, f);
			
			return true;
			
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
}