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
package org.jckj.common.constant;

import org.jckj.core.launch.constant.AppConstant;

/**
 * 启动常量
 *
 * @author Chill
 */
public interface LauncherConstant {

	/**
	 * sentinel dev 地址
	 */
	String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

	/**
	 * sentinel prod 地址
	 */
	String SENTINEL_PROD_ADDR = "127.0.0.1:8858";

	/**
	 * sentinel test 地址
	 */
	String SENTINEL_TEST_ADDR = "127.0.0.1:8858";

	/**
	 * elk dev 地址
	 */
	String ELK_DEV_ADDR = "127.0.0.1:9000";

	/**
	 * elk prod 地址
	 */
	String ELK_PROD_ADDR = "127.0.0.1:9000";

	/**
	 * elk test 地址
	 */
	String ELK_TEST_ADDR = "127.0.0.1:9000";

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		switch (profile) {
			case (AppConstant.PROD_CODE):
				return SENTINEL_PROD_ADDR;
			case (AppConstant.TEST_CODE):
				return SENTINEL_TEST_ADDR;
			default:
				return SENTINEL_DEV_ADDR;
		}
	}



}
