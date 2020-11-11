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
import org.jckj.modules.business.member.entity.PackageBalance;
import org.jckj.modules.business.member.vo.PackageBalanceVO;
import org.jckj.modules.business.member.service.IPackageBalanceService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 会员套餐余额表 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("member/packagebalance")
@Api(value = "会员套餐余额表", tags = "会员套餐余额表接口")
public class PackageBalanceController extends JcKjController {

	private final IPackageBalanceService packageBalanceService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入packageBalance")
	public R<PackageBalance> detail(PackageBalance packageBalance) {
		PackageBalance detail = packageBalanceService.getOne(Condition.getQueryWrapper(packageBalance));
		return R.data(detail);
	}

	/**
	 * 分页 会员套餐余额表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入packageBalance")
	public R<IPage<PackageBalance>> list(PackageBalance packageBalance, Query query) {
		IPage<PackageBalance> pages = packageBalanceService.page(Condition.getPage(query), Condition.getQueryWrapper(packageBalance));
		return R.data(pages);
	}

	/**
	 * 自定义分页 会员套餐余额表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入packageBalance")
	public R<IPage<PackageBalanceVO>> page(PackageBalanceVO packageBalance, Query query) {
		IPage<PackageBalanceVO> pages = packageBalanceService.selectPackageBalancePage(Condition.getPage(query), packageBalance);
		return R.data(pages);
	}

	/**
	 * 新增 会员套餐余额表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入packageBalance")
	public R save(@Valid @RequestBody PackageBalance packageBalance) {
		return R.status(packageBalanceService.save(packageBalance));
	}

	/**
	 * 修改 会员套餐余额表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入packageBalance")
	public R update(@Valid @RequestBody PackageBalance packageBalance) {
		return R.status(packageBalanceService.updateById(packageBalance));
	}

	/**
	 * 新增或修改 会员套餐余额表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入packageBalance")
	public R submit(@Valid @RequestBody PackageBalance packageBalance) {
		return R.status(packageBalanceService.saveOrUpdate(packageBalance));
	}


	/**
	 * 删除 会员套餐余额表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(packageBalanceService.deleteLogic(Func.toLongList(ids)));
	}


}
