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

import org.jckj.core.mp.base.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付表实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Pay对象", description = "支付表")
public class Pay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 支付时间
	*/
		@ApiModelProperty(value = "支付时间")
		private LocalDateTime payDate;
	/**
	* 支付类型
	*/
		@ApiModelProperty(value = "支付类型")
		private String payType;
	/**
	* 支付请求参数
	*/
		@ApiModelProperty(value = "支付请求参数")
		private String payRequestParameters;


}
