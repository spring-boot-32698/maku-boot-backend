package net.maku.system.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.query.SysRoleUserQuery;
import net.maku.system.query.SysUserQuery;
import net.maku.system.vo.SysUserAvatarVO;
import net.maku.system.vo.SysUserBaseVO;
import net.maku.system.vo.SysUserSignatureVO;
import net.maku.system.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    PageResult<SysUserVO> page(SysUserQuery query);

    void save(SysUserVO vo);

    void update(SysUserVO vo);

    void updateLoginInfo(SysUserBaseVO vo);

    void updateAvatar(SysUserAvatarVO avatar);

    void updateSignature(SysUserSignatureVO signature);

    void delete(List<Long> idList);

    /**
     * Get user name list
     *
     * @param idList User ID list
     * @return User name list
     */
    List<String> getRealNameList(List<Long> idList);

    /**
     * Get the user list by organization ID
     *
     * @param orgId By organization ID
     * @return User list
     */
    List<SysUserVO> getOrgUserList(Long orgId);

    SysUserVO getByMobile(String mobile);

    /**
     * Change password
     *
     * @param id          User ID
     * @param newPassword New password
     */
    void updatePassword(Long id, String newPassword);

    /**
     * Assign role, user list
     */
    PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query);

    /**
     * Batch import users
     *
     * @param file     excelFile
     * @param password Password
     */
    void importByExcel(MultipartFile file, String password);

    /**
     * Export user info table
     */
    void export();
}
