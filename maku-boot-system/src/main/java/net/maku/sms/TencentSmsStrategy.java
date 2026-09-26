package net.maku.sms;

import cn.hutool.core.map.MapUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
import net.maku.sms.config.SmsConfig;

import java.util.Map;

/**
 * Tencent Cloud SMS
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class TencentSmsStrategy implements SmsStrategy {
    private final SmsConfig smsConfig;
    private SmsClient client;

    public TencentSmsStrategy(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;

        try {
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            Credential cred = new Credential(smsConfig.getAccessKey(), smsConfig.getSecretKey());
            this.client = new SmsClient(cred, "ap-guangzhou", clientProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String mobile, Map<String, String> params) {
        SendSmsRequest request = new SendSmsRequest();
        request.setSmsSdkAppId(smsConfig.getAppId());
        request.setSignName(smsConfig.getSignName());
        request.setTemplateId(smsConfig.getTemplateId());

        // Set if there are parameters
        if (MapUtil.isNotEmpty(params)) {
            request.setTemplateParamSet(params.values().toArray(new String[0]));
        }

        // Mobile number
        String[] phoneNumberSet = {"+86" + mobile};
        request.setPhoneNumberSet(phoneNumberSet);

        // For international and Hong Kong/Macao/Taiwan SMS, SenderId must be added; leave empty for domestic SMS; not enabled by default
        request.setSenderId(smsConfig.getSenderId());

        try {
            // Send SMS
            SendSmsResponse response = client.SendSms(request);
            SendStatus sendStatus = response.getSendStatusSet()[0];

            // Send failed
            if (!Constant.OK.equalsIgnoreCase(sendStatus.getCode())) {
                throw new ServerException(sendStatus.getMessage());
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
}
