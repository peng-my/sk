package org.jckj.modules.business.translateapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.api.YouDaoApi;

import java.io.IOException;

public class YouDaoUtils {
    public static void main(String[] args) throws IOException {
        JSONObject text = YouDaoApi.text("你好", "en");
        System.err.println(text);
    }

}
