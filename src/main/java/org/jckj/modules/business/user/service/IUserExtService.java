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

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.core.mp.base.BaseService;
import org.jckj.modules.business.user.dto.UserExtDTO;
import org.jckj.modules.business.user.entity.UserExt;
import org.jckj.modules.business.user.vo.UserAdminExtVO;
import org.jckj.modules.business.user.vo.UserExtVO;
import org.jckj.modules.business.user.vo.UserManageExtVO;

/**
 * 用户扩展 服务类
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface IUserExtService extends BaseService<UserExt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userExtVO
	 * @return
	 */
	IPage<UserExtVO> selectUserPage(IPage<UserExtVO> page, UserExtVO userExtVO);
	/**
	 * 用户中心
	 *
	 * @param type 1为主 2为子
	 * @return
	 */
	UserExtVO getInfo(String type);
	/**
	 * 修改昵称
	 */
	boolean updataName(String name);
	/**
	 * 添加子账号
	 */
	boolean submitOrUpdata(UserExt userExt);
	/**
	 * 子账号分页
	 */
	IPage<UserExt> getIpage(IPage<UserExt> page,UserExt userExt);
	/**
	 * 重置密码
	 */
	boolean updataPassword(String password,String newPassword);
	/**
	 * 用户注册
	 */
	boolean register(UserExtDTO userExtDTO);
	/**
	 * 重置密码
	 */
	boolean resetPassword(UserExtDTO userExtDTO);
	/**
	 * 自定义分页用户管理
	 *
	 * @param page
	 * @param userManageExtVO
	 * @return
	 */
	IPage<UserManageExtVO> selectUserManageExtVOPage(IPage<UserManageExtVO> page, UserManageExtVO userManageExtVO);
	
	/**
	 * 封禁
	 */
	boolean hasBan(Long userId);

	/**
	 * 自定义管理员分页
	 */
	IPage<UserAdminExtVO> selectAdminIpage(IPage<UserAdminExtVO> page,String account);
	/**
	 * 保存头像
	 */
	boolean saveHeadPortrait(String url);

	/**
	 * 获取签命
	 */
	String generateUserSig(String userId);
}
