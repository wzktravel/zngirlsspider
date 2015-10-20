package com.wzktravel.downloader;

import com.wzktravel.util.ImageUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
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
        if (isImage(httpResponse)) {
            byte[] imageBytes = EntityUtils.toByteArray(httpResponse.getEntity());
            String imageStr = ImageUtils.getImageBase64(imageBytes);
            return imageStr;
        } else {
            return super.getContent(charset, httpResponse);
        }
    }

    boolean isImage(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getHeaders("Content-Type");
        if (headers != null && headers.length > 0) {
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i].getValue());
                if (StringUtils.contains(headers[i].getValue(), "image/")) {
                    return true;
                }
            }
        }
        return false;
    }
}
