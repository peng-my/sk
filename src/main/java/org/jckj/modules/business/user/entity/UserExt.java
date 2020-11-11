package org.jckj.modules.business.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.modules.system.entity.User;
/**
 * 用户表
 */
@Data
@TableName("jckj_user")
@EqualsAndHashCode(callSuper = true)
public class UserExt extends User {
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Long fkUserId;
}
