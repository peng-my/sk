package org.jckj.modules.business.meeting.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.core.mp.base.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("meeting_create")
@ApiModel(value = "MeetingCreate对象", description = "创建会议记录表")
public class MeetingCreate extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String type;

    /**
     * 最大值
     */
    @ApiModelProperty(value = "最大值")
    private Integer maxNumber;


}
