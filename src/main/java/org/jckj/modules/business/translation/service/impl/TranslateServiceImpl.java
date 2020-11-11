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
package org.jckj.modules.business.translation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.modules.business.member.entity.PurchaseInformation;
import org.jckj.modules.business.member.mapper.PurchaseInformationMapper;
import org.jckj.modules.business.translation.entity.Translate;
import org.jckj.modules.business.translation.vo.TranslateVO;
import org.jckj.modules.business.translation.mapper.TranslateMapper;
import org.jckj.modules.business.translation.service.ITranslateService;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.modules.business.user.entity.Subuser;
import org.jckj.modules.business.user.mapper.SubuserMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 是否开启翻译 服务实现类
 *
 * @author BladeX
 * @since 2020-10-14
 */
@Service
@AllArgsConstructor
public class TranslateServiceImpl extends BaseServiceImpl<TranslateMapper, Translate> implements ITranslateService {
	private PurchaseInformationMapper purchaseInformationMapper;
	private SubuserMapper subuserMapper;
	@Override
	public IPage<TranslateVO> selectTranslatePage(IPage<TranslateVO> page, TranslateVO translate) {
		return page.setRecords(baseMapper.selectTranslatePage(page, translate));
	}

	/**
	 * 开启关闭字幕,是否是会员
	 *
	 * @param
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean isTranslate() {
		boolean flag=false;
		//用于查询得用户
		Long userId = AuthUtil.getUser().getUserId();
		Long userId1 = AuthUtil.getUser().getUserId();

		//查询当前用户是不是子账号
		Subuser subuser = subuserMapper.selectOne(new QueryWrapper<Subuser>().eq("fk_son_user_id", userId));
		if (subuser!=null){
			//获取主用户
			userId=subuser.getFkMainUserId();
		}
		//查询购买信息
		List<PurchaseInformation> purchaseInformations = purchaseInformationMapper.selectList(new QueryWrapper<PurchaseInformation>().eq("create_user", userId));
		if (purchaseInformations.size()>0){
			//先查询有没有开启翻译
			Translate getTranslate = baseMapper.selectOne(new QueryWrapper<Translate>().eq("create_user", userId1));
			if (getTranslate!=null){
				Integer integer = Integer.valueOf(getTranslate.getTranslate());
				switch (integer){
					case 1:
						getTranslate.setTranslate("2");
						break;
					case 2:
						getTranslate.setTranslate("1");
						break;
					default:
						break;
				}
				BaseEntityUtils.resolveEntity(getTranslate);
				baseMapper.updateById(getTranslate);
				flag=Integer.getInteger(getTranslate.getTranslate())==1?true:false;
			}else {
				Translate newTranslcat=new Translate();
				newTranslcat.setTranslate("1");
				BaseEntityUtils.resolveEntity(newTranslcat);
				baseMapper.insert(newTranslcat);
				flag=Integer.getInteger(newTranslcat.getTranslate())==1?true:false;
			}
		}else {
			throw new ServiceException("请前往购买会员!");
		}
		return flag;
	}

}
