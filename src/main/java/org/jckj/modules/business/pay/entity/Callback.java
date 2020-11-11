/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.jckj.modules.business.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jckj.core.mp.base.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 支付回调表实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("pay_callback")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Callback对象", description = "支付回调表")
public class Callback extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 订单外键
	*/
		@ApiModelProperty(value = "支付外订单外键键")
		private Long fkOrderManagementId;

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
