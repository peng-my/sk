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
package org.jckj.modules.business.order.controller;
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
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.order.vo.ManagementVO;
import org.jckj.modules.business.order.service.IManagementService;
import org.jckj.core.boot.ctrl.JcKjController;

import java.util.List;

/**
 * 订单管理 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("ordermanagement/management")
@Api(value = "订单管理", tags = "订单管理接口")
public class ManagementController extends JcKjController {

	private final IManagementService managementService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入management")
	public R<Management> detail(Management management) {
		Management detail = managementService.getOne(Condition.getQueryWrapper(management));
		return R.data(detail);
	}

//	/**
//	 * 分页 订单管理
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入management")
//	public R<IPage<Management>> list(Management management, Query query) {
//		IPage<Management> pages = managementService.page(Condition.getPage(query), Condition.getQueryWrapper(management));
//		return R.data(pages);
//	}
//
//	/**
//	 * 自定义分页 订单管理
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入management")
//	public R<IPage<ManagementVO>> page(ManagementVO management, Query query) {
//		IPage<ManagementVO> pages = managementService.selectManagementPage(Condition.getPage(query), management);
//		return R.data(pages);
//	}

	/**
	 * 新增 订单管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入management")
	public R save(@Valid @RequestBody Management management) {
		return R.status(managementService.save(management));
	}

	/**
	 * 修改 订单管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入management")
	public R update(@Valid @RequestBody Management management) {
		return R.status(managementService.updateById(management));
	}

	/**
	 * 新增或修改 订单管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入management")
	public R submit(@Valid @RequestBody Management management) {
		return R.status(managementService.saveOrUpdate(management));
	}


	/**
	 * 删除 订单管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(managementService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 购买产品生成订单
	 */

	@PostMapping("/pay")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "购买产品生成订单", notes = "传入managementVO")
	public R pay(@Valid @RequestBody ManagementVO managementVO) {
		return R.data(managementService.pay(managementVO));
	}

	/**
	 * 订单下拉框查询
	 */
	@GetMapping("/getQuery")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "订单下拉框查询", notes = "date,payType")
	public R<List<Management>> getQuery(String date,String payType) {
		List<Management> query = managementService.getQuery(date, payType);
		return R.data(query);
	}

	/**
	 * 自定义分页 订单管理
	 */
	@GetMapping("/selectManagementVOPage")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "分页", notes = "传入management")
	public R<IPage<ManagementVO>> selectManagementVOPage(ManagementVO management, Query query) {
		IPage<ManagementVO> pages = managementService.selectManagementVOPage(Condition.getPage(query), management);
		return R.data(pages);
	}
}
