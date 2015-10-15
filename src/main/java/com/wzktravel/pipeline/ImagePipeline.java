package com.wzktravel.pipeline;

import com.wzktravel.util.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.util.List;

/**
 * Created by wangzhengkun on 2015/10/15.
 */
public class ImagePipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String galleryFiledName = "gallery";
    private String imageFiledName = "images";

    public ImagePipeline() {
        setPath("./data/webmagic");
    }

    public ImagePipeline(String path) {
        setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        try {
            String mpath = task.getUUID();
            String gallery = (String)resultItems.get(galleryFiledName);
            List<String> images = resultItems.get(imageFiledName);

            String imageStr = resultItems.get("imageStr");
            String url = resultItems.get("url");
            byte[] imageBytes = ImageUtils.getDataFromBase64(imageStr);
            String path = getFilepath(mpath, "xxx", url);

            checkAndMakeParentDirecotry(path);
            ImageUtils.saveAsImage(path, imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("write file error", e);
        }
    }

    public String getFilepath(String mpath, String gallery, String imageurl) {
        StringBuilder path = new StringBuilder(getPath());
        path.append(PATH_SEPERATOR).append(mpath).append(PATH_SEPERATOR);
        if (StringUtils.isEmpty(gallery)) {
            path.append(gallery).append(PATH_SEPERATOR);
        }
        path.append(ImageUtils.convertUrlToFilename(imageurl));
        path.append(".jpg");
        return path.toString();
    }

    public void setGalleryFiledName(String galleryFiledName) {
        this.galleryFiledName = galleryFiledName;
    }

    public void setImageFiledName(String imageFiledName) {
        this.imageFiledName = imageFiledName;
    }
}
