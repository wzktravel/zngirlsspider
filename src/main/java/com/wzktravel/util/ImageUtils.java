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
     * ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
     */
    public static String getImageBase64(byte[] data) {
        //���ֽ�����Base64����
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//����Base64��������ֽ������ַ���
    }

    /**
     * ���ֽ������ַ�������Base64����
     */
    public static byte[] getDataFromBase64(String imgStr) {
        if (imgStr == null || imgStr.trim().length() < 1) { //ͼ������Ϊ��
            return null;
        }
        byte[] b = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//�����쳣����
                    b[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * ����ͼƬ������
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
     * ��urlת��Ϊ�ļ���������md5
     */
    public static String convertUrlToFilename(String imageurl) {
        return DigestUtils.md5Hex(imageurl);
    }

}
