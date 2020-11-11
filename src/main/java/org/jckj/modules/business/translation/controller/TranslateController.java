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
package org.jckj.modules.business.translation.controller;
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
import org.jckj.modules.business.translation.entity.Translate;
import org.jckj.modules.business.translation.vo.TranslateVO;
import org.jckj.modules.business.translation.service.ITranslateService;
import org.jckj.core.boot.ctrl.JcKjController;

/**
 * 是否开启翻译 控制器
 *
 * @author BladeX
 * @since 2020-10-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("istranslate/translate")
@Api(value = "是否开启翻译", tags = "是否开启翻译接口")
public class TranslateController extends JcKjController {

	private final ITranslateService translateService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入translate")
//	public R<Translate> detail(Translate translate) {
//		Translate detail = translateService.getOne(Condition.getQueryWrapper(translate));
//		return R.data(detail);
//	}
//
//	/**
//	 * 分页 是否开启翻译
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入translate")
//	public R<IPage<Translate>> list(Translate translate, Query query) {
//		IPage<Translate> pages = translateService.page(Condition.getPage(query), Condition.getQueryWrapper(translate));
//		return R.data(pages);
//	}
//
//	/**
//	 * 自定义分页 是否开启翻译
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入translate")
//	public R<IPage<TranslateVO>> page(TranslateVO translate, Query query) {
//		IPage<TranslateVO> pages = translateService.selectTranslatePage(Condition.getPage(query), translate);
//		return R.data(pages);
//	}
//
//	/**
//	 * 新增 是否开启翻译
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入translate")
//	public R save(@Valid @RequestBody Translate translate) {
//		return R.status(translateService.save(translate));
//	}
//
//	/**
//	 * 修改 是否开启翻译
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入translate")
//	public R update(@Valid @RequestBody Translate translate) {
//		return R.status(translateService.updateById(translate));
//	}
//
//	/**
//	 * 新增或修改 是否开启翻译
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入translate")
//	public R submit(@Valid @RequestBody Translate translate) {
//		return R.status(translateService.saveOrUpdate(translate));
//	}
//
//
//	/**
//	 * 删除 是否开启翻译
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(translateService.deleteLogic(Func.toLongList(ids)));
//	}

	/**
	 * 是否开启翻译
	 */
	@PostMapping("/is_translat")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "开启翻译/查询是不是会有", notes = "传入ids")
	public R isTranslate() {
		return R.status(translateService.isTranslate());
	}

}
