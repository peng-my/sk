package org.jckj.modules.business.translateapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.api.XuFeiApi;

public class XuFeiUtils {
    public static void main(String[] args) throws Exception {
        JSONObject txt = XuFeiApi.txt("你好", "en");
        System.err.println(txt);
    }
}
