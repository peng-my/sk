package org.jckj.modules.business.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.modules.business.order.entity.Management;
import org.jckj.modules.business.translation.entity.Records;
import org.jckj.modules.business.user.entity.UserExt;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserExtVO对象", description = "用户")
public class UserExtVO extends UserExt {
	private static final long serialVersionUID = 1L;
	/**
	 * 使用明细
	 */
	List<Records> recordsList;
	/**
	 * 使用明细日期下拉框
	 */
	List<String> recordsDateList;
	/**
	 * 账号下拉框
	 */
	List<String> accountList;
	/**
	 * 订单列表
	 */
	List<Management> managementList;
	/**
	 * 订单列表日期下拉框
	 */
	List<String> managementDateList;

}
