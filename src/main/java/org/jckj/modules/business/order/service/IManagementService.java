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
package org.jckj.modules.business.order.service;

import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.vo.ManagementVO;
import org.jckj.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 订单管理 服务类
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface IManagementService extends BaseService<Management> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param management
	 * @return
	 */
	IPage<ManagementVO> selectManagementPage(IPage<ManagementVO> page, ManagementVO management);

	/**
	 * 购买产品
	 */
	String pay(ManagementVO managementVO);
	/**
	 * 订单下拉框查询
	 */
	List<Management> getQuery(String date,String payType);
	/**
	 * 订单管理分页
	 */
	IPage<ManagementVO> selectManagementVOPage(IPage<ManagementVO> page, ManagementVO managementVO);
}
