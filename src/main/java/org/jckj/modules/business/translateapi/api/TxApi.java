package org.jckj.modules.business.translateapi.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.ImageTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.ImageTranslateResponse;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;
import org.jckj.modules.business.translateapi.config.TxConfig;

import java.util.LinkedHashMap;

public class TxApi {
    //普通翻译
    public static JSONObject text(String query, String to) {
        JSONObject jsonObject=new JSONObject();
        try{
            Credential cred = new Credential(TxConfig.SECRET_ID, TxConfig.SECRET_KEY);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");
            httpProfile.setConnTimeout(10); // 请求连接超时时间，单位为秒(默认60秒)
            httpProfile.setWriteTimeout(10);  // 设置写入超时时间，单位为秒(默认0秒)
            httpProfile.setReadTimeout(10);  // 设置读取超时时间，单位为秒(默认0秒)
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TmtClient client = new TmtClient(cred, "ap-chengdu", clientProfile);

            TextTranslateRequest req = new TextTranslateRequest();
            req.setProjectId(0L);
            req.setTarget(to);
            req.setSource("auto");
            req.setSourceText(query);
            TextTranslateResponse resp = client.TextTranslate(req);
            System.out.println(TextTranslateResponse.toJsonString(resp));
            jsonObject=JSONObject.parseObject(TextTranslateResponse.toJsonString(resp));
            JSONObject jsonObject1=new JSONObject(new LinkedHashMap<>());
            jsonObject1.put("length",query.length());
            jsonObject1.put("originalData",query);
            jsonObject1.put("postTranslationData",jsonObject.get("TargetText").toString());
            return jsonObject1;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    //图片
    public static JSONObject picture(String query, String to) {
        JSONObject jsonObject=new JSONObject();
        try{
            Credential cred = new Credential(TxConfig.SECRET_ID, TxConfig.SECRET_KEY);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");
            httpProfile.setConnTimeout(10); // 请求连接超时时间，单位为秒(默认60秒)
            httpProfile.setWriteTimeout(10);  // 设置写入超时时间，单位为秒(默认0秒)
            httpProfile.setReadTimeout(10);  // 设置读取超时时间，单位为秒(默认0秒)
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            TmtClient client = new TmtClient(cred, "ap-chengdu", clientProfile);
            ImageTranslateRequest req = new ImageTranslateRequest();
            req.setSessionUuid("session-00001");
            req.setProjectId(0L);
            req.setScene("doc");
            req.setTarget(to);
            req.setSource("auto");
            req.setData(query);
            ImageTranslateResponse resp = client.ImageTranslate(req);
            System.err.println(ImageTranslateResponse.toJsonString(resp));
            jsonObject=JSONObject.parseObject(TextTranslateResponse.toJsonString(resp));
            JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get("ImageRecord").toString());
            String value1 = jsonObject1.get("Value").toString();
            JSONArray value = JSONArray.parseArray(jsonObject1.get("Value").toString());
            JSONObject jsonObject3=new JSONObject(new LinkedHashMap<>());
            for (Object o:value){
                JSONObject jsonObject2 = JSONObject.parseObject(o.toString());
                if (jsonObject2.containsKey("SourceText")){
                    String sourceText = jsonObject2.get("SourceText").toString();
                    jsonObject3.put("length",sourceText.length());
                    jsonObject3.put("originalData",sourceText);
                }
                if (jsonObject2.containsKey("TargetText")){
                    String targetText = jsonObject2.get("TargetText").toString();
                    jsonObject3.put("postTranslationData",targetText);
                }
            }
            System.err.println(jsonObject3);
            return jsonObject3;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
