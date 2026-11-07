package net.maku.sms.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.ExceptionUtils;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.sms.SmsContext;
import net.maku.sms.config.SmsConfig;
import net.maku.system.cache.SmsConfigCache;
import net.maku.system.entity.SysSmsLogEntity;
import net.maku.system.service.SysSmsConfigService;
import net.maku.system.service.SysSmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * SMS service
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsService {
    private final SysSmsConfigService sysSmsConfigService;
    private final SysSmsLogService sysSmsLogService;
    private final SmsConfigCache smsConfigCache;

    /**
     * Send SMS
     *
     * @param mobile Mobile number
     * @return Whether sent successfully
     */
    public boolean send(String mobile) {
        return this.send(mobile, MapUtil.newHashMap());
    }

    /**
     * Send SMS
     *
     * @param groupName Group name
     * @param mobile    Mobile number
     * @return Whether sent successfully
     */
    public boolean send(String groupName, String mobile) {
        return this.send(groupName, mobile, MapUtil.newHashMap());
    }

    /**
     * Send SMS
     *
     * @param mobile Mobile number
     * @param params Parameter
     * @return Whether sent successfully
     */
    public boolean send(String mobile, Map<String, String> params) {
        return this.send(null, mobile, params);
    }

    /**
     * Send SMS
     *
     * @param groupName Group name
     * @param mobile    Mobile number
     * @param params    Parameter
     * @return Whether sent successfully
     */
    public boolean send(String groupName, String mobile, Map<String, String> params) {
        SmsConfig config = roundSmsConfig(groupName);

        try {
            // Send SMS
            new SmsContext(config).send(mobile, params);

            saveLog(config, mobile, params, null);
            return true;
        } catch (Exception e) {
            log.error("SMS sending failed, mobile number:{}", mobile, e);

            saveLog(config, mobile, params, e);

            return false;
        }
    }

    /**
     * Save SMS log
     */
    public void saveLog(SmsConfig config, String mobile, Map<String, String> params, Exception e) {
        SysSmsLogEntity logEntity = new SysSmsLogEntity();
        logEntity.setPlatform(config.getPlatform());
        logEntity.setPlatformId(config.getId());
        logEntity.setMobile(mobile);
        logEntity.setParams(JsonUtils.toJsonString(params));

        if (e != null) {
            String error = StringUtils.substring(ExceptionUtils.getExceptionMessage(e), 0, 2000);
            logEntity.setStatus(Constant.FAIL);
            logEntity.setError(error);
        } else {
            logEntity.setStatus(Constant.SUCCESS);
        }

        sysSmsLogService.save(logEntity);
    }

    /**
     * Get the SMS platform settings via a round-robin algorithm
     *
     * @param groupName Group name
     * @return SMS platform settings
     */
    private SmsConfig roundSmsConfig(String groupName) {
        List<SmsConfig> platformList = sysSmsConfigService.listByEnable();

        // Whether there is an available SMS platform
        if (platformList.isEmpty()) {
            throw new ServerException("No SMS platform available, please add one first");
        }

        // Case without an SMS code
        if (StrUtil.isBlank(groupName)) {
            // Send SMS using a round-robin algorithm
            long round = smsConfigCache.getRoundValue();

            return platformList.get((int) round % platformList.size());
        }


        // Case with an SMS code
        List<SmsConfig> newList = platformList.stream().filter(platform -> StrUtil.equals(platform.getGroupName(), groupName)).toList();
        if (newList.isEmpty()) {
            throw new ServerException("SMS group does not exist");
        }

        long round = smsConfigCache.getRoundCodeValue();

        // Specify the SMS platform
        return newList.get((int) round % newList.size());
    }

}
