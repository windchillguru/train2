package ext.workflow;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片添加水印
 *
 * @author 段鑫扬
 * @version 2019/12/27
 */
public class TestImg {

    /*
     * @param  源图片路径
     * @param  保存的图片路径
     * @param  水印内容
     * @param  水印颜色
     * @param  水印字体
     */
    public static ByteArrayOutputStream addWaterMark(String waterMarkContent, String srcImgPath/*, String tarImgPath, String waterMarkContent,Color markContentColor,Font font*/) {
        try {
            int fontSize = 30;
            Font font = new Font("微软雅黑", Font.PLAIN, fontSize);                     //水印字体
            // String srcImgPath="http://192.168.12.135:10004/oss/941133960598392832/photo/1082582918142234624.jpg"; //源图片地址
            //  String tarImgPath="d:\\t.jpg"; //待存储的地址
            //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //Color color=new Color(355,355,355,128);
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            // URL url = new URL(srcImgPath);
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //g.setColor(color); //根据图片的背景设置水印颜色
            g.setFont(font);
            g.setColor(Color.red);//设置字体
            int fontlen = getWatermarkLength(waterMarkContent, g);
            int line = fontlen / srcImgWidth;//文字长度相对于图片宽度应该有多少行
            int y = srcImgHeight - (line + 1) * fontSize;
            System.out.println("水印文字总长度:" + fontlen + ",图片宽度:" + srcImgWidth + ",字符个数:" + waterMarkContent.length());
            //文字叠加,自动换行叠加
            int tempX = 0;
            int tempY = y;
            int tempCharLen = 0;//单字符长度
            int tempLineLen = 0;//单行字符总长度临时计算
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < waterMarkContent.length(); i++) {
                char tempChar = waterMarkContent.charAt(i);
                tempCharLen = getCharLen(tempChar, g);
                tempLineLen += tempCharLen;
                if (tempLineLen >= srcImgWidth) {
//长度已经满一行,进行文字叠加
                    g.drawString(sb.toString(), tempX, tempY);
                    sb.delete(0, sb.length());//清空内容,重新追加
                    tempY += fontSize;
                    tempLineLen = 0;
                }
                sb.append(tempChar);//追加字符
            }
            // g.setBackground(Color.red);
            //设置水印的坐标
       /*     int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
            int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);*/
            /*int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 3;
            int y = srcImgHeight - 3;
            g.drawString(waterMarkContent, x, y);*/  //画出水印
            // g.drawString(waterMarkContent, srcImgWidth-20, srcImgHeight-30);
            g.drawString(sb.toString(), tempX, tempY);//最后叠加余下的文字
            g.dispose();
            //创建储存图片二进制流的输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                //创建ImageOutputStream流
                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);
                //将二进制数据写进ByteArrayOutputStream
                ImageIO.write(bufImg, "jpg", imageOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String tarImgPath = "C:\\img\\yan2.jpg"; //输出图片地址
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();
            return baos;
        } catch (Exception e) {

        }
        return null;
    }

    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    public static int getCharLen(char c, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charWidth(c);
    }

    public static void main(String[] args) throws IOException {
        String waterMarkContent = "你好你是谁啊";  //水印内容
        String srcImgPath = "C:\\img\\yan.jpg"; //源图片地址
        String srcImgPath2 = "C:\\img\\yan2.jpg"; //输出图片地址
        ByteArrayOutputStream baos = new TestImg().addWaterMark(waterMarkContent, srcImgPath);
        // FileOutputStream outImgStream = new FileOutputStream(srcImgPath2);
        // File file = new File(srcImgPath2);
        // OutputStream outputStream = new FileOutputStream(file);
        //baos.writeTo(outputStream);
        // outputStream.write();

    }
}

