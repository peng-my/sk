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
package org.jckj.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.tool.utils.DigestUtil;
import org.jckj.core.tool.utils.Func;
import org.jckj.core.tool.utils.WebUtil;
import org.jckj.modules.auth.enums.JcKjUserEnum;
import org.jckj.modules.auth.provider.ITokenGranter;
import org.jckj.modules.auth.provider.TokenParameter;
import org.jckj.modules.auth.utils.TokenUtil;
import org.jckj.modules.system.entity.Tenant;
import org.jckj.modules.system.entity.UserInfo;
import org.jckj.modules.system.service.ITenantService;
import org.jckj.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码TokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class CaptchaTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "captcha";

	private final IUserService userService;
	private final ITenantService tenantService;


	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		HttpServletRequest request = WebUtil.getRequest();



		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String username = tokenParameter.getArgs().getStr("username");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(username, password)) {
			// 获取租户信息
			Tenant tenant = tenantService.getByTenantId(tenantId);
			if (TokenUtil.judgeTenant(tenant)) {
				throw new ServiceException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
			}
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(JcKjUserEnum.WEB.getName())) {
				userInfo = userService.userInfo(tenantId, username, DigestUtil.hex(password));
			} else if (userType.equals(JcKjUserEnum.APP.getName())) {
				userInfo = userService.userInfo(tenantId, username, DigestUtil.hex(password));
			} else {
				userInfo = userService.userInfo(tenantId, username, DigestUtil.hex(password));
			}
		}
		return userInfo;
	}

}
