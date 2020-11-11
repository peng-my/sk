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
package org.jckj.modules.business.translation.service;

import org.jckj.modules.business.translation.entity.Translate;
import org.jckj.modules.business.translation.vo.TranslateVO;
import org.jckj.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 是否开启翻译 服务类
 *
 * @author BladeX
 * @since 2020-10-14
 */
public interface ITranslateService extends BaseService<Translate> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param translate
	 * @return
	 */
	IPage<TranslateVO> selectTranslatePage(IPage<TranslateVO> page, TranslateVO translate);
	/**
	 * 开启关闭字幕,是否是会员
	 */
	boolean isTranslate();
}
