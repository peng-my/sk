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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.Charsets;
import org.jckj.core.boot.ctrl.JcKjController;
import org.jckj.core.mp.support.Condition;
import org.jckj.core.mp.support.Query;
import org.jckj.core.tool.api.R;
import org.jckj.core.tool.utils.Func;
import org.jckj.modules.business.translateapi.api.YouDaoApi;
import org.jckj.modules.business.translation.dto.RecordsDTO;
import org.jckj.modules.business.translation.entity.Records;
import org.jckj.modules.business.translation.service.IRecordsService;
import org.jckj.modules.business.translation.vo.RecordsVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 翻译记录 控制器
 *
 * @author BladeX
 * @since 2020-10-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("translationrecords/records")
@Api(value = "翻译记录", tags = "翻译记录接口")
public class RecordsController extends JcKjController {

	private final IRecordsService recordsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入records")
	public R<Records> detail(Records records) {
		Records detail = recordsService.getOne(Condition.getQueryWrapper(records));
		return R.data(detail);
	}

	/**
	 * 分页 翻译记录
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入records")
	public R<IPage<Records>> list(Records records, Query query) {
		IPage<Records> pages = recordsService.page(Condition.getPage(query), Condition.getQueryWrapper(records));
		return R.data(pages);
	}

	/**
	 * 自定义分页 翻译记录
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入records")
	public R<IPage<RecordsVO>> page(RecordsVO records, Query query) {
		IPage<RecordsVO> pages = recordsService.selectRecordsPage(Condition.getPage(query), records);
		return R.data(pages);
	}

	/**
	 * 新增 翻译记录
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入records")
	public R save(@Valid @RequestBody Records records) {
		return R.status(recordsService.save(records));
	}

	/**
	 * 修改 翻译记录
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入records")
	public R update(@Valid @RequestBody Records records) {
		return R.status(recordsService.updateById(records));
	}

	/**
	 * 新增或修改 翻译记录
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入records")
	public R submit(@Valid @RequestBody Records records) {
		return R.status(recordsService.saveOrUpdate(records));
	}


	/**
	 * 删除 翻译记录
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(recordsService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 记录翻译数据
	 */
	@PostMapping("/record")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "记录翻译数据", notes = "传入recordsDTO")
	public R submit(@Valid @RequestBody RecordsDTO recordsDTO) {
		return R.data(recordsService.record(recordsDTO));
	}

	/**
	 * 使用明细下拉框查询
	 */
	@GetMapping("/getQuery")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "使用明细下拉框查询", notes = "传入recordsDTO")
	public R getQuery(@ApiParam(value = "时间", required = false) @RequestParam (required = false) String date,
					  @ApiParam(value = "账号", required = false) @RequestParam (required = false) String account,
					  @ApiParam(value = "类型 1主 2子", required = false) @RequestParam (required = false) String type) {
		return R.data(recordsService.getQuery(date,account,type));
	}
	/**
	 * 记录翻译数据
	 */
	@PostMapping("/recorddata")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "翻译数据", notes = "传入recordsDTO")
	public R recorddata(String type,String key ,String from,String to,String data,MultipartFile file) throws Exception {
		return R.data(recordsService.recordData(type,key,from,to,data,file));
	}
	/**
	 * 记录翻译数据
	 */
	@PostMapping("/file")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "file", notes = "file")
	public void file (String type, String key , MultipartFile file, HttpServletResponse response) throws Exception {
		String[] split = key.split(",");
		String file1 = "";
		if (split.length == 2) {
			file1 = YouDaoApi.file(file, split[0], split[1]);
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding(Charsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + "1.docx");//设置下载的压缩文件名称
			OutputStream output = response.getOutputStream();
			FileInputStream fis = null;
			try {
				byte[] buffer = file1.getBytes();
				int n;
				while ((n = fis.read(buffer, 0, buffer.length)) != -1) {
					output.write(buffer, 0, n);

				}
			} catch (Exception e) {

			} finally {
				try {
					fis.close();
					output.flush();
					output.close();
				} catch (IOException e) {

				} finally {

				}
			}

		}
	}
//	/**
//	 * 记录翻译数据
//	 */
//	@PostMapping("/file")
//	@ApiOperationSupport(order = 8)
//	@ApiOperation(value = "文件翻译", notes = "file")
//	public R recorddata(MultipartFile file, String from, String to, MultipartFile file) throws Exception {
//		return R.data(recordsService.recordData(type,data,to,file));
//	}
}
