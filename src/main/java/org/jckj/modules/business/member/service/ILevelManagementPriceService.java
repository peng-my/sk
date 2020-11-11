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
package org.jckj.modules.business.member.service;

import org.jckj.modules.business.member.entity.LevelManagementPrice;
import org.jckj.modules.business.member.vo.LevelManagementPriceVO;
import org.jckj.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 会员等级管理价格表 服务类
 *
 * @author BladeX
 * @since 2020-10-20
 */
public interface ILevelManagementPriceService extends BaseService<LevelManagementPrice> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param levelManagementPrice
	 * @return
	 */
	IPage<LevelManagementPriceVO> selectLevelManagementPricePage(IPage<LevelManagementPriceVO> page, LevelManagementPriceVO levelManagementPrice);

}
