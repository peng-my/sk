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
package org.jckj.modules.business.meeting.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.jckj.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会议表实体类
 *
 * @author BladeX
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("meeting")
@ApiModel(value = "Meeting对象", description = "会议表")
public class Meeting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 会议室Id
	*/
		@ApiModelProperty(value = "会议室Id")
		private String conferenceRoomId;
	/**
	* 会议主题
	*/
		@ApiModelProperty(value = "会议主题")
		private String meetingTheme;
	/**
	* 会议密码
	*/
		@ApiModelProperty(value = "会议密码")
		private String meetingPassword;
	/**
	* 是否自动连接音频{"1":"开启","2":"不开启"}
	*/
		@ApiModelProperty(value = "是否自动连接音频{1:开启，2不开启}")
		private Integer isAudioFrequency;
	/**
	* 是否入会开启摄像头{"1":"开启","2":"不开启"}
	*/
		@ApiModelProperty(value = "是否入会开启摄像头{1:开启，2不开启}")
		private Integer isCamera;


}
