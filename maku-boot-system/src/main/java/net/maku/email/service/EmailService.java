package net.maku.email.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.maku.email.config.EmailConfig;
import net.maku.email.param.EmailAliyunBatchSendParam;
import net.maku.email.param.EmailAliyunSendParam;
import net.maku.email.param.EmailLocalSendParam;
import net.maku.email.util.EmailAliyunUtil;
import net.maku.email.util.EmailLocalUtil;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.ExceptionUtils;
import net.maku.system.cache.EmailConfigCache;
import net.maku.system.entity.SysMailLogEntity;
import net.maku.system.service.SysMailConfigService;
import net.maku.system.service.SysMailLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Mail service
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmailService {
    private final SysMailConfigService sysMailConfigService;
    private final EmailConfigCache emailConfigCache;
    private final SysMailLogService sysMailLogService;

    /**
     * Local send mail
     *
     * @param param Send mail parameters
     * @return message-id
     */
    public boolean sendLocal(EmailLocalSendParam param) {
        EmailConfig config = roundEmailConfig(param.getGroupName());
        return sendLocal(param, config);
    }

    /**
     * Local send mail
     *
     * @param param Send mail parameters
     * @return message-id
     */
    public boolean sendLocal(EmailLocalSendParam param, EmailConfig config) {
        try {
            new EmailLocalUtil(config).sendEmail(param.getTos(), param.getSubject(), param.getContent(), param.isHtml(), ArrayUtil.toArray(param.getFiles(), File.class));
            saveLog(config.getId(), config.getPlatform(), config.getMailFrom(), param.getTos(), param.getSubject(), param.getContent(), null);

            return true;
        } catch (Exception e) {
            log.error("Failed to send local mail", e);
            saveLog(config.getId(), config.getPlatform(), config.getMailFrom(), param.getTos(), param.getSubject(), param.getContent(), e);
            return false;
        }
    }


    /**
     * Alibaba Cloud send mail
     *
     * @param param Send mail parameters
     * @return env-id
     */
    public boolean sendAliyun(EmailAliyunSendParam param) {
        EmailConfig config = roundEmailConfig(param.getGroupName());

        return sendAliyun(param, config);
    }

    /**
     * Alibaba Cloud send mail
     *
     * @param param Send mail parameters
     * @return env-id
     */
    public boolean sendAliyun(EmailAliyunSendParam param, EmailConfig config) {
        try {
            new EmailAliyunUtil(config).sendEmail(param.getFrom(), param.getFormAlias(), param.getTos(), param.getSubject(), param.getContent(), param.isHtml());
            saveLog(config.getId(), config.getPlatform(), param.getFrom(), param.getTos(), param.getSubject(), param.getContent(), null);

            return true;
        } catch (Exception e) {
            log.error("Alibaba Cloud failed to send mail", e);
            saveLog(config.getId(), config.getPlatform(), param.getFrom(), param.getTos(), param.getSubject(), param.getContent(), e);
            return false;
        }
    }

    /**
     * Alibaba Cloud batch send mail
     *
     * @param param Send mail parameters
     * @return message-id
     */
    public boolean batchSendAliyun(EmailAliyunBatchSendParam param) {
        EmailConfig config = roundEmailConfig(param.getGroupName());

        return batchSendAliyun(param, config);
    }

    /**
     * Alibaba Cloud batch send mail
     *
     * @param param Send mail parameters
     * @return message-id
     */
    public boolean batchSendAliyun(EmailAliyunBatchSendParam param, EmailConfig config) {
        try {
            new EmailAliyunUtil(config).batchSendEmail(param.getFrom(), param.getReceiversName(), param.getTemplateName(), param.getTagName());
            saveLog(config.getId(), config.getPlatform(), param.getFrom(), param.getReceiversName(), null, param.getTemplateName(), null);

            return true;
        } catch (Exception e) {
            log.error("Alibaba Cloud failed to send mail", e);
            saveLog(config.getId(), config.getPlatform(), param.getFrom(), param.getReceiversName(), null, param.getTemplateName(), e);
            return false;
        }
    }

    /**
     * Save mail log
     */
    public void saveLog(Long platformId, Integer platform, String mailFrom, String mailTos, String subject, String content, Exception e) {
        SysMailLogEntity logEntity = new SysMailLogEntity();
        logEntity.setPlatformId(platformId);
        logEntity.setPlatform(platform);
        logEntity.setMailFrom(mailFrom);
        logEntity.setMailTos(mailTos);
        logEntity.setSubject(subject);
        logEntity.setContent(content);

        if (e != null) {
            String error = StringUtils.substring(ExceptionUtils.getExceptionMessage(e), 0, 2000);
            logEntity.setStatus(Constant.FAIL);
            logEntity.setError(error);
        } else {
            logEntity.setStatus(Constant.SUCCESS);
        }

        sysMailLogService.save(logEntity);
    }

    /**
     * Get the mail platform settings via a round-robin algorithm
     *
     * @param groupName Group name
     * @return Mail platform settings
     */
    private EmailConfig roundEmailConfig(String groupName) {
        List<EmailConfig> platformList = sysMailConfigService.listByEnable();

        // Whether there is an available mail platform
        if (platformList.isEmpty()) {
            throw new ServerException("No mail platform available, please add one first");
        }

        // Case without groups
        if (StrUtil.isBlank(groupName)) {
            // Send mail using a round-robin algorithm
            long round = emailConfigCache.getRoundValue();

            return platformList.get((int) round % platformList.size());
        }


        // Case with groups
        List<EmailConfig> newList = platformList.stream().filter(platform -> StrUtil.equals(platform.getGroupName(), groupName)).toList();
        if (newList.isEmpty()) {
            throw new ServerException("Mail group does not exist");
        }

        long round = emailConfigCache.getRoundCodeValue();

        // Specify the mail platform
        return newList.get((int) round % newList.size());
    }

}
