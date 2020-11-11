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
package org.jckj.modules.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jckj.modules.business.user.entity.UserExt;
import org.jckj.modules.business.user.vo.UserAdminExtVO;
import org.jckj.modules.business.user.vo.UserExtVO;
import org.jckj.modules.business.user.vo.UserManageExtVO;

import java.util.Date;
import java.util.List;

/**
 * 新增银行卡 Mapper 接口
 *
 * @author BladeX
 * @since 2020-10-13
 */
public interface UserExtMapper extends BaseMapper<UserExt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userExtVO
	 * @return
	 */
	List<UserExtVO> selectBankCardPage(IPage page, UserExtVO userExtVO);

	/**
	 * 自定义分页用户管理
	 */
	List<UserManageExtVO> selectUserManageExtVOPage(IPage page,@Param("userManageExtVO")UserManageExtVO userManageExtVO);

	/**
	 * 查询指定用户
	 */
	UserExtVO selectUser(Long id);

	/**
	 * 启用禁用账户
	 */
	boolean updataIsBan(@Param("isDeleted") Integer isDeleted,@Param("id") Long id);
	/**
	 * 管理员查询和分页
	 */
	List<UserAdminExtVO> selectAdminIpage(IPage page,@Param("roleId")String roleId,@Param("account")String account);
	/**
	 * 查询登陆时间
	 */
	Date selectLoginTime(@Param("account")String account);
}
