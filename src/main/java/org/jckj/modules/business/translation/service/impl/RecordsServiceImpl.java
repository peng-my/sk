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
package org.jckj.modules.business.translation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.core.secure.JcKjUser;
import org.jckj.core.secure.utils.AuthUtil;
import org.jckj.core.tool.utils.BeanUtil;
import org.jckj.core.tool.utils.StringUtil;
import org.jckj.modules.business.translateapi.api.*;
import org.jckj.modules.business.translation.dto.RecordsDTO;
import org.jckj.modules.business.translation.entity.Records;
import org.jckj.modules.business.translation.mapper.RecordsMapper;
import org.jckj.modules.business.translation.service.IRecordsService;
import org.jckj.modules.business.translation.vo.RecordsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 翻译记录 服务实现类
 *
 * @author BladeX
 * @since 2020-10-13
 */
@Service
@AllArgsConstructor
public class RecordsServiceImpl extends BaseServiceImpl<RecordsMapper, Records> implements IRecordsService {
//	private MongoTemplate mongoTemplate;
	@Override
	public IPage<RecordsVO> selectRecordsPage(IPage<RecordsVO> page, RecordsVO records) {
		return page.setRecords(baseMapper.selectRecordsPage(page, records));
	}

	/**
	 * 记录翻译数据
	 *
	 * @param recordsDTO
	 */
	@Override
	public boolean record(RecordsDTO recordsDTO) {
		return recordTranslation(recordsDTO);
	}

