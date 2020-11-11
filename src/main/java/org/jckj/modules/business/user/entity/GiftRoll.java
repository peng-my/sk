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
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员礼金卷及国籍实体类
 *
 * @author BladeX
 * @since 2020-10-19
 */
@Data
@TableName("user_gift_roll")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GiftRoll对象", description = "会员礼金卷及国籍")
public class GiftRoll extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 用户id
	*/
		@ApiModelProperty(value = "用户id")
		private Long fkUserId;
	/**
	* 注册的礼金卷
	*/
		@ApiModelProperty(value = "注册的礼金卷")
		private Double register;
	/**
	* 使用的礼金卷
	*/
		@ApiModelProperty(value = "使用的礼金卷")
		private Double employ;
	/**
	* 余额的礼金卷
	*/
		@ApiModelProperty(value = "余额的礼金卷")
		private Double balance;
	/**
	* 国籍
	*/
		@ApiModelProperty(value = "国籍")
		private String nationality;


}
