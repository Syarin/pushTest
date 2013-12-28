package test ;
    


import javax.microedition.lcdui.*;  
//import javax.microedition.lcdui.game.Sprite;  
    
     public class ImageUtil  
     {  
         public static final int TURN_LEFT = 1;  
         public static final int TURN_RIGHT = 2;  
    
         /*
         *获取图片RGB数据，并返回大小为width*height大小的一维数组
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
         *将pixels[]里的数据，生成一张图片，图片宽为w，高为h
         */
         public Image drawPixels(int[] pixels, int w, int h)  
         {  
             Image image = Image.createRGBImage(pixels, w, h, true);  
             pixels = null;  
             return image;  
         }  
    

    


         /*
         *调整图片大小
         *destW 调整后的宽，destH调整后的高
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
        * 调整图片亮度与对比度。  contrast 对比度，light 亮度
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
          //公式y =ax+b   a为对比度，b为亮度
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
        * 图片镜像特效
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
        * 图片剪切，cut_xpos，cut_ypos 切割框的起始位置坐标，cut_width，cut_height 切割框的宽与高
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
        * 图片特效黑白
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
          *@param imgSource  源图像
          *@param cx   旋转点相对于源图像坐上角横坐标
          *@param cy   旋转点相对于源图像坐上角纵坐标
          *@param theta 图像逆时针旋转的角度
          *@return 旋转后的图像
          */
          public Image rotate(Image imgSource, int cx, int cy, double theta)   
          {  
              if (Math.abs(theta % 360) < 1)  
                  return imgSource; //角度很小时直接返回


              int w1 = imgSource.getWidth(); //原始图像的高度和宽度


              int h1 = imgSource.getHeight();  


              int[] srcMap = new
              int[w1 * h1];  

              imgSource.getRGB(srcMap, 0, w1, 0, 0, w1, h1); //获取原始图像的像素信息


              int dx = cx > w1 / 2 ? cx : w1 - cx; //计算旋转半径


              int dy = cy > h1 / 2 ? cy : h1 - cy;  


              double dr = Math.sqrt(dx * dx + dy * dy);  


              int wh2 = (int) (2 * dr + 1); //旋转后新图像为正方形，其边长+1是为了防止数组越界


              int[] destMap = new
              int[wh2 * wh2]; //存放新图像象素的数组


              double destX, destY;  


              double radian = theta * Math.PI / 180; //计算角度计算对应的弧度值


              for (int i = 0; i < w1; i++) {  
                  for (int j = 0; j < h1; j++) {  
                      if (srcMap[j * w1 + i] >> 24 != 0) { //对非透明点才进行处理
                          // 得到当前点经旋转后相对于新图像左上角的坐标
                          destX = dr + (i - cx) * Math.cos(radian) + (j - cy)  
                                  * Math.sin(radian);  
                          destY = dr + (j - cy) * Math.cos(radian) - (i - cx)  
                                  * Math.sin(radian);  
                          //从源图像中往新图像中填充像素
                          destMap[(int) destY * wh2 + (int) destX] = srcMap[j * w1  
                                  + i];  
                      }  
                  }  
              }  
              return Image.createRGBImage(destMap, wh2, wh2, true); //返回旋转后的图像
          }  

    


 


}  