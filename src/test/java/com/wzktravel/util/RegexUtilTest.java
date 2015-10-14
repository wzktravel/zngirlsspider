package com.wzktravel.util;

import org.junit.Test;

/**
 * Created by wzk on 15/10/14.
 */
public class RegexUtilTest {

    @Test
    public void test() {
        String regex = ".*/g/\\d+/([2-9].html)?";

        String s = "http://www.zngirls.com/g/16736/2.html";
        System.out.println(s.matches(regex));

        s = "http://www.zngirls.com/g/16736/";
        System.out.println(s.matches(regex));




    }

}
