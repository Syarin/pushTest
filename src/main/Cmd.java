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
		//����ͼƬ��С
		case "1": System.out.println("1");
		break;
		//����ͼƬ������Աȶ�
		case "2": System.out.println("2");
		break;
		//ͼƬ������Ч
		case "3": src = iu.effect_mirror(src);
		break;
		//ͼƬ����
		case "4": System.out.println("4");
		break;
		// ͼƬ�Ҷȴ���
		case "5": src = iu.effect_black_white(src);
		break;
		//ͼƬ��ת		
		case "6": System.out.println("6");
		break;
		}
		
		
		try {
			ImageIO.write(src, "JPEG", new File("E:\\DM\\ImageTool\\img\\result.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ������ļ���
		System.out.print("success!");
	}

}
