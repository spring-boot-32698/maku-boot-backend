package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Token
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */

@Data
@TableName("sys_user_token")
public class SysUserTokenEntity {

    @TableId
    private Long id;

    /**
     * User ID
     */
    private Long userId;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * accessToken Expiration time
     */
    private LocalDateTime accessTokenExpire;

    /**
     * refreshToken
     */
    private String refreshToken;

    /**
     * refreshToken Expiration time
     */
    private LocalDateTime refreshTokenExpire;

    /**
     * Tenant ID
     */
    private Long tenantId;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}