package com.wzktravel.downloader;

import com.wzktravel.util.ImageUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.io.IOException;

/**
 * Created by wzk on 15/10/15.
 */
public class ImageDownloader extends HttpClientDownloader {

    @Override
    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        byte[] imageBytes = EntityUtils.toByteArray(httpResponse.getEntity());
        String imageStr = ImageUtils.getImageBase64(imageBytes);
        return imageStr;
    }
}
