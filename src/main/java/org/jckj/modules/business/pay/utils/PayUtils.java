package org.jckj.modules.business.pay.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jckj.core.tool.utils.DateUtil;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.pay.config.AlipayConfig;
import org.jckj.modules.business.pay.payentity.AliPay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PayUtils {
	public static final 	String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public void aliPayCs(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.APP_ID,AlipayConfig.APP_PRIVATE_KEY,"json","utf-8",AlipayConfig.ALIPAY_PUBLIC_KEY,"RSA2");
		AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
		//同步通知url
		alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_url);
		//异步通知url
		alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url);
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());

		alipayTradePagePayRequest.setBizContent("{\"out_trade_no\":\""+ "DD"+date+seconds +"\","
			+ "\"total_amount\":\""+ "1.00" +"\","
			+ "\"subject\":\""+ "VIP会员 1个月" +"\","
			+ "\"body\":\""+ "VIP会员 1个月" +"\","
			+ "\"timeout_express\":\"10m\","
			+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayTradePagePayRequest);
		String result="";
		if(alipayTradePagePayResponse.isSuccess()){
			try {
				result = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
			System.err.println("构建支付宝支付表单成功");
		} else {
			System.err.println("构建支付宝支付表单失败");
		}
		response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
		response.getWriter().write(result);//直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}
	public void aliPay(String orderCode,String totalAmount,String subject, HttpServletResponse response) throws IOException, AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.APP_ID,AlipayConfig.APP_PRIVATE_KEY,"json","utf-8",AlipayConfig.ALIPAY_PUBLIC_KEY,"RSA2");
		AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
		//同步通知url
		alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_url);
		//异步通知url
		alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url);
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());

		alipayTradePagePayRequest.setBizContent("{\"out_trade_no\":\""+ orderCode +"\","
					+ "\"total_amount\":\""+ totalAmount+"\","
					+ "\"subject\":\""+ subject +"\","
					+ "\"body\":\""+ subject +"\","
					+ "\"timeout_express\":\"10m\","
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayTradePagePayRequest);
		String result="";
		if(alipayTradePagePayResponse.isSuccess()){
			try {
				result = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
			System.err.println("构建支付宝支付表单成功");
		} else {
			System.err.println("构建支付宝支付表单失败");
		}
		response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
		response.getWriter().write(result);//直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}
	public String synchronous(HttpServletRequest request) {

		Map<String, String> parameters = new HashMap<>();
		Map<String, String[]> requestParams = request.getParameterMap();
//		System.err.println(StringUtil.format("同步回调成功 {} 参数为:{}",parameters,requestParams));
//		log.info("支付宝同步参数", requestParams);
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			parameters.put(key, valueStr);
		}
		//记录日志
		System.err.println(StringUtil.format("同步{}",request));
		String merchantOrderNo = request.getParameter("out_trade_no");//商户订单号
		System.err.println(StringUtil.format("商户订单号为:{} 同步回调成功 {} 参数为:{}",merchantOrderNo,parameters,requestParams));

//		cashLogMapper.add(request.getParameter("out_trade_no"), "SYNCHRONOUS", JSON.toJSONString(parameters), new Date());
		return "true";
//		return  requestParams.toString();
	}

	public AliPay notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接收参数进行校验
