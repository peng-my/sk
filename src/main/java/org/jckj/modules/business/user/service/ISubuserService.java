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
package org.jckj.modules.business.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jckj.modules.business.user.entity.Subuser;

import java.util.List;

/**
 * 子账号表 服务类
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface ISubuserService extends IService<Subuser> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param subuser
//	 * @return
//	 */
//	IPage<SubuserVO> selectSubuserPage(IPage<SubuserVO> page, SubuserVO subuser);
	/**
	 * 新增父子账号关系
	 */
	boolean add(Subuser subuser );
	/**
	 * 查询父子关系 可以用主用户id或者子用户id来查询
	 */
	List<Subuser> querySubuser(Long fkMainUserId, Long fkSonUserId);
	/**
	 * 排除某个字段查询测试
	 */
	Subuser getAll();
}
