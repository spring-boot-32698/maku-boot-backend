package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.excel.ExcelFinishCallBack;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.ExcelUtils;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.framework.security.cache.TokenStoreCache;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.security.utils.TokenUtils;
import net.maku.system.dao.SysUserDao;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.enums.SuperAdminEnum;
import net.maku.system.query.SysRoleUserQuery;
import net.maku.system.query.SysUserQuery;
import net.maku.system.service.SysUserPostService;
import net.maku.system.service.SysUserRoleService;
import net.maku.system.service.SysUserService;
import net.maku.system.service.SysUserTokenService;
import net.maku.system.vo.*;
import net.maku.framework.common.trans.TransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final SysUserTokenService sysUserTokenService;
    private final TokenStoreCache tokenStoreCache;
    private final TransService transService;

    @Override
    public PageResult<SysUserVO> page(SysUserQuery query) {
        // Query parameters
        Map<String, Object> params = getParams(query);

        // Paged query
        IPage<SysUserEntity> page = getPage(query);
        params.put(Constant.PAGE, page);

        // Data list
        List<SysUserEntity> list = baseMapper.getList(params);

        return new PageResult<>(BeanUtil.copyToList(list, SysUserVO.class), page.getTotal());
    }

    private Map<String, Object> getParams(SysUserQuery query) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", query.getUsername());
        params.put("mobile", query.getMobile());
        params.put("gender", query.getGender());

        // Data permission
        // params.put(Constant.DATA_SCOPE, getDataScope("t1", null));

        // Organization filter
        if (ObjectUtil.isNotEmpty(query.getOrgId())) {
            params.put("orgList", query.getOrgId());
        }

        return params;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserVO vo) {
        SysUserEntity entity = BeanUtil.copyProperties(vo, SysUserEntity.class);
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());

        // Check whether the username exists
        SysUserEntity user = baseMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("Username already exists");
        }

        // Check whether the mobile number exists
        user = baseMapper.getByMobile(entity.getMobile());
        if (user != null) {
            throw new ServerException("Mobile number already exists");
        }

        // Save user
        baseMapper.insert(entity);

        // Save user-role relationship
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // Update user-post relationship
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void update(SysUserVO vo) {
        SysUserEntity entity = BeanUtil.copyProperties(vo, SysUserEntity.class);

        // Check whether the username exists
        SysUserEntity user = baseMapper.getByUsername(entity.getUsername());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("Username already exists");
        }

        // Check whether the mobile number exists
        user = baseMapper.getByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("Mobile number already exists");
        }

        // Update user
        updateById(entity);

        // Update user-role relationship
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // Update user-post relationship
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());

        // Update user cached permissions
        sysUserTokenService.updateCacheAuthByUserId(entity.getId());
    }

    @Override
    public void updateLoginInfo(SysUserBaseVO vo) {
        SysUserEntity entity = BeanUtil.copyProperties(vo, SysUserEntity.class);
        // Set the logged-in user ID
        entity.setId(SecurityUser.getUserId());

        // Check whether the mobile number exists
        SysUserEntity user = baseMapper.getByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("Mobile number already exists");
        }

        // Update user
        updateById(entity);

        // Cache update logic
        String accessToken = TokenUtils.getAccessToken();
        UserDetail cacheUser = tokenStoreCache.getUser(accessToken);
        cacheUser.setRealName(vo.getRealName());
        cacheUser.setMobile(vo.getMobile());
        cacheUser.setEmail(vo.getEmail());
        cacheUser.setGender(vo.getGender());
        // Update operation
        tokenStoreCache.updateUser(accessToken, cacheUser);
    }

    @Override
    public void updateAvatar(SysUserAvatarVO avatar) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(SecurityUser.getUserId());
        entity.setAvatar(avatar.getAvatar());
        updateById(entity);

        // Cache update logic
        String accessToken = TokenUtils.getAccessToken();
        UserDetail cacheUser = tokenStoreCache.getUser(accessToken);
        cacheUser.setAvatar(avatar.getAvatar());
        // Update operation
        tokenStoreCache.updateUser(accessToken, cacheUser);
    }

    @Override
    public void updateSignature(SysUserSignatureVO signature) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(SecurityUser.getUserId());
        entity.setSignature(signature.getSignature());
        updateById(entity);

        // Cache update logic
        String accessToken = TokenUtils.getAccessToken();
        UserDetail cacheUser = tokenStoreCache.getUser(accessToken);
        cacheUser.setSignature(signature.getSignature());
        // Update operation
        tokenStoreCache.updateUser(accessToken, cacheUser);
    }

    @Override
    public void delete(List<Long> idList) {
        // Delete user
        removeByIds(idList);

        // Delete user-role relationship
        sysUserRoleService.deleteByUserIdList(idList);

        // Delete user-post relationship
        sysUserPostService.deleteByUserIdList(idList);
    }

    @Override
    public List<String> getRealNameList(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }

        return baseMapper.selectBatchIds(idList).stream().map(SysUserEntity::getRealName).toList();
    }

    @Override
    public List<SysUserVO> getOrgUserList(Long orgId) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserEntity::getOrgId, orgId);

        List<SysUserEntity> list = baseMapper.selectList(queryWrapper);

        return BeanUtil.copyToList(list, SysUserVO.class);
    }

    @Override
    public SysUserVO getByMobile(String mobile) {
        SysUserEntity user = baseMapper.getByMobile(mobile);

        return BeanUtil.copyProperties(user, SysUserVO.class);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        // Change password
        SysUserEntity user = getById(id);
        user.setPassword(newPassword);

        updateById(user);
    }

    @Override
    public PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query) {
        // Query parameters
        Map<String, Object> params = getParams(query);
        params.put("roleId", query.getRoleId());

        // Paged query
        IPage<SysUserEntity> page = getPage(query);
        params.put(Constant.PAGE, page);

        // Data list
        List<SysUserEntity> list = baseMapper.getRoleUserList(params);

        return new PageResult<>(BeanUtil.copyToList(list, SysUserVO.class), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importByExcel(MultipartFile file, String password) {
        ExcelUtils.readAnalysis(file, SysUserExcelVO.class, new ExcelFinishCallBack<>() {
            @Override
            public void doSaveBatch(List<SysUserExcelVO> result) {
                ExcelUtils.parseDict(result);
                List<SysUserEntity> userList = BeanUtil.copyToList(result, SysUserEntity.class);
                userList.forEach(user -> user.setPassword(password));
                saveBatch(userList);
            }
        });
    }

    @Override
    @SneakyThrows
    public void export() {
        List<SysUserEntity> list = list(Wrappers.lambdaQuery(SysUserEntity.class).eq(SysUserEntity::getSuperAdmin, SuperAdminEnum.NO.getValue()));
        List<SysUserExcelVO> userExcelVOS = BeanUtil.copyToList(list, SysUserExcelVO.class);
        transService.transBatch(userExcelVOS);
        // Write to browser to open
        ExcelUtils.excelExport(SysUserExcelVO.class, "User management", null, userExcelVOS);
    }

}
