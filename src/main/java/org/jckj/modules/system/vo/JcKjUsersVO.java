package org.jckj.modules.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.core.secure.JcKjUser;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserSVO", description = "UserSVO对象")
public class JcKjUsersVO extends JcKjUser {
	/**
	 * 部门名字
	 */
	private String deptName;
	/**
	 * 用户id
	 */
	private Long fkPersonalId;
}
