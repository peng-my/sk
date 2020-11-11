package org.jckj.modules.auth.granter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.jckj.core.launch.constant.TokenConstant;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.Func;
import org.jckj.modules.auth.provider.ITokenGranter;
import org.jckj.modules.auth.provider.TokenParameter;
import org.jckj.modules.auth.utils.TokenUtil;
import org.jckj.modules.system.entity.Tenant;
import org.jckj.modules.system.entity.UserInfo;
import org.jckj.modules.system.service.ITenantService;
import org.jckj.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author Peng
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private final IUserService userService;
	private final ITenantService tenantService;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = AuthUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
			if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
				// 获取租户信息
				Tenant tenant = tenantService.getByTenantId(tenantId);
				if (TokenUtil.judgeTenant(tenant)) {
					throw new ServiceException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
				}
				// 获取用户信息
				userInfo = userService.userInfo(Func.toLong(claims.get(TokenConstant.USER_ID)));
			}
		}
		return userInfo;
	}
}
