package net.maku.framework.security.config;

import net.maku.framework.security.crypto.Sm3PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Encryption settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use national standard SM3 encryption
        return new Sm3PasswordEncoder();

        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
