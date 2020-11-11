package org.jckj.common.cache;

import org.jckj.core.cache.utils.CacheUtil;
import org.jckj.core.tool.utils.SpringUtil;
import org.jckj.core.tool.utils.StringPool;
import org.jckj.modules.system.entity.User;
import org.jckj.modules.system.service.IUserService;

import static org.jckj.core.cache.constant.CacheConstant.USER_CACHE;


public class UserCache {
	private static final String USER_CACHE_ID = "user:id:";
	private static final String USER_CACHE_ACCOUNT = "user:account:";

	private static final IUserService userService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
	}



	/**
	 * 获取用户
	 *
	 * @param userId 用户id
	 * @return
	 */
	public static User getUser(Long userId) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, userId, () -> userService.getById(userId));
	}

	/**
	 * 获取用户
	 *
	 * @param tenantId 租户id
	 * @param account  账号名
	 * @return
	 */
	public static User getUser(String tenantId, String account) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ACCOUNT, tenantId + StringPool.DASH + account, () -> userService.userByAccount(tenantId, account));
	}

}
