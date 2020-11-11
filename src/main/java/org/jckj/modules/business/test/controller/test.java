package org.jckj.modules.business.test.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class test {
	public static void main(String[] args) {
//		String s="{\"id\":\"1\"}";
//		JSONObject jsonObject = JSONObject.parseObject(s);
//		String s1 = jsonObject.toString();
//		System.err.println(s1);
//		double d=new Double(1);
//		Long s2=null;
//		System.out.println(s2);
//		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
//		for (int i = 0; i < 10000; i++) {
//			OrderUtils orderUtils=new OrderUtils(0,0);
//			String s = orderUtils.nextId();
////			String order = getOrder();
//			System.out.println(s);
////		}
//		String trade_success = AliEnum.getValueByCode("WAIT_BUYER_PAY");
////		String trade_closed = AliEnum.getStatus("TRADE_FINISHED");
////		AliEnum aliEnum=new AliEnum();
////		boolean trade_closed = aliEnum.getStatus("TRADE_CLOSED");
//		boolean trade_success1 =AliEnum.getStatus("WAIT_BUYER_PAY");
//		System.out.println(trade_success1);
//		System.out.println(trade_success);
//		System.err.println(StringUtil.format("同步回调成功 {} 参数为:{}",1,2));
//		String id="1";
//		Long aLong = Long.valueOf(id);
//		System.out.println(aLong);
//		Double s=0.00;
//		if (s!=null&&s!=00.00){
//			System.out.println(2);
//		}else {
//			System.out.println(1);
//		}
//		String order = getOrder();
//		System.out.println(order);
	String s="sss s";
		System.out.println(s.length());
	}
	/**
	*前六位数（20150826）是年月日格式化：yyyyMMdd

	*中间的8位数（00001000）是：00001000，固定4个0+1000

	*在后两位（04）：随机生成一个两位数

	*在后两位（00）：又是固定的两个0

	*接下来的6位数是（617496）：时分秒的格式化HHmmss

	*最后两位是（94）：又是随机生成
	*/
	public static String getOrder(){
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
		return date+ UUID.randomUUID()+seconds;
//			return date+"00001000"+getTwo()+"00"+seconds+getTwo();

	}
	/**
	 * 产生随机的2位数
	 * @return
	 */
	public static String getTwo(){
		Random rad=new Random();

		String result  = rad.nextInt(100) +"";

		if(result.length()==1){
			result = "0" + result;
		}
		return result;
	}
}
