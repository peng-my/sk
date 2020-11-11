package org.jckj.modules.auth.provider;

import lombok.AllArgsConstructor;
import org.jckj.core.secure.exception.SecureException;
import org.jckj.core.tool.utils.Func;
import org.jckj.core.tool.utils.SpringUtil;
import org.jckj.modules.auth.granter.CaptchaTokenGranter;
import org.jckj.modules.auth.granter.PasswordTokenGranter;
import org.jckj.modules.auth.granter.RefreshTokenGranter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenGranterBuilder
 *
 * @author Peng
 */
@AllArgsConstructor
public class TokenGranterBuilder {

	/**
	 * TokenGranter缓存池
	 */
	private static final Map<String, ITokenGranter> GRANTER_POOL = new ConcurrentHashMap<>();

	static {
		GRANTER_POOL.put(PasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(PasswordTokenGranter.class));
		GRANTER_POOL.put(CaptchaTokenGranter.GRANT_TYPE, SpringUtil.getBean(CaptchaTokenGranter.class));
		GRANTER_POOL.put(RefreshTokenGranter.GRANT_TYPE, SpringUtil.getBean(RefreshTokenGranter.class));
	}

	/**
	 * 获取TokenGranter
	 *
	 * @param grantType 授权类型
	 * @return ITokenGranter
	 */
	public static ITokenGranter getGranter(String grantType) {
		ITokenGranter tokenGranter = GRANTER_POOL.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
		if (tokenGranter == null) {
			throw new SecureException("no grantType was found");
		} else {
			return GRANTER_POOL.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
		}
	}

}
