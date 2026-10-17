package net.maku.email.util;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.BatchSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;
import net.maku.email.config.EmailConfig;
import net.maku.framework.common.exception.ServerException;

/**
 * Alibaba Cloud mail sending
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
public class EmailAliyunUtil {

    private final Client client;

    public EmailAliyunUtil(EmailConfig emailConfig) throws Exception {
        Config config = new Config();
        config.setEndpoint(emailConfig.getEndpoint());
        config.setRegionId(emailConfig.getRegionId());
        config.setAccessKeyId(emailConfig.getAccessKey());
        config.setAccessKeySecret(emailConfig.getSecretKey());

        this.client = new Client(config);
    }

    /**
     * Send mail
     *
     * @param from      Sender email
     * @param formAlias Sender nickname
     * @param tos       Recipient email list, separate multiple recipients with commas
     * @param subject   Mail subject
     * @param content   Mail content
     * @param isHtml    Whether HTML format
     * @return env-id
     */
    public String sendEmail(String from, String formAlias, String tos, String subject, String content, boolean isHtml) {
        SingleSendMailRequest request = new SingleSendMailRequest();

        // Sender email
        request.setAccountName(from);

        // Sender nickname
        request.setFromAlias(formAlias);

        // Address type: 0 - random account, 1 - sending address
        request.setAddressType(1);

        // Use the reply address configured in the admin console
        request.setReplyToAddress(true);

        // Recipient email
        request.setToAddress(tos);

        // Mail subject
        request.setSubject(subject);

        // Whether HTML
        if (isHtml) {
            request.setHtmlBody(content);
        } else {
            request.setTextBody(content);
        }

        // Whether to enable tracing, which requires filing; 0 off, 1 on
        request.setClickTrace("0");

        try {
            return client.singleSendMail(request).getBody().getEnvId();
        } catch (Exception e) {
            log.error("Failed to send mail", e);
            throw new ServerException(e.getMessage());
        }
    }

    /**
     * Send mail in batch
     *
     * @param from          Sender email
     * @param receiversName Recipient list name
     * @param templateName  Mail template
     * @param tagName       Mail tag
     * @return env-id
     */
    public String batchSendEmail(String from, String receiversName, String templateName, String tagName) {
        BatchSendMailRequest request = new BatchSendMailRequest();

        // Sender email
        request.setAccountName(from);

        // Recipient list name
        request.setReceiversName(receiversName);

        // Mail template
        request.setTemplateName(templateName);

        // Address type: 0 - random account, 1 - sending address
        request.setAddressType(1);

        // Label
        request.setTagName(tagName);

        // Whether to enable tracing, which requires filing; 0 off, 1 on
        request.setClickTrace("0");

        try {
            return client.batchSendMail(request).getBody().getEnvId();
        } catch (Exception e) {
            log.error("Failed to send mail", e);
            throw new ServerException(e.getMessage());
        }
    }

}
