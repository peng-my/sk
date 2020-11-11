package org.jckj.common.utils;

import org.jckj.core.mp.base.BaseEntity;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * set父类属性
 */

public class BaseEntityUtils {
	public static   <T extends BaseEntity> void resolveEntity(T entity)  {
		try {
			JcKjUser user = AuthUtil.getUser();
			Date now = DateUtil.now();
			if (entity.getId() == null) {
				if (user != null) {
					entity.setCreateUser(user.getUserId());
					entity.setCreateDept(Func.firstLong(user.getDeptId()));
					entity.setUpdateUser(user.getUserId());
				}

				if (entity.getStatus() == null) {
					entity.setStatus(1);
				}

				entity.setCreateTime(now);
			} else if (user != null) {
				entity.setUpdateUser(user.getUserId());
			}

			entity.setUpdateTime(now);
			entity.setIsDeleted(0);
			Field field = ReflectUtil.getField(entity.getClass(), "tenantId");
			if (ObjectUtil.isNotEmpty(field)) {
				Method getTenantId = ClassUtil.getMethod(entity.getClass(), "getTenantId", new Class[0]);
				String tenantId = String.valueOf(getTenantId.invoke(entity));
				if (ObjectUtil.isEmpty(tenantId)) {
					Method setTenantId = ClassUtil.getMethod(entity.getClass(), "setTenantId", new Class[]{String.class});
					setTenantId.invoke(entity, null);
				}
			}

		} catch (Throwable var8) {
			try {
				throw var8;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
