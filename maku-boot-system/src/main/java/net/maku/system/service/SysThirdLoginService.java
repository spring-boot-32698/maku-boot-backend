package net.maku.system.service;

import me.zhyd.oauth.model.AuthUser;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysThirdLoginEntity;
import net.maku.system.vo.SysThirdLoginVO;

import java.util.List;

/**
 * Third-party login
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysThirdLoginService extends BaseService<SysThirdLoginEntity> {

    List<SysThirdLoginVO> listByUserId(Long userId);

    void unBind(Long userId, String openType);

    void bind(Long userId, String openType, AuthUser authUser);

    /**
     * Query the user Id by third-party login type and openId
     *
     * @param openType Third-party login type
     * @param openId   Third-party user unique identifier
     * @return User Id
     */
    Long getUserIdByOpenTypeAndOpenId(String openType, String openId);

}