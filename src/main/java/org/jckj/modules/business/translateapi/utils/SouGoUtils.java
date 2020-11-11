package org.jckj.modules.business.translateapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.api.SouGoApi;

public class SouGoUtils {
    public static void main(String[] args) throws Exception {
        JSONObject text = SouGoApi.text("你好", "en");
        System.out.println(text);
    }
}
