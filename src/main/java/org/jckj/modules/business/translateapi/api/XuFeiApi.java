package org.jckj.modules.business.translateapi.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jckj.modules.business.translateapi.config.XuFeiConfig;
import org.jckj.modules.business.translateapi.encryption.XuFeiEncryption;
import org.jckj.modules.business.translateapi.http.XuFeiHttp;
import org.jckj.modules.business.translateapi.utils.WebOTS;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class XuFeiApi {
    //普通翻译
    public static JSONObject txt(String quey,String to) throws Exception {
        JSONObject jsonObject=new JSONObject(new LinkedHashMap<>());
        String body = buildHttpBody(quey,to);
        System.err.println(body);
        //System.out.println("【ITR WebAPI body】\n" + body);
        Map<String, String> header = buildHttpHeader(body);
        Map<String, Object> resultMap = XuFeiHttp.doPost2(XuFeiConfig.WebITS_URL, header, body);
        if (resultMap != null) {
            String resultStr = resultMap.get("body").toString();
             jsonObject = JSONObject.parseObject(resultStr);
            if (jsonObject.containsKey("code")){
                Integer code = Integer.valueOf(jsonObject.get("code").toString());
                if (code!=0){
                    return null;
                }
                JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get("data").toString());
                JSONObject jsonObject2 = new JSONObject();
                Object result = jsonObject1.get("result");
                JSONObject jsonObject3 = JSONObject.parseObject(result.toString());
                Object trans_result = jsonObject3.get("trans_result");
                JSONObject jsonObject4 = JSONObject.parseObject(trans_result.toString());
                Object dst = jsonObject4.get("dst");
                Object src = jsonObject4.get("src");
                JSONObject jsonObject6=new JSONObject(new LinkedHashMap<>());
                jsonObject6.put("length",src.toString().length());
                jsonObject6.put("originalData",src.toString());
                jsonObject6.put("postTranslationData",dst.toString());
                return jsonObject6;
            }
            System.out.println("【OTS WebAPI 接口调用结果】\n" + resultStr);
            //以下仅用于调试
            Gson json = new Gson();
            WebOTS.ResponseData resultData = json.fromJson(resultStr, WebOTS.ResponseData.class);
            int code = resultData.getCode();
            if (resultData.getCode() != 0) {
                System.out.println("请前往https://www.xfyun.cn/document/error-code?code=" + code + "查询解决办法");
            }
        } else {
            System.out.println("调用失败！请根据错误信息检查代码，接口文档：https://www.xfyun.cn/doc/nlp/niutrans/API.html");
        }
        return null;
    }
    /**
     * 组装http请求头
     */
    public static Map<String, String> buildHttpHeader(String body) throws Exception {
        Map<String, String> header = new HashMap<String, String>();
        URL url = new URL(XuFeiConfig.WebITS_URL);

        //时间戳
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateD = new Date();
        String date = format.format(dateD);
        //System.out.println("【OTS WebAPI date】\n" + date);

        //对body进行sha256签名,生成digest头部，POST请求必须对body验证
        String digestBase64 = "SHA-256=" + XuFeiEncryption.signBody(body);
        //System.out.println("【OTS WebAPI digestBase64】\n" + digestBase64);

        //hmacsha256加密原始字符串
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("POST ").append(url.getPath()).append(" HTTP/1.1").append("\n").//
                append("digest: ").append(digestBase64);
        //System.out.println("【OTS WebAPI builder】\n" + builder);
        String sha = XuFeiEncryption.hmacsign(builder.toString(), XuFeiConfig.API_SECRET);
        //System.out.println("【OTS WebAPI sha】\n" + sha);

        //组装authorization
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", XuFeiConfig.API_KEY, "hmac-sha256", "host date request-line digest", sha);
        //System.out.println("【OTS WebAPI authorization】\n" + authorization);

        header.put("Authorization", authorization);
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json,version=1.0");
        header.put("Host", url.getHost());
        header.put("Date", date);
        header.put("Digest", digestBase64);
        //System.out.println("【OTS WebAPI header】\n" + header);
        return header;
    }


    /**
     * 组装http请求体
     */
    public static String buildHttpBody(String quey,String to) throws Exception {
        JsonObject body = new JsonObject();
        JsonObject business = new JsonObject();
        JsonObject common = new JsonObject();
        JsonObject data = new JsonObject();
        //填充common
        common.addProperty("app_id", XuFeiConfig.APPID);
        //填充business
        business.addProperty("from", "auto");
        business.addProperty("to", to);
        //填充data
        //System.out.println("【OTS WebAPI TEXT字个数：】\n" + TEXT.length());
        byte[] textByte = quey.getBytes("UTF-8");
        String textBase64 = new String(Base64.getEncoder().encodeToString(textByte));
        //System.out.println("【OTS WebAPI textBase64编码后长度：】\n" + textBase64.length());
        data.addProperty("text", textBase64);
        //填充body
        body.add("common", common);
        body.add("business", business);
        body.add("data", data);
        return body.toString();
    }

}
