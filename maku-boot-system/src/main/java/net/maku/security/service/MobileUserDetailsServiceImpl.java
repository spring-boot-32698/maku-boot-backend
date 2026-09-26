package net.maku.security.service;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import net.maku.framework.security.mobile.MobileUserDetailsService;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.dao.SysUserDao;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Mobile verification code login MobileUserDetailsService
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {
    private final SysUserDetailsService sysUserDetailsService;
    private final SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByMobile(mobile);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Mobile number or verification code is wrong");
        }

        return sysUserDetailsService.getUserDetails(BeanUtil.copyProperties(userEntity, UserDetail.class));
    }

}
