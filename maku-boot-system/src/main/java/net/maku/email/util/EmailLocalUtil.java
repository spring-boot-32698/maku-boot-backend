package net.maku.email.util;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import net.maku.email.config.EmailConfig;
import net.maku.framework.common.exception.ServerException;

import java.io.File;

/**
 * Local mail sending
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
public class EmailLocalUtil {

    private final MailAccount mailAccount;

    public EmailLocalUtil(EmailConfig config) {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(config.getMailHost());
        if (config.getMailPort() != null) {
            mailAccount.setPort(config.getMailPort());
            // Enable SSL encryption
            if (config.getMailPort() == 465 || config.getMailPort() == 587) {
                mailAccount.setSslEnable(true);
            }
        }
        mailAccount.setFrom(config.getMailFrom());
        mailAccount.setPass(config.getMailPass());
        this.mailAccount = mailAccount;
    }

    /**
     * Send mail
     *
     * @param tos     Recipient email list, separate multiple recipients with commas
     * @param subject Mail subject
     * @param content Mail content
     * @param files   Attachment list
     * @return message-id
     */
    public String sendEmail(String tos, String subject, String content, boolean isHtml, File... files) {
        try {
            return MailUtil.send(mailAccount, tos, subject, content, isHtml, files);
        } catch (Exception e) {
            log.error("Failed to send mail", e);
            throw new ServerException(e.getMessage());
        }
    }
}
