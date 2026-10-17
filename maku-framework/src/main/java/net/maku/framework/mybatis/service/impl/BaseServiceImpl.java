package net.maku.framework.mybatis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.query.Query;
import net.maku.framework.mybatis.interceptor.DataScope;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;

import java.util.List;


/**
 * Base service class, all Services must extend it
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * Get the pagination object
     *
     * @param query Pagination parameters
     */
    protected IPage<T> getPage(Query query) {
        Page<T> page = new Page<>(query.getPage(), query.getLimit());

        // Sort
        if (StringUtils.isNotBlank(query.getOrder())) {
            if (query.isAsc()) {
                return page.addOrder(OrderItem.asc(query.getOrder()));
            } else {
                return page.addOrder(OrderItem.desc(query.getOrder()));
            }
        }

        return page;
    }

    /**
     * MyBatis-Plus Data permission
     */
    protected void dataScopeWrapper(LambdaQueryWrapper<T> queryWrapper) {
        DataScope dataScope = getDataScope(null, null);
        if (dataScope != null) {
            queryWrapper.apply(dataScope.getSqlFilter());
        }
    }

    /**
     * Native SQL data permission
     *
     * @param tableAlias Table alias; required when joining multiple tables
     * @param orgIdAlias Organization ID alias, null: means org_id
     * @return Return data permission
     */
    protected DataScope getDataScope(String tableAlias, String orgIdAlias) {
        UserDetail user = SecurityUser.getUser();
        // If it is a super administrator, do not filter data
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            return null;
        }

        // If null, set to an empty string
        if (tableAlias == null) {
            tableAlias = "";
        }

        // Get table alias
        if (StringUtils.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        // Data permission scope
        List<Long> dataScopeList = user.getDataScopeList();
        // All data permissions
        if (dataScopeList == null) {
            return null;
        }
        // Data filtering
        if (dataScopeList.size() > 0) {
            if (StringUtils.isBlank(orgIdAlias)) {
                orgIdAlias = "org_id";
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);

            sqlFilter.append(" in(").append(StrUtil.join(",", dataScopeList)).append(")");

            sqlFilter.append(" or ");
        }

        // Query own data
        sqlFilter.append(tableAlias).append("creator").append("=").append(user.getId());

        sqlFilter.append(")");

        return new DataScope(sqlFilter.toString());
    }
}