package net.maku.quartz.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* Scheduled task log
*
* @author Amu babamu@126.com
*/
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}