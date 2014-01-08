package tool;

import java.awt.image.BufferedImage;


public class ImgUtil {
	public static final int TURN_LEFT = 1;
	public static final int TURN_RIGHT = 2;

	/**
	 * ��ȡBufferedImage RGB���ݣ������ش�СΪwidth*height��С��һά����
	 */
	public int[] getPixels(BufferedImage src) {
		int w = src.getWidth();
		int h = src.getHeight();
		int[] pixels = new int[w * h];
		/**
		 * public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) 
		 * pixel = rgbArray[offset + (y-startY)*scansize + (x-startX)];
		 */
		src.getRGB(0, 0, w, h, pixels, 0, w);
		return pixels;
	}

	/**
	 * ��pixels[]������ݣ�д��BufferedImage��ͼƬ��Ϊw����Ϊh
	 */
	public BufferedImage drawPixels(BufferedImage src, int[] pixels, int w,
			int h) {
		/**
		 * public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) 
		 * pixel = rgbArray[offset + (y-startY)*scansize + (x-startX)];
		 */
		src.setRGB(0, 0, w, h, pixels, 0, w);
		pixels = null;
		return src;
	}

	/**
	 * ����ͼƬ��СdestW ������Ŀ�destH������ĸ�
	 */
	public BufferedImage effect_resizeImage(BufferedImage src, int destW, int destH) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();

		int[] destPixels = new int[destW * destH];

		int[] srcPixels = getPixels(src);
		for (int destY = 0; destY < destH; ++destY) {
			for (int destX = 0; destX < destW; ++destX) {
				int srcX = (destX * srcW) / destW;
				int srcY = (destY * srcH) / destH;
				destPixels[destX + destY * destW] = srcPixels[srcX + srcY
						* srcW];
			}
		}
		return drawPixels(src, destPixels, destW, destH);
	}

	/**
	 * ����ͼƬ������Աȶȡ� contrast �Աȶȣ�light ����
	 */
	public BufferedImage effect_light_contrast(BufferedImage src, double contrast, int light) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		// ��ʽy =ax+b aΪ�Աȶȣ�bΪ����
		// int para_b = light - 127 * (light - 1);
		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				r = (int) (r * contrast + light);
				g = (int) (g * contrast + light);
				b = (int) (b * contrast + light);

				/*
				 * r =(int)((r -127 ) * contrast + 127+para_b); g =(int)((g -127
				 * ) * contrast + 127+para_b); b =(int)((b -127 ) * contrast +
				 * 127+para_b);
				 */
				if (r > 255)
					r = 255;
				else if (r < 0)
					r = 0;
				if (g > 255)
					g = 255;
				else if (g < 0)
					g = 0;
				if (b > 255)
					b = 255;
				else if (b < 0)
					b = 0;
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);

			}
		}
		return drawPixels(src, srcPixels, srcW, srcH);
	}

	/**
	 * ͼƬ������Ч
	 */
	public BufferedImage effect_mirror(BufferedImage src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int len;
		int temp;
		for (int i = 0; i < srcH; i++) {
			len = (i + 1) * srcW;
			for (int ii = 0; ii < srcW / 2; ii++) {
				temp = srcPixels[i * srcW + ii];
				srcPixels[i * srcW + ii] = srcPixels[len - 1 - ii];
				srcPixels[len - 1 - ii] = temp;
			}
		}
		return drawPixels(src, srcPixels, srcW, srcH);
	}

	/**
	 * ͼƬ���У�cut_xpos��cut_ypos �и�����ʼλ�����꣬cut_width��cut_height �и��Ŀ����
	 */
	public BufferedImage effect_cut(BufferedImage src, int cut_xpos, int cut_ypos,
			int cut_width, int cut_height) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int[] desPixels = new int[cut_width * cut_height];
		//int argb;
		int num = 0;
		for (int i = 0; i < srcH; i++) {
			if (i >= cut_ypos && i < cut_height + cut_ypos) {
				for (int ii = 0; ii < srcW; ii++) {
					if (ii >= cut_xpos && ii < cut_width + cut_xpos) {
						desPixels[num] = srcPixels[i * srcW + ii];
						num++;

					}
				}
			}
		}
		return drawPixels(src, desPixels, cut_width, cut_height);
	}

	/**
	 * ͼƬ�Ҷȴ����Ȩƽ��ֵ�������µ���ɫֵR��G��B��(R �� Wr��G��Wg��B��Wb)��һ���������۶Բ�ͬ��ɫ�����жȲ�һ��������������ɫֵ��Ȩ�ز�һ����һ����˵��ɫ��ߣ���ɫ��Σ���ɫ��ͣ�������ȡֵ�ֱ�ΪWr �� 30����Wg �� 59����Wb �� 11��
	 */
	public BufferedImage effect_black_white(BufferedImage src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		int temp;

		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				temp = (int) (.299 * (double) r + .587 * (double) g + .114 * (double) b);
				r = temp;
				g = temp;
				b = temp;
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(src, srcPixels, srcW, srcH);

	}

	/**
	 * @param imgSource
	 *            Դͼ��
	 * @param cx
	 *            ��ת�������Դͼ�����ϽǺ�����
	 * @param cy
	 *            ��ת�������Դͼ�����Ͻ�������
	 * @param theta
	 *            ͼ����ʱ����ת�ĽǶ�
	 * @return ��ת���ͼ��
	 */
	public BufferedImage rotate(BufferedImage imgSource, int cx, int cy, double theta) {
		if (Math.abs(theta % 360) < 1)
			return imgSource; // �ǶȺ�Сʱֱ�ӷ���

		int w1 = imgSource.getWidth(); // ԭʼͼ��ĸ߶ȺͿ��

		int h1 = imgSource.getHeight();

		int[] srcMap = new int[w1 * h1];

		srcMap = getPixels(imgSource); // ��ȡԭʼͼ���������Ϣ

		int dx = cx > w1 / 2 ? cx : w1 - cx; // ������ת�뾶

		int dy = cy > h1 / 2 ? cy : h1 - cy;

		double dr = Math.sqrt(dx * dx + dy * dy);

		int wh2 = (int) (2 * dr + 1); // ��ת����ͼ��Ϊ�����Σ���߳�+1��Ϊ�˷�ֹ����Խ��

		int[] destMap = new int[wh2 * wh2]; // �����ͼ�����ص�����

		double destX, destY;

		double radian = theta * Math.PI / 180; // ����Ƕȼ����Ӧ�Ļ���ֵ

		for (int i = 0; i < w1; i++) {
			for (int j = 0; j < h1; j++) {
				if (srcMap[j * w1 + i] >> 24 != 0) { // �Է�͸����Ž��д���
					// �õ���ǰ�㾭��ת���������ͼ�����Ͻǵ�����
					destX = dr + (i - cx) * Math.cos(radian) + (j - cy)
							* Math.sin(radian);
					destY = dr + (j - cy) * Math.cos(radian) - (i - cx)
							* Math.sin(radian);
					// ��Դͼ��������ͼ�����������
					destMap[(int) destY * wh2 + (int) destX] = srcMap[j * w1
							+ i];
				}
			}
		}
		return drawPixels(imgSource, srcMap, wh2, wh2); // ������ת���ͼ��
	}
	
}
