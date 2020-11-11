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
package org.jckj.modules.business.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.modules.business.member.entity.LevelManagement;
import org.jckj.modules.business.member.entity.PurchaseInformation;
import org.jckj.modules.business.member.mapper.LevelManagementMapper;
import org.jckj.modules.business.member.mapper.PurchaseInformationMapper;
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.mapper.ManagementMapper;
import org.jckj.modules.business.pay.entity.Pay;
import org.jckj.modules.business.pay.utils.PayUtils;
import org.jckj.modules.business.pay.vo.PayVO;
import org.jckj.modules.business.pay.mapper.PayMapper;
import org.jckj.modules.business.pay.service.IPayService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付表 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
@AllArgsConstructor
public class PayServiceImpl extends BaseServiceImpl<PayMapper, Pay> implements IPayService {
	private ManagementMapper managementMapper;
	private LevelManagementMapper levelManagementMapper;
	@Override
	public IPage<PayVO> selectPayPage(IPage<PayVO> page, PayVO pay) {
		return page.setRecords(baseMapper.selectPayPage(page, pay));
	}

	/**
	 * 调用支付接口
	 *
	 * @param orderCode
	 */
	@Override
	public void launchPay(String orderCode,String payType, HttpServletResponse response) throws IOException, AlipayApiException {
		//查询订单
		Management management = managementMapper.selectOne(new QueryWrapper<Management>().eq("order_code", orderCode));
		if (management!=null){
			//查询支付类型
			if (payType.equals(management.getPayType())){
				Integer integer = Integer.valueOf(payType);
				//查询会员购买等级信息
				Long fkMemberLevelManagementId = management.getFkMemberLevelManagementId();
				//查询购买信息
				LevelManagement levelManagement = levelManagementMapper.selectById(fkMemberLevelManagementId);
				//购买月份
				Integer monthOfPurchase = management.getMonthOfPurchase();
				//价格
				Double price = levelManagement.getPrice();
				//总价
				String totalAmount=String.valueOf(price*monthOfPurchase);
				//描述
				String subject=monthOfPurchase+"个月";
				if (levelManagement!=null){
					PayUtils payUtils=new PayUtils();
					switch (integer){
						//支付宝
						case 1:
							payUtils.aliPay(orderCode,totalAmount,subject,response);
							break;
						//微信
						case 2:
							break;
						//软银
						case 3:
							break;
						default:
							break;
					}
				}else {
					throw new ServiceException("无法查询到该产品,请联系管理员!");
				}

			}else {
				throw new ServiceException("当前支付类型和订单支付类型不符合!");
			}

		}
	}

}
