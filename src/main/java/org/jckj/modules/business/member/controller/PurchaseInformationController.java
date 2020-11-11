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
import org.jckj.modules.business.member.entity.PurchaseInformation;
import org.jckj.modules.business.member.vo.PurchaseInformationVO;
import org.jckj.modules.business.member.service.IPurchaseInformationService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 会员购买信息 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("member/purchaseinformation")
@Api(value = "会员购买信息", tags = "会员购买信息接口")
public class PurchaseInformationController extends JcKjController {

	private final IPurchaseInformationService purchaseInformationService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入purchaseInformation")
	public R<PurchaseInformation> detail(PurchaseInformation purchaseInformation) {
		PurchaseInformation detail = purchaseInformationService.getOne(Condition.getQueryWrapper(purchaseInformation));
		return R.data(detail);
	}

	/**
	 * 分页 会员购买信息
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入purchaseInformation")
	public R<IPage<PurchaseInformation>> list(PurchaseInformation purchaseInformation, Query query) {
		IPage<PurchaseInformation> pages = purchaseInformationService.page(Condition.getPage(query), Condition.getQueryWrapper(purchaseInformation));
		return R.data(pages);
	}

	/**
	 * 自定义分页 会员购买信息
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入purchaseInformation")
	public R<IPage<PurchaseInformationVO>> page(PurchaseInformationVO purchaseInformation, Query query) {
		IPage<PurchaseInformationVO> pages = purchaseInformationService.selectPurchaseInformationPage(Condition.getPage(query), purchaseInformation);
		return R.data(pages);
	}

	/**
	 * 新增 会员购买信息
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入purchaseInformation")
	public R save(@Valid @RequestBody PurchaseInformation purchaseInformation) {
		return R.status(purchaseInformationService.save(purchaseInformation));
	}

	/**
	 * 修改 会员购买信息
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入purchaseInformation")
	public R update(@Valid @RequestBody PurchaseInformation purchaseInformation) {
		return R.status(purchaseInformationService.updateById(purchaseInformation));
	}

	/**
	 * 新增或修改 会员购买信息
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入purchaseInformation")
	public R submit(@Valid @RequestBody PurchaseInformation purchaseInformation) {
		return R.status(purchaseInformationService.saveOrUpdate(purchaseInformation));
	}


	/**
	 * 删除 会员购买信息
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(purchaseInformationService.deleteLogic(Func.toLongList(ids)));
	}


}
