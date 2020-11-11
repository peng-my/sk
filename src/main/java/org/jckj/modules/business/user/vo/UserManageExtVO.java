package org.jckj.modules.business.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "UserManageExtVO对象", description = "用户管理展示")
public class UserManageExtVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long id;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;
    /**
     * 礼卷id
     */
    @ApiModelProperty(value = "礼卷id")
    private Long gId;
    /**
     * 礼卷日本
     */
    @ApiModelProperty(value = "礼卷日本")
    private Double jpy;
    /**
     * 礼卷中国
     */
    @ApiModelProperty(value = "礼卷中国")
    private Double cny;
    /**
     * 礼卷美国
     */
    @ApiModelProperty(value = "礼卷美国")
    private Double usd;
    /**
     * 会员购买等级id
     */
    @ApiModelProperty(value = "会员购买等级id")
    private Long fkMemberLevelManagementId;
    /**
     * 会员等级
     */
    @ApiModelProperty(value = "会员等级")
    private String membershipLevel;
    /**
     * 用户是否被禁用
     */
    @ApiModelProperty(value = "用户是否被禁用")
    private Integer isDeleted;
    /**
     * 角色id
     */
    private String roleId;
}
