package net.maku.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.utils.TokenUtils;
import net.maku.system.service.SysAuthService;
import net.maku.system.service.SysCaptchaService;
import net.maku.system.vo.*;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "Authentication management")
@AllArgsConstructor
public class SysAuthController {
    private final SysCaptchaService sysCaptchaService;
    private final SysAuthService sysAuthService;

    @GetMapping("captcha")
    @Operation(summary = "Captcha")
    public Result<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();

        return Result.ok(captchaVO);
    }

    @GetMapping("captcha/enabled")
    @Operation(summary = "Whether to enable the captcha")
    public Result<Boolean> captchaEnabled() {
        boolean enabled = sysCaptchaService.isCaptchaEnabled();

        return Result.ok(enabled);
    }

    @PostMapping("login")
    @Operation(summary = "Account and password login")
    public Result<SysUserTokenVO> login(@RequestBody SysAccountLoginVO login) {
        SysUserTokenVO token = sysAuthService.loginByAccount(login);

        return Result.ok(token);
    }

    @PostMapping("send/code")
    @Operation(summary = "Send SMS verification code")
    public Result<String> sendCode(String mobile) {
        boolean flag = sysAuthService.sendCode(mobile);
        if (!flag) {
            return Result.error("SMS sending failed!");
        }

        return Result.ok();
    }

    @PostMapping("mobile")
    @Operation(summary = "Mobile number login")
    public Result<SysUserTokenVO> mobile(@RequestBody SysMobileLoginVO login) {
        SysUserTokenVO token = sysAuthService.loginByMobile(login);

        return Result.ok(token);
    }

    @PostMapping("third")
    @Operation(summary = "Third-party login")
    public Result<SysUserTokenVO> third(@RequestBody SysThirdCallbackVO login) {
        SysUserTokenVO token = sysAuthService.loginByThird(login);

        return Result.ok(token);
    }

    @PostMapping("token")
    @Operation(summary = "Get accessToken")
    public Result<AccessTokenVO> token(String refreshToken) {
        AccessTokenVO token = sysAuthService.getAccessToken(refreshToken);

        return Result.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "Exit")
    public Result<String> logout(HttpServletRequest request) {
        sysAuthService.logout(TokenUtils.getAccessToken(request));

        return Result.ok();
    }
}
