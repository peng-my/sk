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
package org.jckj.modules.business.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.jckj.core.mp.base.BaseEntity;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增银行卡实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("new_bank_card")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BankCard对象", description = "新增银行卡")
public class BankCard extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 卡号
	*/
		@ApiModelProperty(value = "卡号")
		private String cardNumber;
	/**
	* 有效期
	*/
		@ApiModelProperty(value = "有效期")
		private LocalDate termOfValidity;
	/**
	* CVV
	*/
		@ApiModelProperty(value = "CVV ")
		private String cvv;
	/**
	* 名
	*/
		@ApiModelProperty(value = "名")
		private String name;
	/**
	* 中间名
	*/
		@ApiModelProperty(value = "中间名")
		private String middleName;
	/**
	* 姓
	*/
		@ApiModelProperty(value = "姓")
		private String surName;
	/**
	* 街道地址1
	*/
		@ApiModelProperty(value = "街道地址1")
		private String streetAddressOne;
	/**
	* 街道地址2
	*/
		@ApiModelProperty(value = "街道地址2")
		private String streetAddressTow;
	/**
	* 城市
	*/
		@ApiModelProperty(value = "城市")
		private String city;
	/**
	* 省/州
	*/
		@ApiModelProperty(value = "省/州")
		private String provinceState;
	/**
	* 邮编
	*/
		@ApiModelProperty(value = "邮编")
		private String postCode;
	/**
	* 国家/地区
	*/
		@ApiModelProperty(value = "国家/地区")
		private String countryRegion;


}
