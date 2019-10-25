package com.yyl.common;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtily {

	public static void createPreviewImage(String srcFile, String destFile) 
	{
		final int IMAGE_SIZE = 120;
		
		        try {   
		            File fi = new File(srcFile); // src   
		            File fo = new File(destFile); // dest   
		            BufferedImage bis = ImageIO.read(fi);   
		  
		            int w = bis.getWidth();   
		            int h = bis.getHeight();   
		            
		            int nw = IMAGE_SIZE; // final int IMAGE_SIZE = 120;   
		            int nh = (nw * h) / w;   
		            if (nh > IMAGE_SIZE) {   
		                nh = IMAGE_SIZE;   
		                nw = (nh * w) / h;   
		            }   
		            BufferedImage bid = new BufferedImage(nw, nh,   
		                    BufferedImage.TYPE_3BYTE_BGR);   
		            ImageIO.write(bid, " jpeg ", fo);   
		        } catch (Exception e) {   
		            e.printStackTrace();   
		            throw new RuntimeException(   
		                    " Failed in create preview image. Error:  "  
		                            + e.getMessage());   
		        }   
		    }  

}
