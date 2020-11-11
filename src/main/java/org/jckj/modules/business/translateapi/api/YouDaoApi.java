package org.jckj.modules.business.translateapi.api;

import com.alibaba.fastjson.JSONObject;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.translateapi.config.YouDaoConfig;
import org.jckj.modules.business.translateapi.encryption.YouDaoEncryption;
import org.jckj.modules.business.translateapi.http.YouDaoHttp;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class YouDaoApi {
    //普通字幕
    public static JSONObject text(String query, String to) throws IOException {
        Map<String,String> params = new HashMap<String,String>();
        String q = query;
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "auto");
        params.put("to", to);
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = YouDaoConfig.APP_KEY + YouDaoEncryption.truncate(q) + salt + curtime + YouDaoConfig.APP_SECRET;
        String sign = YouDaoEncryption.getDigest(signStr);
        params.put("appKey", YouDaoConfig.APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        /** 处理结果 */
        JSONObject jsonObject = YouDaoHttp.requestForHttp(YouDaoConfig.YOUDAO_URL, params);
        System.err.println(jsonObject);
        return jsonObject;
    }
    //图片
    public static JSONObject picture(String fileBase64, String to) throws IOException {
        Map<String,String> params = new HashMap<String,String>();
//        String q = loadAsBase64("图片的路径");
        String q = fileBase64;
        String salt = String.valueOf(System.currentTimeMillis());
        String from = "源语言";
//        String to = "目标语言";
        String type = "1";
        params.put("from","auto");
        params.put("to",to);
        params.put("type",type);
        params.put("q", fileBase64);
        String signStr = YouDaoConfig.APP_KEY + q + salt + YouDaoConfig.APP_SECRET;
        String sign = YouDaoEncryption.getDigestPicture(signStr);
        params.put("appKey", YouDaoConfig.APP_KEY);
        params.put("salt", salt);
        params.put("sign", sign);
        JSONObject jsonObject = YouDaoHttp.pictureForHttp(YouDaoConfig.PICTURE_YOUDAO_URL,params);
        /** 处理结果 */
        System.err.println(jsonObject);
        return jsonObject;
    }
    public static String file(MultipartFile file,String from,String to) throws Exception {
        String files=null;
        String s = Base64.getEncoder().encodeToString(file.getBytes());
        //截取文件名的后缀
        String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String originalFilename = file.getOriginalFilename();
        JSONObject en = YouDaoApi.upload(s, originalFilename, contentType,from, to);
        System.out.println(en);
        if (en.containsKey("errorCode")){
            Integer errorCode = Integer.valueOf(en.get("errorCode").toString());
            if (errorCode==0){
                String flownumber = en.get("flownumber").toString();
                JSONObject query = YouDaoApi.query(flownumber);
                if (query.containsKey("errorCode")){
                    Integer errorCode1 = Integer.valueOf(query.get("errorCode").toString());
                    Integer status = Integer.valueOf(query.get("status").toString());
                    if (errorCode1==0&&status==4){
                        String word = YouDaoApi.download(flownumber, "word");
                        if (StringUtil.isNoneBlank(word)){
                            files=word;
                        }
                    }
                }
            }
        }
        return files;
    }

    public static JSONObject upload(String fileBase64,String fileName,String fileType, String langFrom,String to) throws IOException {
    Map<String,String> params = new HashMap<String,String>();
    String salt = String.valueOf(System.currentTimeMillis());
    String curtime = String.valueOf(System.currentTimeMillis() / 1000);
    String signStr = YouDaoConfig.APP_KEY + truncate(fileBase64) + salt + curtime + YouDaoConfig.APP_SECRET;
    String sign = YouDaoEncryption.getFileDigest(signStr);
    params.put("q", fileBase64);
    params.put("fileName", fileName);
    params.put("fileType", fileType);
//    params.put("langFrom", "zh-CHS");
    params.put("langFrom", langFrom);
    params.put("langTo", to);
    params.put("appKey", YouDaoConfig.APP_KEY);
    params.put("salt", salt);
    params.put("curtime", curtime);
    params.put("sign", sign);
    params.put("docType", "json");
    params.put("signType", "v3");
        JSONObject jsonObject = JSONObject.parseObject(YouDaoHttp.FileForHttp(YouDaoConfig.YOUDAO_URL_UPLOAD, params).toString());
        /** 处理结果 */
    System.err.println(StringUtil.format("有道上传：{}",jsonObject.toJSONString()));
    return jsonObject;
}

    public static JSONObject query(String flownumber) throws IOException {
        Map<String,String> params = new HashMap<String,String>();
//        String flownumber = "文件流水号";
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = YouDaoConfig.APP_KEY + truncate(flownumber) + salt + curtime +YouDaoConfig. APP_SECRET;
        String sign = YouDaoEncryption.getDigest(signStr);
        params.put("flownumber", flownumber);
        params.put("appKey", YouDaoConfig.APP_KEY);
        params.put("salt", salt);
        params.put("curtime", curtime);
        params.put("sign", sign);
        params.put("docType", "json");
        params.put("signType", "v3");
        JSONObject jsonObject = JSONObject.parseObject(YouDaoHttp.FileForHttp(YouDaoConfig.YOUDAO_URL_QUERY, params).toString());
        /** 处理结果 */
        System.err.println(StringUtil.format("有道查询：{}",jsonObject.toJSONString()));
        if (jsonObject.containsKey("status")){
            Integer status = Integer.valueOf(jsonObject.get("status").toString());
            if (status==4){
                return jsonObject;
            }
        }
        return query(flownumber);
    }

    public static String download(String flownumber,String downloadFileType) throws IOException {
        Map<String,String> params = new HashMap<String,String>();
//        String flownumber = "文件流水号";
        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = YouDaoConfig.APP_KEY + truncate(flownumber) + salt + curtime + YouDaoConfig.APP_SECRET;
        String sign = YouDaoEncryption.getDigest(signStr);
        params.put("flownumber", flownumber);
        params.put("downloadFileType", downloadFileType);
        params.put("appKey", YouDaoConfig.APP_KEY);
        params.put("salt", salt);
        params.put("curtime", curtime);
        params.put("sign", sign);
        params.put("docType", "json");
        params.put("signType", "v3");
        String o = YouDaoHttp.FileForHttp(YouDaoConfig.YOUDAO_URL_DOWNLOAD, params).toString();
        /** 处理结果 */
        System.err.println(StringUtil.format("有道下载：{}",o));
        return o;
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