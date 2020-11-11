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
package org.jckj.modules.business.user.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jckj.modules.business.user.entity.RegistrationGift;
import org.jckj.modules.business.user.vo.RegistrationGiftVO;
import org.jckj.modules.business.user.service.IRegistrationGiftService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 用户注册赠送 控制器
 *
 * @author BladeX
 * @since 2020-10-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("userregistrationgift/registrationgift")
@Api(value = "用户注册赠送", tags = "用户注册赠送接口")
public class RegistrationGiftController extends JcKjController {

	private final IRegistrationGiftService registrationGiftService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入registrationGift")
//	public R<RegistrationGift> detail(RegistrationGift registrationGift) {
//		RegistrationGift detail = registrationGiftService.getOne(Condition.getQueryWrapper(registrationGift));
//		return R.data(detail);
//	}
//
//	/**
//	 * 分页 用户注册赠送
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入registrationGift")
//	public R<IPage<RegistrationGift>> list(RegistrationGift registrationGift, Query query) {
//		IPage<RegistrationGift> pages = registrationGiftService.page(Condition.getPage(query), Condition.getQueryWrapper(registrationGift));
//		return R.data(pages);
//	}
//
//	/**
//	 * 自定义分页 用户注册赠送
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入registrationGift")
//	public R<IPage<RegistrationGiftVO>> page(RegistrationGiftVO registrationGift, Query query) {
//		IPage<RegistrationGiftVO> pages = registrationGiftService.selectRegistrationGiftPage(Condition.getPage(query), registrationGift);
//		return R.data(pages);
//	}
//
//	/**
//	 * 新增 用户注册赠送
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入registrationGift")
//	public R save(@Valid @RequestBody RegistrationGift registrationGift) {
//		return R.status(registrationGiftService.save(registrationGift));
//	}
//
//	/**
//	 * 修改 用户注册赠送
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入registrationGift")
//	public R update(@Valid @RequestBody RegistrationGift registrationGift) {
//		return R.status(registrationGiftService.updateById(registrationGift));
//	}
//
//	/**
//	 * 新增或修改 用户注册赠送
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入registrationGift")
//	public R submit(@Valid @RequestBody RegistrationGift registrationGift) {
//		return R.status(registrationGiftService.saveOrUpdate(registrationGift));
//	}
//
//
//	/**
//	 * 删除 用户注册赠送
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(registrationGiftService.deleteLogic(Func.toLongList(ids)));
//	}

	/**
	 * 设置礼卷金额
	 */
	@PostMapping("/submitRegistrationGift")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "设置礼卷金额", notes = "传入registrationGift")
	public R submitRegistrationGift(@Valid @RequestBody RegistrationGift registrationGift) {
		return R.status(registrationGiftService.submitRegistrationGift(registrationGift));
	}
}
