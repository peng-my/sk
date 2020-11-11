package org.jckj.modules.auth.utils;

import org.jckj.common.constant.TenantConstant;
import org.jckj.core.launch.constant.TokenConstant;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.secure.TokenInfo;
import org.jckj.core.secure.utils.SecureUtil;
import org.jckj.core.tenant.JcKjTenantProperties;
import org.jckj.core.tool.constant.JcKjConstant;
import org.jckj.core.tool.jackson.JsonUtil;
import org.jckj.core.tool.support.Kv;
import org.jckj.core.tool.utils.*;
import org.jckj.modules.system.entity.Tenant;
import org.jckj.modules.system.entity.User;
import org.jckj.modules.system.entity.UserInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证工具类
 *
 * @author Peng
 */
public class TokenUtil {


	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确";

	public final static String USER_TYPE_HEADER_KEY = "User-Type";
	public final static String DEFAULT_USER_TYPE = "web";

	public final static String USER_HAS_NO_ROLE = "未获得用户的角色信息";
	public final static String USER_HAS_NO_TENANT = "未获得用户的租户信息";
	public final static String USER_HAS_NO_TENANT_PERMISSION = "租户授权已过期,请联系管理员";


	private static JcKjTenantProperties tenantProperties;

	/**
	 * 获取租户配置
	 *
	 * @return tenantProperties
	 */
	private static JcKjTenantProperties getTenantProperties() {
		if (tenantProperties == null) {
			tenantProperties = SpringUtil.getBean(JcKjTenantProperties.class);
		}
		return tenantProperties;
	}

	/**
	 * 创建认证token
	 *
	 * @param userInfo 用户信息
	 * @return token
	 */
	public static Kv createAuthInfo(UserInfo userInfo) {
		Kv authInfo = Kv.create();
		User user = userInfo.getUser();
		//设置jwt参数
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.ACCESS_TOKEN);
		param.put(TokenConstant.TENANT_ID, user.getTenantId());
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		param.put(TokenConstant.DEPT_ID, user.getDeptId());
		param.put(TokenConstant.POST_ID, user.getPostId());
		param.put(TokenConstant.ROLE_ID, user.getRoleId());
		param.put(TokenConstant.OAUTH_ID, userInfo.getOauthId());
		param.put(TokenConstant.ACCOUNT, user.getAccount());
		param.put(TokenConstant.USER_NAME, user.getAccount());
		param.put(TokenConstant.NICK_NAME, user.getRealName());
		param.put(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()));

		//拼装accessToken
		try {
			TokenInfo accessToken = SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.ACCESS_TOKEN);
			//返回accessToken
			return authInfo.set(TokenConstant.TENANT_ID, user.getTenantId())
				.set(TokenConstant.USER_ID, Func.toStr(user.getId()))
				.set(TokenConstant.DEPT_ID, user.getDeptId())
				.set(TokenConstant.POST_ID, user.getPostId())
				.set(TokenConstant.ROLE_ID, user.getRoleId())
				.set(TokenConstant.OAUTH_ID, userInfo.getOauthId())
				.set(TokenConstant.ACCOUNT, user.getAccount())
				.set(TokenConstant.USER_NAME, user.getAccount())
				.set(TokenConstant.NICK_NAME, user.getRealName())
				.set(TokenConstant.ROLE_NAME, Func.join(userInfo.getRoles()))
				.set(TokenConstant.AVATAR, Func.toStr(user.getAvatar(), TokenConstant.DEFAULT_AVATAR))
				.set(TokenConstant.ACCESS_TOKEN, accessToken.getToken())
				.set(TokenConstant.REFRESH_TOKEN, createRefreshToken(userInfo).getToken())
				.set(TokenConstant.TOKEN_TYPE, TokenConstant.BEARER)
				.set(TokenConstant.EXPIRES_IN, accessToken.getExpire())
				.set(TokenConstant.LICENSE, TokenConstant.LICENSE_NAME);
		} catch (Exception ex) {
			return authInfo.set("error_code", HttpServletResponse.SC_UNAUTHORIZED).set("error_description", ex.getMessage());
		}
	}

	/**
	 * 创建refreshToken
	 *
	 * @param userInfo 用户信息
	 * @return refreshToken
	 */
	private static TokenInfo createRefreshToken(UserInfo userInfo) {
		User user = userInfo.getUser();
		Map<String, String> param = new HashMap<>(16);
		param.put(TokenConstant.TOKEN_TYPE, TokenConstant.REFRESH_TOKEN);
		param.put(TokenConstant.USER_ID, Func.toStr(user.getId()));
		return SecureUtil.createJWT(param, "audience", "issuser", TokenConstant.REFRESH_TOKEN);
	}

	/**
	 * 判断租户权限
	 *
	 * @param tenant 租户信息
	 * @return boolean
	 */
	public static boolean judgeTenant(Tenant tenant) {
		if (tenant == null) {
			throw new ServiceException(TokenUtil.USER_HAS_NO_TENANT);
		}
		if (StringUtil.equalsIgnoreCase(tenant.getTenantId(), JcKjConstant.ADMIN_TENANT_ID)) {
			return false;
		}
		Date expireTime = tenant.getExpireTime();
		if (getTenantProperties().getLicense()) {
			String licenseKey = tenant.getLicenseKey();
			String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
			expireTime = JsonUtil.parse(decrypt, Tenant.class).getExpireTime();
		}
		if (expireTime != null && expireTime.before(DateUtil.now())) {
			throw new ServiceException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
		}
		return false;
	}

}
