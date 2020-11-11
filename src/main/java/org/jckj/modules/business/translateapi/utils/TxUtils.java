package org.jckj.modules.business.translateapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.api.TxApi;

;

public class TxUtils {
    public static void main(String[] args) {
        JSONObject baidu = TxApi.text("你好", "en");
        System.err.println(baidu);
    }



}


