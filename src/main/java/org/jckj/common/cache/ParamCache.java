package org.jckj.common.cache;

import org.jckj.core.cache.utils.CacheUtil;
import org.jckj.core.tool.utils.SpringUtil;
import org.jckj.modules.system.entity.Param;
import org.jckj.modules.system.service.IParamService;

import static org.jckj.core.cache.constant.CacheConstant.PARAM_CACHE;


public class ParamCache {

	private static final String PARAM_ID = "param:id:";
	private static final String PARAM_VALUE = "param:value:";

	private static final IParamService paramService;

	static {
		paramService = SpringUtil.getBean(IParamService.class);
	}

	/**
	 * 获取参数实体
	 *
	 * @param id 主键
	 * @return Param
	 */
	public static Param getById(Long id) {
		return CacheUtil.get(PARAM_CACHE, PARAM_ID, id, () -> paramService.getById(id));
	}

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数值
	 * @return String
	 */
	public static String getValue(String paramKey) {
		return CacheUtil.get(PARAM_CACHE, PARAM_VALUE, paramKey, () -> paramService.getValue(paramKey));
	}

}
