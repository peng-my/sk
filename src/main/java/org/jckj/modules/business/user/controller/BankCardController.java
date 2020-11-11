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
import org.jckj.modules.business.user.entity.BankCard;
import org.jckj.modules.business.user.vo.BankCardVO;
import org.jckj.modules.business.user.service.IBankCardService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 新增银行卡 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("newbankcard/bankcard")
@Api(value = "新增银行卡", tags = "新增银行卡接口")
public class BankCardController extends JcKjController {

	private final IBankCardService bankCardService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入bankCard")
	public R<BankCard> detail(BankCard bankCard) {
		BankCard detail = bankCardService.getOne(Condition.getQueryWrapper(bankCard));
		return R.data(detail);
	}

	/**
	 * 分页 新增银行卡
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入bankCard")
	public R<IPage<BankCard>> list(BankCard bankCard, Query query) {
		IPage<BankCard> pages = bankCardService.page(Condition.getPage(query), Condition.getQueryWrapper(bankCard));
		return R.data(pages);
	}

	/**
	 * 自定义分页 新增银行卡
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入bankCard")
	public R<IPage<BankCardVO>> page(BankCardVO bankCard, Query query) {
		IPage<BankCardVO> pages = bankCardService.selectBankCardPage(Condition.getPage(query), bankCard);
		return R.data(pages);
	}

	/**
	 * 新增 新增银行卡
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入bankCard")
	public R save(@Valid @RequestBody BankCard bankCard) {
		return R.status(bankCardService.save(bankCard));
	}

	/**
	 * 修改 新增银行卡
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入bankCard")
	public R update(@Valid @RequestBody BankCard bankCard) {
		return R.status(bankCardService.updateById(bankCard));
	}

	/**
	 * 新增或修改 新增银行卡
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入bankCard")
	public R submit(@Valid @RequestBody BankCard bankCard) {
		return R.status(bankCardService.saveOrUpdate(bankCard));
	}


	/**
	 * 删除 新增银行卡
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bankCardService.deleteLogic(Func.toLongList(ids)));
	}


}
