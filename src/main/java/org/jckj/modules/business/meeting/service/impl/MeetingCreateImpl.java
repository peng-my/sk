package org.jckj.modules.business.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jckj.common.utils.BaseEntityUtils;
import org.jckj.core.mp.base.BaseServiceImpl;
import org.jckj.modules.business.meeting.entity.MeetingCreate;
import org.jckj.modules.business.meeting.mapper.MeetingCreateMapper;
import org.jckj.modules.business.meeting.service.IMeetingCreateService;
import org.springframework.stereotype.Service;

@Service
public class MeetingCreateImpl  extends BaseServiceImpl<MeetingCreateMapper, MeetingCreate> implements IMeetingCreateService {

    /**
     * 创建
     */
    @Override
    public Integer addSumit() {
        Integer max=0;
        MeetingCreate type = baseMapper.selectOne(new QueryWrapper<MeetingCreate>().eq("type", "1"));
        if (type!=null){
            Integer maxNumber = type.getMaxNumber();
            type.setMaxNumber(maxNumber+1);
            BaseEntityUtils.resolveEntity(type);
            baseMapper.updateById(type);
            max=type.getMaxNumber();
        }else {
            MeetingCreate meetingCreate=new MeetingCreate();
            meetingCreate.setMaxNumber(1);
            meetingCreate.setType("1");
            BaseEntityUtils.resolveEntity(meetingCreate);
            baseMapper.insert(meetingCreate);
            max=meetingCreate.getMaxNumber();
        }
        return max;
    }
}