	/**
	 * 使用明细下拉框查询
	 *
	 * @param date
	 * @param account
	 * @param type 1主 2子
	 */
	@Override
	public List<Records> getQuery(String date, String account,String type) {
		Date geParse = null;
		Date leParse = null;
		if (StringUtil.isNoneBlank(date)){
			String ge=date+"-01";
			String le=date+"-31";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				geParse = sdf.parse(ge);
				leParse = sdf.parse(le);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer types = Integer.valueOf(type);
		List<Records> records=new ArrayList<>();
		JcKjUser user = AuthUtil.getUser();
		switch (types){
			case 1:
				 records = baseMapper.selectList(new QueryWrapper<Records>().ge(StringUtil.isNoneBlank(date), "create_time", geParse).le(StringUtil.isNoneBlank(date), "create_time", leParse)
					.eq(StringUtil.isNoneBlank(account), "account", account).eq("create_user",user.getUserId()).orderByDesc("update_time"));
				break;
			case 2:
				records = baseMapper.selectList(new QueryWrapper<Records>().ge(StringUtil.isNoneBlank(date), "create_time", geParse).le(StringUtil.isNoneBlank(date), "create_time", leParse)
					.eq("create_user",user).orderByDesc("update_time"));
				break;
			default:
				break;
		}

		return records;
	}

	/**
	 * 翻译数据
	 *
	 * @param recordsDTO
	 */
	@Override
	public String recordData(String type,String key ,String from,String to,String data,MultipartFile file) throws Exception {
		String rm="";
		Integer types=Integer.valueOf(type);
		RecordsDTO recordsDTO=new RecordsDTO();
		switch (types){
			case 1:
				JSONObject text = BaiDuApi.text(data, to);
				if (text!=null){

				}else {
					JSONObject text1 = SouGoApi.text(data, to);
					if (text1!=null){

					}else {
						JSONObject text2 = TxApi.text(data, to);
						if (text2!=null){

						}else {
							JSONObject text3 = XuFeiApi.txt(data, to);
							if (text3!=null){

							}else {
								JSONObject text4 = YouDaoApi.text(data, to);
								if (text4!=null){

								}
							}
						}
					}
				}
				break;
			case 2:
				String[] split = key.split(",");
				if (split.length==2){
					rm = YouDaoApi.file(file, split[0], split[1]);
				}

//				String contentType = file.getContentType();
//				String file1 = file(file,key);
//				recordsDTO.setTargetLanguageType("2");
//				recordsDTO.setOriginalData(null);
//				recordsDTO.setPostTranslationData(null);
//				recordTranslation(recordsDTO);
				break;
			case 3:
				String s = Base64.getEncoder().encodeToString(file.getBytes());
				JSONObject picture = TxApi.picture(s, to);
				if (picture!=null){
					recordsDTO.setTranslationType("3");
					recordsDTO.setUseNumber(picture.getString("length"));
					recordsDTO.setOriginalData(picture.getString("originalData"));
					recordsDTO.setPostTranslationData(picture.getString("postTranslationData"));
					recordTranslation(recordsDTO);
					rm=picture.getString("postTranslationData");
				}else {
				}

				break;
			default:
				break;
		}
		return rm;
	}

	/**
	 * 文件翻译
	 *
	 * @param file
	 * @param from
	 * @param to
	 */
	@Override
	public String file(MultipartFile file, String key) {
		JSONObject file1=new JSONObject();
		String[] split = key.split("-");
		if (split.length==2){
			file1= BaiDuApi.file(file, split[0], split[1]);
		}

		return file1.toJSONString();
	}

	@Transactional(rollbackFor = Exception.class)
	boolean recordTranslation(RecordsDTO recordsDTO){
		//翻译类型
		Integer type = Integer.valueOf(recordsDTO.getTargetLanguageType());
		//原数据
		String originalData = recordsDTO.getOriginalData();
		//翻译的数据
		String postTranslationData = recordsDTO.getPostTranslationData();
		Records records=new Records();
		BeanUtil.copyProperties(recordsDTO,records);
		JcKjUser user = AuthUtil.getUser();
		JSONObject jsonObject=new JSONObject(new LinkedHashMap<>());
		JSONObject insert=new JSONObject();
		records.setAccount(user.getAccount());
		String collectionName="";
		String mongodbId="";
		switch (type){
			case 1:
				//会议室得id
				String conferenceRoomId = recordsDTO.getConferenceRoomId();
				jsonObject.put("user_id",user.getUserId());
				jsonObject.put("user_name",user.getUserName());
				jsonObject.put("type","1");
				jsonObject.put("type_name","视频会议");
				jsonObject.put("original_data",originalData);
				jsonObject.put("post_translation_data",postTranslationData);
				Date createTime = records.getCreateTime();
				String date = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
				//存入mongodb collectionName对应mongodbd的key
				 collectionName=date+conferenceRoomId;
//				insert = mongoTemplate.insert(jsonObject,collectionName);
//				 mongodbId = String.valueOf(insert.get("_id"));
				records.setServe("视频会议");
				records.setMongodbKey(collectionName);
//				records.setMongodbId(mongodbId);
				BaseEntityUtils.resolveEntity(records);
				baseMapper.insert(records);
				break;
			case 2:
				records.setServe("文件翻译");
				BaseEntityUtils.resolveEntity(records);
				baseMapper.insert(records);
				jsonObject.put("type","2");
				jsonObject.put("type_name","文件翻译");
				jsonObject.put("original_data",originalData);
				jsonObject.put("post_translation_data",postTranslationData);
				 collectionName=user.getUserName() + user.getUserId();
//				 insert = mongoTemplate.insert(jsonObject, collectionName);
				mongodbId = String.valueOf(insert.get("_id"));
				records.setServe("文件翻译");
				records.setMongodbKey(collectionName);
//				records.setMongodbId(mongodbId);
				BaseEntityUtils.resolveEntity(records);
				baseMapper.insert(records);
				break;
			case 3:
				records.setServe("图片识别及翻译");
				BaseEntityUtils.resolveEntity(records);
				jsonObject.put("type","3");
				jsonObject.put("type_name","图片识别及翻译");
				jsonObject.put("original_data",originalData);
				jsonObject.put("post_translation_data",postTranslationData);
				collectionName=user.getUserName() + user.getUserId();
//				insert = mongoTemplate.insert(jsonObject, collectionName);
//				mongodbId = String.valueOf(insert.get("_id"));
				records.setServe("图片识别及翻译");
				records.setMongodbKey(collectionName);
//				records.setMongodbId(mongodbId);
				BaseEntityUtils.resolveEntity(records);
				baseMapper.insert(records);
				break;
			default:
				break;
		}
		return  true;
	}
}
