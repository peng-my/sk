package org.jckj.modules.business.pay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
	public static String APP_ID = "2016102600762471";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCT+9Bi6Nw3qzYeRl0WaS8VtnYXP/EIOQ8eRAXGcQSPDKryoNqUSKMhm9lWowYdTwzPnJpei8nvq1c4UkbfqVo1Iam5vt2xEeQ5nK31JqX5q+JsEGpkEP3v63p83p9FICJQgXn7jquUCWfN4eWSHY0KqbFPgP99/rgGH0uNWnyRIFvQ5K9KAlpqhO99j5JUEFK5JL7IpWxwMlIde3BCGwsIFdFja5LdhTDhzZlfzBroMJg3iJCOnykHQ8rigVEVCDudjDWUMHQH5Vxp16MpGThGhw/G7d7Bwhcga13+tA5nQeWCQp/+hUvklaOwGr3AfQSeTA9o9JlUn/eY3nXFUWGdAgMBAAECggEAe5TNf+xLC8pVVY8Zaf/JLG7Fy8DAP0e3UmUoFYKIdhX0JM8j+rV2Vjjalblq/8rmPfLgD5jZuObz3bsIKPw6syg7nzy/0oHIgDxSIep2rTxXyDslPg/aoSOblPAtPgMJ5QVbozKQIv5/y7WcjBtVj9QKmQR3WPcBI0u7Qn6QjHiduQR/TfnwSyo/sWNLOpsxpdcvr+R7zLxOf081XRPRBjhh3Ias8QcTy/fq6Cq5+6fwYDPE6zIbX+tdSQTAapnPeuXDhlyj90KTsIuBRG4VysGfYAsM9wk1402szXucbi9k6ZLBGJYc31vhMrMswqXTxKDChTzflswkXE7isxBmwQKBgQDPR2V/xQbmKl5KLIxTKQfCrxHYw8kTrR/2UJt7sL58FNIF6vpbpGPRSde4BA+KrlVP+yaP+ihi6FafO9892+Q+/Q21Mbi82ELdtvbntFPwnZTfC9HomKoYQu7wT7LkKL2jGix1YMcEhgrsMgDL2gJBUbZwxy9rgc3vrbJjh0p3RQKBgQC2xHEWCyVkMTVd1iOos9bW7UQNThTmNEHutDKaj9cskp71hFunpzOOQsjNwlp8FTadczY+XYRkY4rCbIUC/srS5BGnZ2DU6EzayyrQxGTpsblK45rnh2Q3RgViqHHbHtT/VgFBP8HTraT0eafts2OZEYh61vJs4yCivfZ/7hoaeQKBgBtv5PSTvQUMiKyi1vRPExLnH6Kkz+IF4Zw2MU6fJ6e/Dcnwv7EPwpK6ho56IyCFUQ+kPAeE4oGtJYIwZ62FhOH9lynr6fs9WSSGCorDKPVJtqILq35xbtqFSaGPuytOZUPIvCfRZqano5x+RfK16eSBj3fANfuyE/Ns95cLGoVNAoGBAK8qSOQCnLyQ+P/NS3HVorNsxQsnaZpUPVik9dbywxxiDd1rFFjzQ9M1qLSjcAt6XVS75iIRiOCaEpRSY4zCW+uRUGYsCz8iwP31ODzL7ZeYkOj4iS8bsO3UuzaaED0dKLDrYdqCW6cfxnfU3SBRDMTYbACnX8MCT/YJsGVd80CpAoGBAMmDFNVDS2x6tq1ahqemRwtILasPrVOGwcGq9mt1Xcu3zsr3/6FjU0pqA11ODDO/9xpFValQm0fVtzi5nAIuxotfG988v+BxHzzgXZ7F76CVWeZMhzs32+hZg97Jt1QcuCJdjmW6OUvNvbxffIuz4sUmVdGpbGLzx24PETvKyFCK";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://csdf.nat300.top/test/getNotifyUrlInfo";

	// 页面跳转同步通知页面路径 支付同步返回地址
	public static String return_url = "http://csdf.nat300.top/test/getReturnUrlInfo";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//	public static String ALIPAY_PUBLIC_KEY = "你自己的支付宝公钥，注意是支付宝公钥，不是应用公钥";
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkWQkowODnIZ6OUWNLIejkKZdIg77F+0Ltw2zoxHPasL5ZKw88Hms/E8E4QeGa5OAsfxoZAd3y/OEcO/z7jxBz+jm72SkBSWPAx4egN/6im6wvPIESSS+4wjIY/m2A2jzORUAlA/hIN4pZ4jWuut2m8We8EkSEf+9tHp8YSn4tAY6Ul1mCODQIW1Bz1cqK5h7kDGEaIjNkYTFvyDB0c5sXGGWX5+ZHmdc0X9n5ZRXtCBsZS4KblbluPwxnYECITZZTel+iGXsUbrQHeEVFtPOr062bVuJEGk3MLsLDw3zUXgBP81GA/tXmpFfnLTfkXsYk0dGttLVaLdsVt24JVnFLwIDAQAB";
	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String CHARSET = "utf-8";

	// 支付宝网关，这是沙箱的网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
