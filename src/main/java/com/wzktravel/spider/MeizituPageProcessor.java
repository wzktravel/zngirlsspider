package com.wzktravel.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by wzk on 15/10/14.
 */
public class MeizituPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
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
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String ourl = "http://www.meizitu.com/a/qingchun_3_1.html";
//        String ourl = "http://www.meizitu.com/a/5047.html";
        Spider.create(new MeizituPageProcessor()).addUrl(ourl).addPipeline(new ConsolePipeline())
                .addPipeline(new JsonFilePipeline("logs")).thread(5).run();

    }
}
