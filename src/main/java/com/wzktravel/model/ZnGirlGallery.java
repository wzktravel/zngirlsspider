package com.wzktravel.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by wzk on 15/10/13.
 */

@HelpUrl(value = "/girl/\\d+/", sourceRegion = "//li[@class='beautyli']/div/a/@href")
@TargetUrl(value = "*/g/\\d+/([2-9].html)?" )
public class ZnGirlGallery extends Model<ZnGirlGallery> implements AfterExtractor {

    private static final String GALLERY_REGX = "http://www.zngirls.com/g/\\d+/(\\d+\\.html)?";

    @ExtractBy("//h1[@id='htilte']/text()")
    private String gallery;

    @ExtractBy(value = "//ul[@id='hgallery']/img/@src", multi = true)
    private List<String> images;

    @Override
    public void afterProcess(Page page) {
        System.out.println("xxxxxxxxxxx");
        if (page.getUrl().regex(GALLERY_REGX).match()) {
            System.out.println("xxxxxxxxxxx===========");
            String girlId = "";
            if (!images.isEmpty() && StringUtils.isNotEmpty(images.get(0))) {
                girlId = StringUtils.substringBetween(images.get(0), "gallery/", "/");
            }
            String girlName = "";
            if (StringUtils.isNotEmpty(gallery) && StringUtils.split(gallery, "-").length > 0) {
                girlName = StringUtils.split(gallery, "-")[0].trim();
            }

            this.set("id", girlId);
            this.set("name", girlName);
            this.set("images", StringUtils.join(images, ","));

            save();
        }
    }

    public static void main(String[] args) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://localhost/zngirls?characterEncoding=utf-8", "root", "root");
        c3p0Plugin.start();
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.addMapping("ZnGirlGallery", ZnGirlGallery.class);
        activeRecordPlugin.start();

        OOSpider.create(Site.me(), ZnGirlGallery.class).addUrl("http://www.zngirls.com/girl/21600/").run();
    }

}
