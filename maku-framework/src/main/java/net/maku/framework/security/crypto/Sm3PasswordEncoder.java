package net.maku.framework.security.crypto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Uses the national standard SM3 encryption algorithm to encrypt system passwords
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class Sm3PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return SmUtil.sm3(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StrUtil.equals(SmUtil.sm3(rawPassword.toString()), encodedPassword);
    }
}
