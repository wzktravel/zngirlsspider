package com.wzktravel.spider;

import com.wzktravel.downloader.ImageDownloader;
import com.wzktravel.pipeline.ImagePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MeizituPageProcessor
 * Created by wzk on 15/10/14.
 */
public class MeizituPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

//    private static List<String> imageurls = new ArrayList();

    public void process(Page page) {
        try {
            List<String> pages = page.getHtml().xpath("//div[@id='wp_page_numbers']/ul/li/a/@href").regex("qingchun_\\d_\\d.html").all();
            List<String> galleries = page.getHtml().xpath("//div[@class='pic']/a/@href").regex(".*/a/\\d+\\.html").all();

            page.addTargetRequests(pages);
            page.addTargetRequests(galleries);

            if (page.getUrl().regex(".*/a/\\d+.html").match()) {
                String galleryName = page.getHtml().xpath("//div[@class='metaRight']/h2/a/text()").toString();
                List<String> images = page.getHtml().xpath("//div[@id='picture']/p/img/@src").all();
                if (!images.isEmpty()) {
                    page.putField("galleryName", galleryName);
                    page.putField("images", images);
                    page.addTargetRequests(images);
                }
//                imageurls.addAll(images);
            } else if (page.getUrl().regex(".*\\.jpg").match()) {
                String imageStr = page.getRawText();
                String url = page.getRequest().getUrl();
                page.putField("url", url);
                page.putField("imageStr", imageStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String ourl = "http://www.meizitu.com/a/qingchun_3_1.html";
//        String ourl = "http://www.meizitu.com/a/5151.html";
//        String ourl = "http://pic.meizitu.com/wp-content/uploads/2015a/09/18/01.jpg";
        Spider.create(new MeizituPageProcessor()).addUrl(ourl).addPipeline(new ConsolePipeline())
            .addPipeline(new ImagePipeline()).setDownloader(new ImageDownloader()).thread(5).run();

        Map<String, String> map = new HashMap();
    }
}
