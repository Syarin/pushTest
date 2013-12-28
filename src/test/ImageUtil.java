package test ;
    


import javax.microedition.lcdui.*;  
//import javax.microedition.lcdui.game.Sprite;  
    
     public class ImageUtil  
     {  
         public static final int TURN_LEFT = 1;  
         public static final int TURN_RIGHT = 2;  
    
         /*
         *��ȡͼƬRGB���ݣ������ش�СΪwidth*height��С��һά����
         */
         public	int[] getPixels(Image src)  
         {  
             int w = src.getWidth();  
             int h = src.getHeight();  
             int[] pixels = new
             int[w * h];  
             src.getRGB(pixels, 0, w, 0, 0, w, h);  
             return pixels;  
         }  
    

    


         /*
         *��pixels[]������ݣ�����һ��ͼƬ��ͼƬ��Ϊw����Ϊh
         */
         public Image drawPixels(int[] pixels, int w, int h)  
         {  
             Image image = Image.createRGBImage(pixels, w, h, true);  
             pixels = null;  
             return image;  
         }  
    

    


         /*
         *����ͼƬ��С
         *destW ������Ŀ�destH������ĸ�
         */
         public  Image effect_resizeImage(Image src, int destW, int destH)  
         {  
             int srcW = src.getWidth();  
             int srcH = src.getHeight();  

             int[] destPixels = new
             int[destW * destH];   
    
             int[] srcPixels = getPixels(src);   
                 for (int destY = 0; destY < destH; ++destY)  
                 {  
                     for (int destX = 0; destX < destW; ++destX)  
                     {  
                         int srcX = (destX * srcW) / destW;  
                         int srcY = (destY * srcH) / destH;  
                         destPixels[destX + destY * destW] = srcPixels[srcX + srcY  
                                 * srcW];  
                     }  
                 }  
             return drawPixels(destPixels, destW, destH);  
         }  
    

    


        /*
        * ����ͼƬ������Աȶȡ�  contrast �Աȶȣ�light ����
        */
        public Image effect_light_contrast(Image src,double contrast,int light)  
        {  
                 int srcW = src.getWidth();  
                 int srcH = src.getHeight();   
                 int[] srcPixels = getPixels(src);  
                 int r = 0;  
                 int g = 0;  
                 int b = 0;  
                 int a = 0;  
                 int argb;  
          //��ʽy =ax+b   aΪ�Աȶȣ�bΪ����
          //int para_b  = light - 127 * (light - 1);
          for (int i = 0; i < srcH; i++)  {  
                   for(int ii=0;ii<srcW;ii++)  
                   {  
                           argb = srcPixels[i*srcW+ii];   
                           a = ((argb & 0xff000000) >> 24); // alpha channel
                           r =((argb & 0x00ff0000) >> 16); // red channel
                           g =((argb & 0x0000ff00) >> 8); // green channel
                           b =(argb & 0x000000ff); // blue channel
                           r =(int)( r*contrast+light);                       
                           g =(int)( g*contrast+light);  
                           b =(int)( b*contrast+light);  
    

              /*r =(int)((r -127 ) * contrast + 127+para_b);
              g =(int)((g -127 ) * contrast + 127+para_b);
              b =(int)((b -127 ) * contrast + 127+para_b);*/
              if(r>255)  
                                    r = 255;  
                            else
                            	if(r<0) r=0;  
                            if(g>255)  
                                    g = 255;  
                            else
                            	if(g<0) g=0;  
                            if(b>255)  
                                    b = 255;  
                            else
                            	if(b<0) b=0;  
                    srcPixels[i*srcW+ii] = ((a << 24) | (r << 16) | (g << 8) | b);  
    

             }  
          }  
              return drawPixels(srcPixels, srcW, srcH);  
        }  
    

    


         /*
        * ͼƬ������Ч
        */
         public Image effect_mirror(Image src)  
         {  
               int srcW = src.getWidth();  
               int srcH = src.getHeight();   
               int[] srcPixels = getPixels(src);  
               int len;  
               int temp;  
               for (int i = 0; i < srcH; i++)  
               {  
                   len = (i+1)*srcW;  
                   for(int ii=0;ii<srcW/2;ii++)   
                   {     
                      temp=srcPixels[i*srcW+ii];   
                      srcPixels[i*srcW+ii]=srcPixels[len-1-ii];   
                      srcPixels[len-1-ii]=temp;   
                   }   
                }  
                return drawPixels(srcPixels, srcW, srcH);  
         }  
    

           /*
        * ͼƬ���У�cut_xpos��cut_ypos �и�����ʼλ�����꣬cut_width��cut_height �и��Ŀ����
        */
         public Image effect_cut(Image src,int cut_xpos,int cut_ypos,int cut_width,int cut_height)  
         {  
               int srcW = src.getWidth();  
               int srcH = src.getHeight();   
               int[] srcPixels = getPixels(src);  
               int[] desPixels = new
               int[cut_width*cut_height];  
               int argb;  
               int num = 0;  
               for (int i = 0; i < srcH; i++)  
               {  
                 if(i >= cut_ypos&&i<cut_height+cut_ypos)  
                 {  
                      for(int ii=0;ii<srcW;ii++)  
                      {  
                         if(ii>=cut_xpos && ii<cut_width+cut_xpos)  
                         {  
                              desPixels[num] = srcPixels[i*srcW+ii];      
                              num++;  

                         }  
                      }  
                  }  
              }  
                return drawPixels(desPixels, cut_width, cut_height);  
         }  
    

    



    

           /*
        * ͼƬ��Ч�ڰ�
        */
         public Image effect_black_white(Image src) {  
              int srcW = src.getWidth();  
              int srcH = src.getHeight();   
              int[] srcPixels = getPixels(src);  
              int r = 0;  
              int g = 0;  
              int b = 0;  
              int a = 0;  
              int argb;  
              int temp;  
    

              for (int i = 0; i < srcH; i++)  
              {  
                   for(int ii=0;ii<srcW;ii++)  
             {  
                	   		 argb = srcPixels[i*srcW+ii];  
                             a = ((argb & 0xff000000) >> 24); // alpha channel
                             r = ((argb & 0x00ff0000) >> 16); // red channel
                             g = ((argb & 0x0000ff00) >> 8); // green channel
                             b = (argb & 0x000000ff); // blue channel
                             temp = (int)(.299*(double)r+.587*(double)g+.114*(double)b);  
                             r = temp;  
                             g = temp;  
                             b = temp;  
                             srcPixels[i*srcW+ii] = ((a << 24) | (r << 16) | (g << 8) | b);  
             }  
              }  
             return drawPixels(srcPixels, srcW, srcH);  
    

    


         }  
    


     

         /**
          *@param imgSource  Դͼ��
          *@param cx   ��ת�������Դͼ�����ϽǺ�����
          *@param cy   ��ת�������Դͼ�����Ͻ�������
          *@param theta ͼ����ʱ����ת�ĽǶ�
          *@return ��ת���ͼ��
          */
          public Image rotate(Image imgSource, int cx, int cy, double theta)   
          {  
              if (Math.abs(theta % 360) < 1)  
                  return imgSource; //�ǶȺ�Сʱֱ�ӷ���


              int w1 = imgSource.getWidth(); //ԭʼͼ��ĸ߶ȺͿ��


              int h1 = imgSource.getHeight();  


              int[] srcMap = new
              int[w1 * h1];  

              imgSource.getRGB(srcMap, 0, w1, 0, 0, w1, h1); //��ȡԭʼͼ���������Ϣ


              int dx = cx > w1 / 2 ? cx : w1 - cx; //������ת�뾶


              int dy = cy > h1 / 2 ? cy : h1 - cy;  


              double dr = Math.sqrt(dx * dx + dy * dy);  


              int wh2 = (int) (2 * dr + 1); //��ת����ͼ��Ϊ�����Σ���߳�+1��Ϊ�˷�ֹ����Խ��


              int[] destMap = new
              int[wh2 * wh2]; //�����ͼ�����ص�����


              double destX, destY;  


              double radian = theta * Math.PI / 180; //����Ƕȼ����Ӧ�Ļ���ֵ


              for (int i = 0; i < w1; i++) {  
                  for (int j = 0; j < h1; j++) {  
                      if (srcMap[j * w1 + i] >> 24 != 0) { //�Է�͸����Ž��д���
                          // �õ���ǰ�㾭��ת���������ͼ�����Ͻǵ�����
                          destX = dr + (i - cx) * Math.cos(radian) + (j - cy)  
                                  * Math.sin(radian);  
                          destY = dr + (j - cy) * Math.cos(radian) - (i - cx)  
                                  * Math.sin(radian);  
                          //��Դͼ��������ͼ�����������
                          destMap[(int) destY * wh2 + (int) destX] = srcMap[j * w1  
                                  + i];  
                      }  
                  }  
              }  
              return Image.createRGBImage(destMap, wh2, wh2, true); //������ת���ͼ��
          }  

    


 


}  