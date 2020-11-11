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
package org.jckj.modules.business.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.jckj.core.mp.base.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单管理实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("order_management")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Management对象", description = "订单管理")
public class Management extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 订单编号
	*/
		@ApiModelProperty(value = "订单编号")
		private String orderCode;
	/**
	* 购买会员
	*/
		@ApiModelProperty(value = "购买会员")
		private String buyingMembers;
	/**
	* 支付金额
	*/
		@ApiModelProperty(value = "支付金额")
		private Double payAmount;
	/**
	* 用户账号
	*/
		@ApiModelProperty(value = "用户账号")
		private String account;
	/**
	* 支付类型
	*/
		@ApiModelProperty(value = "支付类型")
		private String payType;
	/**
	* 支付时间
	*/
		@ApiModelProperty(value = "支付时间")
		private Date payStartDate;
	/**
	* 支付结束时间
	*/
		@ApiModelProperty(value = "支付结束时间")
		private Date payEndDate;
	/**
	* 购买会员等级外键
	*/
		@ApiModelProperty(value = "购买会员等级外键")
		private Long fkMemberLevelManagementId;
	/**
	 * 购买月份
	 */
	@ApiModelProperty(value = "购买月份")
	private Integer monthOfPurchase ;
	/**
	* 订单状态
	*/
		@ApiModelProperty(value = "订单状态 1待付款 2已付款")
		private Integer orderStatus;


}
