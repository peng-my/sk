package org.jckj.modules.business.pay.payenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  AliEnum {
	WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建，等待买家付款",false),

	TRADE_CLOSED("TRADE_CLOSED","未付款交易超时关闭，或支付完成后全额退款",false),

	TRADE_SUCCESS("TRADE_SUCCESS","交易支付成功",true),

	TRADE_FINISHED("TRADE_FINISHED","交易结束，不可退款",false);
	private String code;
	private String value;
	private boolean status;

	/**
	 * 根据code获取去value
	 * @param code
	 * @return
	 */
	public static String getValueByCode(String code){
		for(AliEnum platformFree:AliEnum.values()){
			if(code.equals(platformFree.getCode())){
				return platformFree.getValue();
			}
		}
		return  null;
	}
	/**
	* 根据code获取去status
	 * @param code
	 * @return
		 */
	public static boolean getStatus(String code){
		for(AliEnum platformFree:AliEnum.values()){

			if(code.equals(platformFree.getCode())){
				return platformFree.status;
			}
		}
		return  false;
	}
}
