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
 * 会员套餐余额记录表实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("member_package_balance_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PackageBalanceInfo对象", description = "会员套餐余额记录表")
public class PackageBalanceInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 会员套餐余额外键
	*/
		@ApiModelProperty(value = "会员套餐余额外键")
		private Long fkMemberPackageBalanceId;
	/**
	* 会员等级
	*/
		@ApiModelProperty(value = "会员等级")
		private String membershipLevel;
	/**
	* 语音会议
	*/
		@ApiModelProperty(value = "语音会议")
		private Double voiceConference;
	/**
	* 视频会议
	*/
		@ApiModelProperty(value = "视频会议")
		private Double videoConferencing;
	/**
	* 字幕翻译
	*/
		@ApiModelProperty(value = "字幕翻译")
		private Double subtitleTranslation;
	/**
	* 文件翻译
	*/
		@ApiModelProperty(value = "文件翻译")
		private Double documentaryTranslation;
	/**
	* 图片识别及翻译
	*/
		@ApiModelProperty(value = "图片识别及翻译")
		private Double imageRecognitionAndTranslation;


}
