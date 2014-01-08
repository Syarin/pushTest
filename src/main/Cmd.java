package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import tool.ImgUtil;

public class Cmd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String func = "5";
		String srcImageFile = "E:\\DM\\ImageTool\\img\\src.jpg";
		String w = "";
		String h = "";
		ImgUtil iu = new ImgUtil();
		BufferedImage src = null;
		try {
			src = ImageIO.read(new File(srcImageFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(func)

		{
		//调整图片大小
		case "1": System.out.println("1");
		break;
		//调整图片亮度与对比度
		case "2": System.out.println("2");
		break;
		//图片反相特效
		case "3": src = iu.effect_mirror(src);
		break;
		//图片剪切
		case "4": System.out.println("4");
		break;
		// 图片灰度处理
		case "5": src = iu.effect_black_white(src);
		break;
		//图片旋转		
		case "6": System.out.println("6");
		break;
		}
		
		
		try {
			ImageIO.write(src, "JPEG", new File("E:\\DM\\ImageTool\\img\\result.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 输出到文件流
		System.out.print("success!");
	}

}
