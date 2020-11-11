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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.BeanUtil;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.member.entity.*;
import org.jckj.modules.business.member.mapper.*;
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.mapper.ManagementMapper;
import org.jckj.modules.business.pay.entity.Callback;
import org.jckj.modules.business.pay.entity.PayError;
import org.jckj.modules.business.pay.mapper.PayErrorMapper;
import org.jckj.modules.business.pay.payentity.AliPay;
import org.jckj.modules.business.pay.vo.CallbackVO;
import org.jckj.modules.business.pay.mapper.CallbackMapper;
import org.jckj.modules.business.pay.service.ICallbackService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 支付回调表 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
@AllArgsConstructor
public class CallbackServiceImpl extends BaseServiceImpl<CallbackMapper, Callback> implements ICallbackService {
	private ManagementMapper managementMapper;
	private LevelManagementMapper levelManagementMapper;
	private PayErrorMapper payErrorMapper;
	private PurchaseInformationMapper purchaseInformationMapper;
	private PurchaseInformationInfoMapper purchaseInformationInfoMapper;
	private PackageBalanceMapper packageBalanceMapper;
	private PackageBalanceInfoMapper packageBalanceInfoMapper;
	@Override
	public IPage<CallbackVO> selectCallbackPage(IPage<CallbackVO> page, CallbackVO callback) {
		return page.setRecords(baseMapper.selectCallbackPage(page, callback));
	}

