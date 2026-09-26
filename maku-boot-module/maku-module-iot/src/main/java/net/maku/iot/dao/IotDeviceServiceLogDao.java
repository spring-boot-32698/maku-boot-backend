package net.maku.iot.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.iot.entity.IotDeviceServiceLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Device service log
 *
 * @author LSF maku_lsf@163.com
 */
@Mapper
public interface IotDeviceServiceLogDao extends BaseDao<IotDeviceServiceLogEntity> {

}