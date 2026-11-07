package net.maku.iot.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.iot.entity.IotDeviceEventLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Device event log
 *
 * @author LSF maku_lsf@163.com
 */
@Mapper
public interface IotDeviceEventLogDao extends BaseDao<IotDeviceEventLogEntity> {

}