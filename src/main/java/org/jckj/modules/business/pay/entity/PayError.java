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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.core.mp.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 支付表实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("pay_error")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "error对象", description = "支付异常表")
public class PayError extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 支付回调表id
	*/
		@ApiModelProperty(value = "支付回调表id")
		private Long fkPayCallbackId;
	/**
	* 异常说明
	*/
		@ApiModelProperty(value = "异常说明")
		private String remarks;


}