	/**
	 * 阿里支付异步回调
	 *
	 * @param aliPay
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean aliPayNotify(AliPay aliPay) {
		String outTradeNo = aliPay.getOutTradeNo();
		Management management = managementMapper.selectOne(new QueryWrapper<Management>().eq("order_code", outTradeNo));
		Callback callback=new Callback();
		if (management!=null){
			//通过订单id查询到购买会员的id
			Long fkMemberLevelManagementId = management.getFkMemberLevelManagementId();
			//购买的会员等级信息
			LevelManagement levelManagement = levelManagementMapper.selectById(fkMemberLevelManagementId);
			if (levelManagement!=null){
				Double price = levelManagement.getPrice();
				Double buyerPayAmount = aliPay.getBuyerPayAmount();
				if (price.equals(buyerPayAmount)){
					BeanUtil.copyProperties(aliPay,callback);
					callback.setFkOrderManagementId(management.getId());
					BaseEntityUtils.resolveEntity(callback);
					baseMapper.insert(callback);
					//支付交易时间
					Date gmtCreate = aliPay.getGmtCreate();
					management.setPayStartDate(gmtCreate);
					//支付付款时间
					Date gmtPayment = aliPay.getGmtPayment();
					management.setPayEndDate(gmtPayment);
					management.setOrderStatus(2);
					BaseEntityUtils.resolveEntity(management);
					managementMapper.insert(management);
					//新增会员购买信息
						//获取用户id
					Long userId = AuthUtil.getUser().getUserId();
						//查询有没有购买过 {通过用户id和等级id}
					PurchaseInformation purchaseInformation = purchaseInformationMapper.selectOne(new QueryWrapper<PurchaseInformation>().eq("create_user", userId).eq("fk_member_level_management_id", fkMemberLevelManagementId));
					if (purchaseInformation!=null){
						//获取购买月份
						Integer monthOfPurchase = management.getMonthOfPurchase();
						purchaseInformation.setFkMemberLevelManagementId(fkMemberLevelManagementId);
						purchaseInformation.setMonthOfPurchase(monthOfPurchase);
						BaseEntityUtils.resolveEntity(purchaseInformation);
						//在购买信息表更新数据
						purchaseInformationMapper.updateById(purchaseInformation);
						//在往购买信息记录表新增数据
						PurchaseInformationInfo purchaseInformationInfo = new PurchaseInformationInfo();
						//购买信息id
						Long purchaseInformationId = purchaseInformation.getId();
						purchaseInformationInfo.setFkMemberPurchaseInformationId(purchaseInformationId);
						purchaseInformationInfo.setSurplusDate(monthOfPurchase);
						purchaseInformationInfo.setOperationType("2");
						BaseEntityUtils.resolveEntity(purchaseInformationInfo);
						purchaseInformationInfoMapper.insert(purchaseInformationInfo);
						//再往会员套餐余额表更新数据
							//先查询数据
						PackageBalance packageBalance = packageBalanceMapper.selectOne(new QueryWrapper<PackageBalance>().eq("create_user", userId).eq("fk_member_purchase_information_id", purchaseInformationId));
						if (packageBalance!=null){
							//会员等级里面的信息
							//会员等级
							String levMembershipLevel=null;
							//语音会议
							Double leveVoiceConference=null;
							//视频会议
							Double leveVideoConferencing=null;
							//字幕翻译
							Double  leveSubtitleTranslation=null;
							//文件翻译
							Double  leveDcumentaryTranslation=null;
							//图片识别及翻译
							Double levImageRecognitionAndTranslation=null;
							//判断获取购买的信息
							if (StringUtil.isNoneBlank(levelManagement.getMembershipLevel())){
								levMembershipLevel = levelManagement.getMembershipLevel();
							}
							if (levelManagement.getVoiceConference()!=null&&levelManagement.getVoiceConference()!=00.00){
								leveVoiceConference = levelManagement.getVoiceConference()*monthOfPurchase;
							}
							if (levelManagement.getVideoConferencing()!=null&&levelManagement.getVideoConferencing()!=00.00){
								leveVideoConferencing = levelManagement.getVideoConferencing()*monthOfPurchase;
							}
							if (levelManagement.getSubtitleTranslation()!=null&&levelManagement.getSubtitleTranslation()!=00.00){
								leveSubtitleTranslation = levelManagement.getSubtitleTranslation()*monthOfPurchase;
							}
							if (levelManagement.getDocumentaryTranslation()!=null&&levelManagement.getDocumentaryTranslation()!=00.00){
								leveDcumentaryTranslation = levelManagement.getDocumentaryTranslation()*monthOfPurchase;
							}
							if (levelManagement.getImageRecognitionAndTranslation()!=null&&levelManagement.getImageRecognitionAndTranslation()!=00.00){
								levImageRecognitionAndTranslation = levelManagement.getImageRecognitionAndTranslation()*monthOfPurchase;
							}
							//会员等级
							String pacMembershipLevel=null;
							//语音会议
							Double pacVoiceConference=null;
							//视频会议
							Double pacVideoConferencing=null;
							//字幕翻译
							Double pacSubtitleTranslation=null;
							//文件翻译
							Double pacDcumentaryTranslation=null;
							//图片识别及翻译
							Double pacImageRecognitionAndTranslation=null;
							//判断并加上购买的信息
							if (StringUtil.isNoneBlank(packageBalance.getMembershipLevel())){
								pacMembershipLevel = levMembershipLevel;
							}
							if (packageBalance.getVoiceConference()!=null&&packageBalance.getVoiceConference()!=00.00){
								pacVoiceConference = packageBalance.getVoiceConference()+leveVoiceConference;
								packageBalance.setVoiceConference(pacVoiceConference);
							}
							if (packageBalance.getVideoConferencing()!=null&&packageBalance.getVideoConferencing()!=00.00){
								pacVideoConferencing = packageBalance.getVideoConferencing()+leveVideoConferencing;
								packageBalance.setVideoConferencing(pacVideoConferencing);
							}
							if (packageBalance.getSubtitleTranslation()!=null&&packageBalance.getSubtitleTranslation()!=00.00){
								pacSubtitleTranslation = packageBalance.getSubtitleTranslation()+leveSubtitleTranslation;
								packageBalance.setSubtitleTranslation(pacSubtitleTranslation);
							}
							if (packageBalance.getDocumentaryTranslation()!=null&&packageBalance.getDocumentaryTranslation()!=00.00){
								pacDcumentaryTranslation = packageBalance.getDocumentaryTranslation()+leveDcumentaryTranslation;
								packageBalance.setDocumentaryTranslation(pacDcumentaryTranslation);
							}
							if (packageBalance.getImageRecognitionAndTranslation()!=null&&packageBalance.getImageRecognitionAndTranslation()!=00.00){
								pacImageRecognitionAndTranslation = packageBalance.getImageRecognitionAndTranslation()+levImageRecognitionAndTranslation;
								packageBalance.setImageRecognitionAndTranslation(pacImageRecognitionAndTranslation);
							}
							//存入数据库
							packageBalance.setFkMemberPurchaseInformationId(purchaseInformationId);
							packageBalance.setMembershipLevel(pacMembershipLevel);
							BaseEntityUtils.resolveEntity(packageBalance);
							//更新套餐余额
							packageBalanceMapper.updateById(packageBalance);
							PackageBalanceInfo packageBalanceInfo=new PackageBalanceInfo();
							BeanUtil.copyProperties(packageBalance,packageBalanceInfo);
							packageBalanceInfo.setId(null);
							packageBalanceInfo.setFkMemberPackageBalanceId(packageBalance.getId());
							packageBalanceInfo.setCreateDept(null);
							packageBalanceInfo.setCreateTime(null);
							packageBalanceInfo.setCreateUser(purchaseInformation.getCreateUser());
							packageBalanceInfo.setUpdateTime(null);
							packageBalanceInfo.setUpdateUser(null);
							packageBalanceInfo.setStatus(null);
							packageBalanceInfo.setIsDeleted(null);
							BaseEntityUtils.resolveEntity(packageBalanceInfo);
							//新增套餐余额记录表
							packageBalanceInfoMapper.insert(packageBalanceInfo);
						}else {
							throw new ServiceException("无套餐记录,请联系管理员");
						}
					}else {
						//获取购买月份
						Integer monthOfPurchase = management.getMonthOfPurchase();
						purchaseInformation.setFkMemberLevelManagementId(fkMemberLevelManagementId);
						purchaseInformation.setMonthOfPurchase(monthOfPurchase);
						BaseEntityUtils.resolveEntity(purchaseInformation);
						//在购买信息表更新数据
						purchaseInformationMapper.insert(purchaseInformation);
						//在往购买信息记录表新增数据
						PurchaseInformationInfo purchaseInformationInfo = new PurchaseInformationInfo();
						//购买信息id
						Long purchaseInformationId = purchaseInformation.getId();
						purchaseInformationInfo.setFkMemberPurchaseInformationId(purchaseInformationId);
						purchaseInformationInfo.setSurplusDate(monthOfPurchase);
						purchaseInformationInfo.setOperationType("1");
						BaseEntityUtils.resolveEntity(purchaseInformationInfo);
						purchaseInformationInfoMapper.insert(purchaseInformationInfo);
						//会员等级里面的信息
						//会员等级
						String levMembershipLevel=null;
						//语音会议
						Double leveVoiceConference=null;
						//视频会议
						Double leveVideoConferencing=null;
						//字幕翻译
						Double  leveSubtitleTranslation=null;
						//文件翻译
						Double  leveDcumentaryTranslation=null;
						//图片识别及翻译
						Double levImageRecognitionAndTranslation=null;
						//判断获取购买的信息
						if (StringUtil.isNoneBlank(levelManagement.getMembershipLevel())){
							levMembershipLevel = levelManagement.getMembershipLevel();
						}
						if (levelManagement.getVoiceConference()!=null&&levelManagement.getVoiceConference()!=00.00){
							leveVoiceConference = levelManagement.getVoiceConference()*monthOfPurchase;
						}
						if (levelManagement.getVideoConferencing()!=null&&levelManagement.getVideoConferencing()!=00.00){
							leveVideoConferencing = levelManagement.getVideoConferencing()*monthOfPurchase;
						}
						if (levelManagement.getSubtitleTranslation()!=null&&levelManagement.getSubtitleTranslation()!=00.00){
							leveSubtitleTranslation = levelManagement.getSubtitleTranslation()*monthOfPurchase;
						}
						if (levelManagement.getDocumentaryTranslation()!=null&&levelManagement.getDocumentaryTranslation()!=00.00){
							leveDcumentaryTranslation = levelManagement.getDocumentaryTranslation()*monthOfPurchase;
						}
						if (levelManagement.getImageRecognitionAndTranslation()!=null&&levelManagement.getImageRecognitionAndTranslation()!=00.00){
							levImageRecognitionAndTranslation = levelManagement.getImageRecognitionAndTranslation()*monthOfPurchase;
						}
						PackageBalance packageBalance = new PackageBalance();
						packageBalance.setFkMemberPurchaseInformationId(purchaseInformationId);
						packageBalance.setMembershipLevel(levMembershipLevel);
						packageBalance.setVoiceConference(leveVoiceConference);
						packageBalance.setVideoConferencing(leveVideoConferencing);
						packageBalance.setSubtitleTranslation(leveSubtitleTranslation);
						packageBalance.setDocumentaryTranslation(leveDcumentaryTranslation);
						packageBalance.setImageRecognitionAndTranslation(levImageRecognitionAndTranslation);
						BaseEntityUtils.resolveEntity(packageBalance);
						//新增套餐余额
						packageBalance.setCreateUser(management.getCreateUser());
						packageBalanceMapper.insert(packageBalance);
						PackageBalanceInfo packageBalanceInfo=new PackageBalanceInfo();
						BeanUtil.copyProperties(packageBalance,packageBalanceInfo);
						packageBalanceInfo.setId(null);
						packageBalanceInfo.setFkMemberPackageBalanceId(packageBalance.getId());
						packageBalanceInfo.setCreateDept(null);
						packageBalanceInfo.setCreateTime(null);
						packageBalanceInfo.setCreateUser(packageBalance.getCreateUser());
						packageBalanceInfo.setUpdateTime(null);
						packageBalanceInfo.setUpdateUser(null);
						packageBalanceInfo.setStatus(null);
						packageBalanceInfo.setIsDeleted(null);
						BaseEntityUtils.resolveEntity(packageBalanceInfo);
						//新增套餐余额记录表
						packageBalanceInfoMapper.insert(packageBalanceInfo);
					}
				}else {
					BeanUtil.copyProperties(aliPay,callback);
					BaseEntityUtils.resolveEntity(callback);
					baseMapper.insert(callback);
					PayError payError=new PayError();
					payError.setFkPayCallbackId(callback.getId());
					payError.setRemarks("支付金额和商品金额不符合!");
					BaseEntityUtils.resolveEntity(payError);
					payErrorMapper.insert(payError);
					//支付交易时间
					Date gmtCreate = aliPay.getGmtCreate();
					management.setPayStartDate(gmtCreate);
					//支付付款时间
					Date gmtPayment = aliPay.getGmtPayment();
					management.setPayEndDate(gmtPayment);
					management.setOrderStatus(3);
					BaseEntityUtils.resolveEntity(management);
					managementMapper.insert(management);
					throw new ServiceException("所购买的商品不存在,请联系管理员");
				}
			}else {
				BeanUtil.copyProperties(aliPay,callback);
				BaseEntityUtils.resolveEntity(callback);
				baseMapper.insert(callback);
				throw new ServiceException("所购买的商品不存在,请联系管理员");
			}

		}else {
			BeanUtil.copyProperties(aliPay,callback);
			BaseEntityUtils.resolveEntity(callback);
			baseMapper.insert(callback);
			throw new ServiceException("订单不存在,请联系管理员");
		}
		return true;
	}

}
