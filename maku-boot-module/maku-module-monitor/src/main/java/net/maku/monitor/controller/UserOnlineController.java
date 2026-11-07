package net.maku.monitor.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.api.module.system.UserTokenApi;
import net.maku.framework.common.cache.RedisCache;
import net.maku.framework.common.cache.RedisKeys;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.cache.TokenStoreCache;
import net.maku.framework.security.user.UserDetail;
import net.maku.monitor.vo.UserOnlineVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("monitor/user")
@AllArgsConstructor
@Tag(name = "Online user monitoring")
public class UserOnlineController {
    private final TokenStoreCache tokenStoreCache;
    private final RedisCache redisCache;
    private final UserTokenApi userTokenApi;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<PageResult<UserOnlineVO>> page(@ParameterObject @Valid Query query) {
        // Get all keys of the logged-in user
        List<String> keys = tokenStoreCache.getUserKeyList();

        // Logical pagination
        List<String> keyList = ListUtil.page(query.getPage() - 1, query.getLimit(), keys);

        // One MGET for the whole page instead of one GET per key
        List<Object> values = keyList.isEmpty() ? List.of() : redisCache.multiGet(keyList);

        List<UserOnlineVO> userOnlineList = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            UserDetail user = (UserDetail) values.get(i);
            if (user != null) {
                UserOnlineVO userOnlineVO = new UserOnlineVO();
                userOnlineVO.setId(user.getId());
                userOnlineVO.setUsername(user.getUsername());
                userOnlineVO.setRealName(user.getRealName());
                userOnlineVO.setGender(user.getGender());
                userOnlineVO.setEmail(user.getEmail());
                userOnlineVO.setAccessToken(key.replace(RedisKeys.getAccessTokenKey(""), ""));

                userOnlineList.add(userOnlineVO);
            }
        }

        return Result.ok(new PageResult<>(userOnlineList, keys.size()));
    }

    @DeleteMapping("{accessToken}")
    @Operation(summary = "Force logout")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<String> forceLogout(@PathVariable("accessToken") String accessToken) {
        // Token cannot be empty
        if (StrUtil.isBlank(accessToken)) {
            return Result.error("Token cannot be empty");
        }

        // End the session and expire the refresh token, so the client cannot simply refresh back in
        userTokenApi.forceLogout(accessToken);

        return Result.ok();
    }
}
