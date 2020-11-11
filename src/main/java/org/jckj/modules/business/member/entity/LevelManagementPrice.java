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
 * 会员等级管理价格表实体类
 *
 * @author BladeX
 * @since 2020-10-20
 */
@Data
@TableName("member_level_management_price")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LevelManagementPrice对象", description = "会员等级管理价格表")
public class LevelManagementPrice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 会员等级管理外键
	*/
		@ApiModelProperty(value = "会员等级管理外键")
		private String fkMemberLevelManagementId;
	/**
	* 月份
	*/
		@ApiModelProperty(value = "月份")
		private Integer month;
	/**
	* 日元
	*/
		@ApiModelProperty(value = "日元")
		private Double jpy;
	/**
	* 人民币
	*/
		@ApiModelProperty(value = "人民币")
		private Double cny;
	/**
	* 美元
	*/
		@ApiModelProperty(value = "美元")
		private Double usd;


}
