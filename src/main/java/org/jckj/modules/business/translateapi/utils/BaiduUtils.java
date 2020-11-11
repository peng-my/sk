package org.jckj.modules.business.translateapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.api.BaiDuApi;

public class BaiduUtils {
    //文档翻译

    public static void main(String[] args) {
        JSONObject text = BaiDuApi.text("你好", "en");
        System.err.println(text);
    }
}
