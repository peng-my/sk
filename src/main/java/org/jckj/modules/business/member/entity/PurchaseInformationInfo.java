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
package org.jckj.modules.business.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.jckj.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员购买信息记录表实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("member_purchase_information_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PurchaseInformationInfo对象", description = "会员购买信息记录表")
public class PurchaseInformationInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 会员购买信息外键
	*/
		@ApiModelProperty(value = "会员购买信息外键")
		private Long fkMemberPurchaseInformationId;
	/**
	* 操作类型
	*/
		@ApiModelProperty(value = "操作类型 1:新购买 2:续费或者叠加")
		private String operationType;
	/**
	* 购买月份
	*/
		@ApiModelProperty(value = "购买月份")
		private Integer surplusDate;


}
