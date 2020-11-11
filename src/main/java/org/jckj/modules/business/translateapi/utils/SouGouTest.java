package org.jckj.modules.business.translateapi.utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jckj.modules.business.translateapi.config.SouGoConfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class SouGouTest {
    public static void main(String[] args) throws IOException {

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(400);
        manager.setDefaultMaxPerRoute(30);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(manager)
                .build();
        HttpPost httpPost = new HttpPost(SouGoConfig.SOUGO_URL);
        String pid = SouGoConfig.PID;     //平台分配的PID，可前往用户中心申请
        String key = SouGoConfig.KEY;     //平台分配的key，可前往用户中心申请
        String q = "sogou";     //待翻译文本
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String salt = date;      //随机数，可以填入时间戳
        String sign = DigestUtils.md5Hex(pid + q + salt + key);
        List nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("from", "auto"));     //源语言：61种语言互译，支持源语言自动检测，详情见语种列表
        nvps.add(new BasicNameValuePair("to", "zh-CHS"));   //目标语言:61种语言互译 详情见语种列表
        nvps.add(new BasicNameValuePair("pid", pid));
        nvps.add(new BasicNameValuePair("q", q));
        nvps.add(new BasicNameValuePair("salt", salt));
        nvps.add(new BasicNameValuePair("sign", sign));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        HttpRequestBase requestBase = httpPost;
        requestBase.addHeader("content-type", "application/x-www-form-urlencoded");
        requestBase.addHeader("accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(requestBase);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        System.err.println(result);
    }
}
