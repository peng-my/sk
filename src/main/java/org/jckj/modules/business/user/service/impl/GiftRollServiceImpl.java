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
package org.jckj.modules.business.user.service.impl;

import org.jckj.modules.business.user.entity.GiftRoll;
import org.jckj.modules.business.user.vo.GiftRollVO;
import org.jckj.modules.business.user.mapper.GiftRollMapper;
import org.jckj.modules.business.user.service.IGiftRollService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 会员礼金卷及国籍 服务实现类
 *
 * @author BladeX
 * @since 2020-10-19
 */
@Service
public class GiftRollServiceImpl extends BaseServiceImpl<GiftRollMapper, GiftRoll> implements IGiftRollService {

	@Override
	public IPage<GiftRollVO> selectGiftRollPage(IPage<GiftRollVO> page, GiftRollVO giftRoll) {
		return page.setRecords(baseMapper.selectGiftRollPage(page, giftRoll));
	}

}
