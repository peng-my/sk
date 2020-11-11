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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jckj.core.log.exception.ServiceException;
import org.jckj.modules.business.user.entity.Subuser;
import org.jckj.modules.business.user.mapper.SubuserMapper;
import org.jckj.modules.business.user.service.ISubuserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 子账号表 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
public class SubuserServiceImpl extends ServiceImpl<SubuserMapper, Subuser> implements ISubuserService {
	/**
	 * 新增父子账号关系
	 *
	 * @param subuser
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean add(Subuser subuser) {
		if (subuser!=null){
			 baseMapper.insert(subuser);
		}else {
			throw new ServiceException("对象为空!");
		}
		return true;
	}

	/**
	 * 查询父子关系 可以用主用户id或者子用户id来查询
	 *
	 * @param fkMainUserId
	 * @param fkSonUserId
	 */
	@Override
	public List<Subuser> querySubuser(Long fkMainUserId, Long fkSonUserId) {
		List<Subuser> subusers = baseMapper.selectList(new QueryWrapper<Subuser>().eq(fkMainUserId != null, "fk_main_user_id", fkMainUserId)
			.eq(fkSonUserId != null, "fk_son_user_id", fkSonUserId));
		return subusers;
	}

	/**
	 * 排除某个字段查询测试
	 */
	@Override
	public Subuser getAll() {
		QueryWrapper<Subuser> companyInfoQueryWrapper=new QueryWrapper<>();
		companyInfoQueryWrapper.select(Subuser.class ,
			info->!info.getColumn().equals("fk_main_user_id")
			&&!info.getColumn().equals("fk_son_user_id")
		);
		List<Subuser> subusers = baseMapper.selectList(companyInfoQueryWrapper);
		return null;
	}

//	@Override
//	public IPage<SubuserVO> selectSubuserPage(IPage<SubuserVO> page, SubuserVO subuser) {
//		return page.setRecords(baseMapper.selectSubuserPage(page, subuser));
//	}

}
