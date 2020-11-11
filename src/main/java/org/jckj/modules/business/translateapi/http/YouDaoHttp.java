package org.jckj.modules.business.translateapi.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.translateapi.config.OvertimeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class YouDaoHttp {
    private static Logger logger = LoggerFactory.getLogger(YouDaoHttp.class);

    public static JSONObject requestForHttp(String url, Map<String,String> params) throws IOException {
        JSONObject jsonObject=new JSONObject(new LinkedHashMap<>());
        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(OvertimeConfig.ConnectTimeout).setConnectTimeout(OvertimeConfig.ConnectTimeout).build();//设置超时时间
        httpPost.setConfig(requestConfig);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            logger.info("Content-Type:" + contentType[0].getValue());
            if("audio/mp3".equals(contentType[0].getValue())){
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if(result != null){//合成成功
                    String file = "合成的音频存储路径"+System.currentTimeMillis() + ".mp3";
                    byte2File(result,file);
                }
            }else{
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity,"UTF-8");
                EntityUtils.consume(httpEntity);
                logger.info(json);
                System.out.println(json);
                jsonObject=JSONObject.parseObject(json);
                if (jsonObject.containsKey("errorCode")){
                    Integer errorCode = Integer.valueOf(jsonObject.get("errorCode").toString());
                    if (errorCode!=0){
                        return null;
                    }else {
                        String data=null;
                        String query = jsonObject.get("query").toString();
                        Object translation = jsonObject.get("translation");
                        JSONArray objects = JSONArray.parseArray(translation.toString());
                        for (Object o:objects){
                            data=o.toString();
                        }
                        JSONObject jsonObject1 = new JSONObject(new LinkedHashMap<>());
                        jsonObject1.put("length",query.length());
                        jsonObject1.put("originalData",query);
                        jsonObject1.put("postTranslationData",data);
                        return jsonObject1;
                    }
                }

            }
        }catch (Exception e){
            return jsonObject=null;
        }
        finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return jsonObject=null;
    }

    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            logger.info(e.toString());
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    //图片
    public static JSONObject pictureForHttp(String url,Map<String,String> params) throws IOException {
        JSONObject jsonObject=new JSONObject();

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(OvertimeConfig.ConnectTimeout).setConnectTimeout(OvertimeConfig.ConnectTimeout).build();//设置超时时间
        httpPost.setConfig(requestConfig);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            HttpEntity httpEntity = httpResponse.getEntity();
            String json = EntityUtils.toString(httpEntity,"UTF-8");
            EntityUtils.consume(httpEntity);
            logger.info(json);
            System.out.println(json);
            jsonObject=JSONObject.parseObject(json);
            if (jsonObject.containsKey("errorCode")){
                Integer errorCode = Integer.valueOf(jsonObject.get("errorCode").toString());
                System.err.println(StringUtil.format("有道请求状态码：{}",errorCode));
                if (errorCode!=0){
                    return null;
                }else {
                    return jsonObject;
                }
            }
        }catch (Exception e){
            return jsonObject=null;
        }
        finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return jsonObject=null;
    }
    //文档
    public static Object FileForHttp(String url,Map<String,String> params) throws IOException {
       JSONObject jsonObject=new JSONObject();

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(OvertimeConfig.ConnectTimeout).setConnectTimeout(OvertimeConfig.ConnectTimeout).build();//设置超时时间
        httpPost.setConfig(requestConfig);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            HttpEntity httpEntity = httpResponse.getEntity();
            String  json= EntityUtils.toString(httpEntity,"UTF-8");

            if (params.containsKey("downloadFileType")){
                InputStream content = httpEntity.getContent();
                return json;
            }
            jsonObject=JSONObject.parseObject(json);
            logger.info(json);
            System.out.println(json);
            if (jsonObject.containsKey("errorCode")){
                Integer errorCode = Integer.valueOf(jsonObject.get("errorCode").toString());
                System.err.println(StringUtil.format("有道请求状态码：{}",errorCode));
                if (errorCode!=0){
                    return null;
                }else {
                    return jsonObject;
                }
            }
        }catch (Exception e){
            return jsonObject=null;
        }
        finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return jsonObject=null;
    }
    //文档
    public static InputStream FileForHttps(String url,Map<String,String> params) throws IOException {
        JSONObject jsonObject=new JSONObject();

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(OvertimeConfig.ConnectTimeout).setConnectTimeout(OvertimeConfig.ConnectTimeout).build();//设置超时时间
        httpPost.setConfig(requestConfig);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            HttpEntity httpEntity = httpResponse.getEntity();
            String  json= EntityUtils.toString(httpEntity,"UTF-8");
            InputStream content1 = httpEntity.getContent();
            if (params.containsKey("downloadFileType")){
                InputStream content = httpEntity.getContent();
                return content1;
            }
            jsonObject=JSONObject.parseObject(json);
            logger.info(json);
            System.out.println(json);
            if (jsonObject.containsKey("errorCode")){
                Integer errorCode = Integer.valueOf(jsonObject.get("errorCode").toString());
                System.err.println(StringUtil.format("有道请求状态码：{}",errorCode));
                if (errorCode!=0){
                    return null;
                }else {
                    return null;
                }
            }
        }catch (Exception e){
            System.err.println("错误了!");
            return null;
        }
        finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return null;
    }
}