//		Map<String, String> parameters = new HashMap<>();
//		Map<String, String[]> requestParams = request.getParameterMap();
//		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
//			String key = entry.getKey();
//			String[] values = entry.getValue();
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			parameters.put(key, valueStr);
//		}
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		AliPay aliPay=new AliPay();
		if (params.size()>0){
			if (params.containsKey("trade_status")){
				Map<Object,Object> aliPayEntity=new HashMap<>();
				//通知校验ID
				String notifyId = params.get("notify_id");
				aliPayEntity.put("notifyId",notifyId);
				//支付类型
				Integer payType=1;
				aliPayEntity.put("payType",payType);
				//支付状态
				String tradeStatuses = params.get("trade_status");
				aliPayEntity.put("tradeStatuses",tradeStatuses);
				//商户订单号
				String outTradeNo = String.valueOf(params.get("out_trade_no"));
				aliPayEntity.put("outTradeNo",outTradeNo);
				//支付宝交易号
				String tradeNo =params.get("trade_no");
				aliPayEntity.put("tradeNo",tradeNo);
				//买家支付宝账号
				String buyerId =params.get("buyerId");
				aliPayEntity.put("buyerId",buyerId);
				//订单金额
				Double totalAmount = Double.valueOf(params.get("total_amount"));
				aliPayEntity.put("totalAmount",totalAmount);
				//实收金额
				Double receiptAmount = Double.valueOf(params.get("receipt_amount"));
				aliPayEntity.put("receiptAmount",receiptAmount);
				//付款金额
				Double buyerPayAmount = Double.valueOf(params.get("buyer_pay_amount"));
				aliPayEntity.put("buyerPayAmount",buyerPayAmount);
				//订单标题
				String subject = params.get("subject");
				aliPayEntity.put("subject",subject);
				//商品描述
				String body = params.get("body");
				aliPayEntity.put("body",body);
				//交易创建时间
				Date gmtCreate = DateUtil.parse(String.valueOf(params.get("gmt_create")), PATTERN_DATETIME);
				aliPayEntity.put("gmtCreate",gmtCreate);
				//交易付款时间
				Date gmtPayment = DateUtil.parse(String.valueOf(params.get("gmt_payment")), PATTERN_DATETIME);
				aliPayEntity.put("gmtPayment",gmtPayment);
				//通知时间
				Date notifyTime = DateUtil.parse(String.valueOf(params.get("notify_time")), PATTERN_DATETIME);
				aliPayEntity.put("notifyTime",notifyTime);
				//转为实体对象
				 aliPay = JSON.parseObject(JSON.toJSONString(aliPayEntity), AliPay.class);
			}
		}
		System.err.println(StringUtil.format("异步{}",request));
		System.err.println(StringUtil.format("异步回调成功 {} 参数为:{}",params,requestParams));
		return aliPay;
//		log.info("parameters is [parameters={}]", parameters);
//		String appId = request.getParameter("app_id");//appid
//		String merchantOrderNo = request.getParameter("out_trade_no");//商户订单号
//
//		String orderId = cashMapper.getCashByMerchantOrderNo(merchantOrderNo).getOrderId();
//		if (orderId == null) {
//			log.error("orderId is null");
//			ReturnData.fail(ReturnCode.SERVER_EXCEPTION);
//		}
//		log.info("orderId: {}", orderId);
//		String payState = request.getParameter("trade_status");//交易状态
//		String encodeOrderNum = null;
//		cashLogMapper.add(request.getParameter("out_trade_no"), "NOTIFY", JSON.toJSONString(parameters), new Date());
//		try {
//			encodeOrderNum = URLDecoder.decode(request.getParameter("passback_params"), "UTF-8");
//			log.info("encodeOrderNum is [encodeOrderNum={}]", encodeOrderNum);
//
//			boolean signVerified;
//			signVerified = AlipaySignature.rsaCheckV1(parameters, ali_public_key, "UTF-8", sign_type);//验证签名
//			log.info("signVerified is [signVerified={}]", signVerified);
//			if (signVerified) { //通过验证
//				log.info("payState: {}", payState);
//				if (payState.equals(TRADE_SUCCESS)) {
//					//判断订单号与插入的订单号是否一样
//					if (merchantOrderNo.equals(encodeOrderNum) == false || appid.equals(appId) == false) {
//						log.info("vali failure");
//						cashMapper.update(merchantOrderNo, 4);
//
//						response.getOutputStream().print("failure");
//						return;
//					}
//					cashMapper.update(merchantOrderNo, 3);
//					orderMapper.afterPay(orderId
//					);
//					response.getOutputStream().print("success");
//					return;
//				} else if (payState.equals(TRADE_CLOSED)) { //交易关闭
//					cashMapper.update(merchantOrderNo, 7);
//				} else {
//					cashMapper.update(merchantOrderNo, 4);
//					response.getOutputStream().print("failure");
//					return;
//				}
//			} else {
//				//签名校验失败更状态
//				cashMapper.update(merchantOrderNo, 4);
//				response.getOutputStream().print("failure");
//				return;
//			}
//			log.info("encodeOrderNum is  [encodeOrderNum={}]", encodeOrderNum);
//			cashMapper.update(merchantOrderNo, 4);
//			response.getOutputStream().print("failure");
//			return;
//		} catch (AlipayApiException e) {
//			log.error(e.getErrMsg());
//			throw new RuntimeException("调用支付宝接口发生异常");
//		} catch (UnsupportedEncodingException e) {
//			log.error(e.getMessage());
//			throw new RuntimeException("URLDecoderf发生异常");
//		} catch (IOException e) {
//			log.error(e.getMessage());
//			throw new RuntimeException("IO发生异常");
//		}

	}
}
