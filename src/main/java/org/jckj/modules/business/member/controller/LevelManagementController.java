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
package org.jckj.modules.business.member.controller;
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
import org.jckj.modules.business.member.entity.LevelManagement;
import org.jckj.modules.business.member.vo.LevelManagementVO;
import org.jckj.modules.business.member.service.ILevelManagementService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 会员等级管理 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("member/levelmanagement")
@Api(value = "会员等级管理", tags = "会员等级管理接口")
public class LevelManagementController extends JcKjController {

	private final ILevelManagementService levelManagementService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入levelManagement")
	public R<LevelManagement> detail(LevelManagement levelManagement) {
		LevelManagement detail = levelManagementService.getOne(Condition.getQueryWrapper(levelManagement));
		return R.data(detail);
	}

	/**
	 * 分页 会员等级管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入levelManagement")
	public R<IPage<LevelManagement>> list(LevelManagement levelManagement, Query query) {
		IPage<LevelManagement> pages = levelManagementService.page(Condition.getPage(query), Condition.getQueryWrapper(levelManagement));
		return R.data(pages);
	}

	/**
	 * 自定义分页 会员等级管理
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入levelManagement")
	public R<IPage<LevelManagementVO>> page(LevelManagementVO levelManagement, Query query) {
		IPage<LevelManagementVO> pages = levelManagementService.selectLevelManagementPage(Condition.getPage(query), levelManagement);
		return R.data(pages);
	}

	/**
	 * 新增 会员等级管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入levelManagement")
	public R save(@Valid @RequestBody LevelManagement levelManagement) {
		return R.status(levelManagementService.save(levelManagement));
	}

	/**
	 * 修改 会员等级管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入levelManagement")
	public R update(@Valid @RequestBody LevelManagement levelManagement) {
		return R.status(levelManagementService.updateById(levelManagement));
	}

	/**
	 * 新增或修改 会员等级管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入levelManagement")
	public R submit(@Valid @RequestBody LevelManagement levelManagement) {
		return R.status(levelManagementService.saveOrUpdate(levelManagement));
	}


	/**
	 * 删除 会员等级管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(levelManagementService.deleteLogic(Func.toLongList(ids)));
	}


}
