package com.wzktravel.spider;

import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wzk on 15/10/13.
 */
public class ZnGirlsPageProcessor implements PageProcessor,AfterExtractor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String GALLERY_REGX = "http://www.zngirls.com/g/\\d+(/\\d+\\.html)?";

    public void process(Page page) {

        // 人物列表
        List<String> girlList = page.getHtml().xpath("//li[@class='beautyli']/div/a/@href").regex("/girl/\\d+/").all();
        // 人物列表翻页
        List<String> girlListPages = page.getHtml().xpath("//div[@class='pagesYY']/div/a/@href").regex("\\d+\\.html").all();
        // 写真页面
        List<String> girlGallery = page.getHtml().xpath("//a[@class='igalleryli_link']/@href").regex("/g/\\d+/").all();
        // 写真页面翻页
        List<String> girlGalleryPages = page.getHtml().xpath("//div[@id='pages']/a/@href").regex("/g/\\d+/[2-9]+\\.html").all();

        page.addTargetRequests(girlListPages);
        page.addTargetRequests(girlList);
        page.addTargetRequests(girlGallery);
        page.addTargetRequests(girlGalleryPages);

//        String girlName = page.getHtml().xpath("//div[@class='div_h1']/h1/text()").toString();
//        page.putField("name", girlName);
//        if (StringUtils.isEmpty(girlName)) {
//            page.setSkip(true);
//        }

        try {
            System.out.println(page.getUrl());
            if (page.getUrl().regex(GALLERY_REGX).match()) {
                String galleryName = page.getHtml().xpath("//h1[@id='htilte']/text()").toString();
                page.putField("galleryName", galleryName);
                String girlName = "";
                if (StringUtils.isNotEmpty(galleryName) && StringUtils.split(galleryName, "-").length > 0) {
                    girlName = StringUtils.split(galleryName, "-")[0].trim();
                }
                page.putField("girlName", girlName);
                List<String> images = page.getHtml().xpath("//ul[@id='hgallery']/img/@src").all();
                page.putField("images", images);
                String girlId = "";
                if (!images.isEmpty() && StringUtils.isNotEmpty(images.get(0))) {
                    girlId = StringUtils.substringBetween(images.get(0), "gallery/", "/");
                }
                page.putField("girlId", girlId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afterProcess(Page page) {

    }

    public Site getSite() {
        return site;
    }

    public void printToConsole(List<String> list) {
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
//        String ourl = "http://www.zngirls.com/tag/riben/";
        String ourl = "http://www.zngirls.com/girl/21600/";
        Spider.create(new ZnGirlsPageProcessor()).addUrl(ourl).addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("logs")).thread(5).run();
    }


}

