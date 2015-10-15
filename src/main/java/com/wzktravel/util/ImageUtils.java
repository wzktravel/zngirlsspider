package com.wzktravel.util;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wangzhengkun on 2015/10/15.
 */
public class ImageUtils {

    public static String PATH_SEPERATOR = "/";

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String getImageBase64(byte[] data) {
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * 对字节数组字符串进行Base64解码
     */
    public static byte[] getDataFromBase64(String imgStr) {
        if (imgStr == null || imgStr.trim().length() < 1) { //图像数据为空
            return null;
        }
        byte[] b = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 保存图片到本地
     */
    public static boolean saveAsImage(String file, byte[] bytes) {
        try {
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将url转换为文件名，计算md5
     */
    public static String convertUrlToFilename(String imageurl) {
        return DigestUtils.md5Hex(imageurl);
    }

}
