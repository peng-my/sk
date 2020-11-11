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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.AesUtil;
import org.jckj.modules.business.user.entity.BankCard;
import org.jckj.modules.business.user.vo.BankCardVO;
import org.jckj.modules.business.user.mapper.BankCardMapper;
import org.jckj.modules.business.user.service.IBankCardService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 新增银行卡 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
public class BankCardServiceImpl extends BaseServiceImpl<BankCardMapper, BankCard> implements IBankCardService {

	@Override
	public IPage<BankCardVO> selectBankCardPage(IPage<BankCardVO> page, BankCardVO bankCard) {
		return page.setRecords(baseMapper.selectBankCardPage(page, bankCard));
	}

}
