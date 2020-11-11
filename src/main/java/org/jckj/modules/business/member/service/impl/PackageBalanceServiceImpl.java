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

import org.jckj.modules.business.member.entity.PackageBalance;
import org.jckj.modules.business.member.vo.PackageBalanceVO;
import org.jckj.modules.business.member.mapper.PackageBalanceMapper;
import org.jckj.modules.business.member.service.IPackageBalanceService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 会员套餐余额表 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
public class PackageBalanceServiceImpl extends BaseServiceImpl<PackageBalanceMapper, PackageBalance> implements IPackageBalanceService {

	@Override
	public IPage<PackageBalanceVO> selectPackageBalancePage(IPage<PackageBalanceVO> page, PackageBalanceVO packageBalance) {
		return page.setRecords(baseMapper.selectPackageBalancePage(page, packageBalance));
	}

}
