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
package org.jckj.modules.business.translation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.jckj.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 翻译记录实体类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Data
@TableName("translation_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Records对象", description = "翻译记录")
public class Records extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* mongodb的key
	*/
		@ApiModelProperty(value = "mongodb的key")
		private String mongodbKey;
	/**
	* mongodb的Id
	*/
		@ApiModelProperty(value = "mongodb的Id")
		@TableField("mongodb_Id")
	private String mongodbId;
	/**
	* 翻译类型
	*/
		@ApiModelProperty(value = "翻译类型{1:视频,2:文件,3:图片}")
		private String translationType;
	/**
	 * 会议室Id
	 */
	@ApiModelProperty(value = "会议室Id")
	private String conferenceRoomId;
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账号")
	private String account;

	/**
	 * 服务
	 */
	@ApiModelProperty(value = "服务")
	private String serve;
	/**
	 * 使用量
	 */
	@ApiModelProperty(value = "使用量")
	private String useNumber;

	/**
	* 原语音类型
	*/
		@ApiModelProperty(value = "原语音类型")
		private String originalVoiceType;
	/**
	* 目标语音类型
	*/
		@ApiModelProperty(value = "目标语音类型")
		@TableField("Target_language_type")
	private String targetLanguageType;


}
