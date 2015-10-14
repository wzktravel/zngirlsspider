package com.wzktravel.model;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.JsonFilePageModelPipeline;

import java.util.List;

/**
 * Created by wzk on 15/10/14.
 */
@HelpUrl(value = "(http://www.meizitu.com/a/)?qingchun(_\\d_\\d)?.html", sourceRegion = "//div[@id='wp_page_numbers']/ul/li/a/@href")
@TargetUrl(value = "http://www.meizitu.com/a/\\d+.html", sourceRegion = "//li[@class='wp-item']/div/div/a/@href")
public class Meizitu {

    @ExtractBy(value="//div[@class='metaRight']/h2/a/text()")
    private String gallery;

    @ExtractBy(value="//div[@id='picture']/p/img/@src", multi = true)
    private List<String> pictures;

    public static void main(String[] args) {
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), Meizitu.class)
                .addUrl("http://www.meizitu.com/a/5047.html").run();

    }


    public String getGallery() {
        return gallery;
    }

    public List<String> getPictures() {
        return pictures;
    }

}
