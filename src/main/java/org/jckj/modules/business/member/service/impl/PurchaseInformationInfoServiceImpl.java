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
package org.jckj.modules.business.member.service.impl;

import org.jckj.modules.business.member.entity.PurchaseInformationInfo;
import org.jckj.modules.business.member.vo.PurchaseInformationInfoVO;
import org.jckj.modules.business.member.mapper.PurchaseInformationInfoMapper;
import org.jckj.modules.business.member.service.IPurchaseInformationInfoService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 会员购买信息记录表 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
public class PurchaseInformationInfoServiceImpl extends BaseServiceImpl<PurchaseInformationInfoMapper, PurchaseInformationInfo> implements IPurchaseInformationInfoService {

	@Override
	public IPage<PurchaseInformationInfoVO> selectPurchaseInformationInfoPage(IPage<PurchaseInformationInfoVO> page, PurchaseInformationInfoVO purchaseInformationInfo) {
		return page.setRecords(baseMapper.selectPurchaseInformationInfoPage(page, purchaseInformationInfo));
	}

}
