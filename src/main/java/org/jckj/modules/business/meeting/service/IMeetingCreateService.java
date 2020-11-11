package org.jckj.modules.business.meeting.service;

import org.jckj.core.mp.base.BaseService;
import org.jckj.modules.business.meeting.entity.MeetingCreate;

public interface IMeetingCreateService extends BaseService<MeetingCreate> {
    /**
     * 创建
     */
    Integer addSumit();
}
