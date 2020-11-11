package org.jckj.modules.business.translateapi.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jckj.modules.business.translateapi.config.YouDaoConfig;
import org.jckj.modules.business.translateapi.encryption.YouDaoEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Demo {

    private static Logger logger = LoggerFactory.getLogger(Demo.class);

    private static final String YOUDAO_URL_UPLOAD = "https://openapi.youdao.com/file_trans/upload";

    private static final String YOUDAO_URL_QUERY = "https://openapi.youdao.com/file_trans/query";

    private static final String YOUDAO_URL_DOWNLOAD = "https://openapi.youdao.com/file_trans/download";

    private static final String APP_KEY = "582de88849ab8dab";

    private static final String APP_SECRET = "1RXpYa4mpyp5nm0h4mGG6Al082YXm4zw";

    public static void main(String[] args) throws IOException {
        String q="";
        upload(q,"cs.docx","docx","en");
//        query();
//        download();
    }

    public static void upload(String fileBase64,String fileName,String fileType, String to) throws IOException {
//        Map<String,String> params = new HashMap<String,String>();
//        String q = loadAsBase64("文件的路径");
//        String salt = String.valueOf(System.currentTimeMillis());
//        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
//        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
//        String sign = getDigest(signStr);
//        params.put("q", q);
//        params.put("fileName", "文件名称");
//        params.put("fileType", "文件类型");
//        params.put("langFrom", "源语言");
//        params.put("langTo", "目标语言");
//        params.put("appKey", APP_KEY);
//        params.put("salt", salt);
//        params.put("curtime", curtime);
//        params.put("sign", sign);
//        params.put("docType", "json");
//        params.put("signType", "v3");
//        String result = requestForHttp(YOUDAO_URL_UPLOAD, params);
//        /** 处理结果 */
//        System.out.println(result);
        Map<String,String> params = new HashMap<String,String>();
        String q = loadAsBase64("C:\\Users\\THUNOEROBOT\\Desktop\\cs.docx");
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = YouDaoConfig.APP_KEY + truncate(q) + salt + curtime + YouDaoConfig.APP_SECRET;
        String sign = YouDaoEncryption.getFileDigest(signStr);
        params.put("q", q);
        params.put("fileName", fileName);
        params.put("fileType", fileType);
        params.put("langFrom", "zh-CHS");
        params.put("langTo", to);
        params.put("appKey", YouDaoConfig.APP_KEY);
        params.put("salt", salt);
        params.put("curtime", curtime);
        params.put("sign", sign);
        params.put("docType", "json");
        params.put("signType", "v3");
//        JSONObject jsonObject = YouDaoHttp.FileForHttp(YouDaoConfig.YOUDAO_URL_UPLOAD, params);
        /** 处理结果 */
//        System.err.println(jsonObject);
//        return jsonObject;
    }

    public static void query() throws IOException {
        Map<String,String> params = new HashMap<String,String>();
        String flownumber = "文件流水号";
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = APP_KEY + truncate(flownumber) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("flownumber", flownumber);
        params.put("appKey", APP_KEY);
        params.put("salt", salt);
        params.put("curtime", curtime);
        params.put("sign", sign);
        params.put("docType", "json");
        params.put("signType", "v3");
        String result = requestForHttp(YOUDAO_URL_QUERY, params);
        /** 处理结果 */
        System.out.println(result);
    }

    public static void download() throws IOException {
        Map<String,String> params = new HashMap<String,String>();
        String flownumber = "文件流水号";
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = APP_KEY + truncate(flownumber) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("flownumber", flownumber);
        params.put("downloadFileType", "文件下载类型");
        params.put("appKey", APP_KEY);
        params.put("salt", salt);
        params.put("curtime", curtime);
        params.put("sign", sign);
        params.put("docType", "json");
        params.put("signType", "v3");
        String result = requestForHttp(YOUDAO_URL_DOWNLOAD, params);
        /** 处理结果 */
        System.out.println(result);
    }

    public static String requestForHttp(String url,Map<String,String> params) throws IOException {
        String result = "";

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
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
            result = EntityUtils.toString(httpEntity,"UTF-8");
            EntityUtils.consume(httpEntity);
        }finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return result;
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String loadAsBase64(String imgFile)
    {//将文件转化为字节数组字符串，并对其进行Base64编码处理

        File file = new File(imgFile);
        if(!file.exists()){
            logger.error("文件不存在");
            return null;
        }
        InputStream in = null;
        byte[] data = null;
        //读取文件字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);//返回Base64编码过的字节数组字符串
    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}