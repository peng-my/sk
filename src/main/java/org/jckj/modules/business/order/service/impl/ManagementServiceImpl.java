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
package org.jckj.modules.business.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.mp.base.BaseEntity;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.BeanUtil;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.vo.ManagementVO;
import org.jckj.modules.business.order.mapper.ManagementMapper;
import org.jckj.modules.business.order.service.IManagementService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.modules.business.test.controller.SnowflakeIdWorker;
import org.jckj.modules.business.translation.entity.Records;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 订单管理 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
public class ManagementServiceImpl extends BaseServiceImpl<ManagementMapper, Management> implements IManagementService {

	@Override
	public IPage<ManagementVO> selectManagementPage(IPage<ManagementVO> page, ManagementVO management) {
		return page.setRecords(baseMapper.selectManagementPage(page, management));
	}

	/**
	 * 购买产品
	 *
	 * @param managementVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String pay(ManagementVO managementVO) {
		String code=null;
		if (managementVO!=null){
			if (managementVO.getId()!=null){
				Management management = new Management();
				SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
				String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
				String seconds = new SimpleDateFormat("HHmmss").format(new Date());
				//订单组成 前两位 SK固定
				//中间有年月日时分秒组成
				//后面由雪花算法得uuid组成
				code="SK"+date+seconds+idWorker;
				management.setOrderCode(code);
				BeanUtil.copyProperties(managementVO,managementVO);
				BaseEntityUtils.resolveEntity(management);
				baseMapper.insert(management);
				code=management.getOrderCode();
			}else {
				throw new ServiceException("订单已经过期,请重新购买!");
			}
		}
		return code;
	}

	/**
	 * 订单下拉框查询
	 *
	 * @param date
	 * @param payType 订单状态
	 */
	@Override
	public List<Management> getQuery(String date, String payType) {
		Date geParse = null;
		Date leParse = null;
		if (StringUtil.isNoneBlank(date)){
			String ge=date+"-01";
			String le=date+"-31";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				geParse = sdf.parse(ge);
				leParse = sdf.parse(le);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		JcKjUser user = AuthUtil.getUser();
		List<Management> managements = baseMapper.selectList(new QueryWrapper<Management>().ge(StringUtil.isNoneBlank(date), "create_time", geParse).le(StringUtil.isNoneBlank(date), "create_time", leParse)
			.eq(StringUtil.isNoneBlank(payType), "order_status", Integer.valueOf(payType)).eq("create_user", user.getUserId()).orderByDesc("update_time"));
		return managements;
	}

	/**
	 * 订单管理分页
	 *
	 * @param page
	 * @param managementVO
	 */
	@Override
	public IPage<ManagementVO> selectManagementVOPage(IPage<ManagementVO> page, ManagementVO managementVO) {
		return page.setRecords(baseMapper.selectManagementVOPage(page,managementVO));
	}

}
