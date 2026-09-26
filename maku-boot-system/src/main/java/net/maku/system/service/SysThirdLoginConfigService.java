package net.maku.system.service;

import me.zhyd.oauth.request.AuthRequest;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysThirdLoginConfigEntity;
import net.maku.system.vo.SysThirdLoginConfigVO;

import java.util.List;

/**
 * Third-party login settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysThirdLoginConfigService extends BaseService<SysThirdLoginConfigEntity> {

    PageResult<SysThirdLoginConfigVO> page(Query query);

    void save(SysThirdLoginConfigVO vo);

    void update(SysThirdLoginConfigVO vo);

    void delete(List<Long> idList);

    /**
     * Get the authorization request by type
     *
     * @param openType Third-party login type
     */
    AuthRequest getAuthRequest(String openType);
}