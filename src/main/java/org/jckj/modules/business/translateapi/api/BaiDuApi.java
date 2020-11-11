package org.jckj.modules.business.translateapi.api;

import com.alibaba.fastjson.JSONObject;
import org.jckj.modules.business.translateapi.config.BaiDuConfig;
import org.jckj.modules.business.translateapi.encryption.BaiduMd5;
import org.jckj.modules.business.translateapi.http.BaiduHttpGet;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class BaiDuApi {
    //普通翻译
    public static JSONObject text(String query, String to) {
        Map<String, String> params = buildParams(query, "auto", to);
        return BaiduHttpGet.get(BaiDuConfig.TRANS_API_HOST, params);
    }
    //文件翻译
    public static JSONObject file(MultipartFile file,String from, String to) {
        Map<String, String> params =buildFileParams(file, from, to);
        return BaiduHttpGet.post(BaiDuConfig.FILE_API_HOST, params);
    }
    private static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", BaiDuConfig.APP_ID);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = BaiDuConfig.APP_ID + query + salt + BaiDuConfig.SECURITY_KEY; // 加密前的原文
        params.put("sign", BaiduMd5.md5(src));

        return params;
    }
    private static Map<String, String> buildFileParams(MultipartFile file, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
//        params.put("q", file);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", BaiDuConfig.APP_ID);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = BaiDuConfig.APP_ID + file + salt + BaiDuConfig.SECURITY_KEY; // 加密前的原文
        params.put("sign", BaiduMd5.md5(src));

        return params;
    }
}
