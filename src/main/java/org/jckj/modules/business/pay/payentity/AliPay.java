package org.jckj.modules.business.pay.payentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class AliPay implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 通知校验ID
	 */
	@ApiModelProperty(value = "通知校验ID")
	private String notifyId;
	/**
	 * 支付类型
	 */
	@ApiModelProperty(value = "支付类型")
	private Integer payType;
	/**
	 * 支付状态
	 */
	@ApiModelProperty(value = "支付状态")
	private String tradeStatuses;
	/**
	 * 商户订单号
	 */
	@ApiModelProperty(value = "商户订单号")
	private String outTradeNo;
	/**
	 * 支付宝交易号
	 */
	@ApiModelProperty(value = "支付宝交易号")
	private String tradeNo;
	/**
	 * 买家支付宝账号
	 */
	@ApiModelProperty(value = "买家支付宝账号")
	private String buyerId;
	/**
	 * 订单金额
	 */
	@ApiModelProperty(value = "订单金额")
	private Double totalAmount;
	/**
	 * 实收金额
	 */
	@ApiModelProperty(value = "实收金额")
	private Double receiptAmount;
	/**
	 * 付款金额
	 */
	@ApiModelProperty(value = "付款金额")
	private Double buyerPayAmount;
	/**
	 * 订单标题
	 */
	@ApiModelProperty(value = "订单标题")
	private String subject;
	/**
	 * 商品描述
	 */
	@ApiModelProperty(value = "商品描述")
	private String body;
	/**
	 * 交易创建时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "交易创建时间")
	private Date gmtCreate;
	/**
	 * 交易付款时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "交易付款时间")
	private Date gmtPayment;
	/**
	 * 通知时间
	 */
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "通知时间")
	private Date notifyTime;
}
