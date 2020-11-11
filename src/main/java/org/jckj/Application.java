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
package org.jckj;

import org.jckj.common.constant.CommonConstant;
import org.jckj.core.launch.JcKjApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动器
 *
 * @author Chill
 */
@EnableScheduling
@EnableTransactionManagement    //开启事务管理
@EnableAsync(proxyTargetClass=true)    //配置代理为cglib代理，默认使用 的是jdk动态代理
@SpringBootApplication
//@ServletComponentScan(basePackages = "org.jckj.common.filter")
public class Application {

	public static void main(String[] args) {
		JcKjApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
	}

}

