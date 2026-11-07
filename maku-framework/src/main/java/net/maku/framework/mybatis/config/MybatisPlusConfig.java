package net.maku.framework.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import net.maku.framework.mybatis.handler.FieldMetaObjectHandler;
import net.maku.framework.mybatis.interceptor.DataScopeInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus Settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // Data permission
        mybatisPlusInterceptor.addInnerInterceptor(new DataScopeInnerInterceptor());
        // Pagination plugin
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // Optimistic lock
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // Prevent full-table updates and deletes
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return mybatisPlusInterceptor;
    }

    @Bean
    public FieldMetaObjectHandler fieldMetaObjectHandler(){
        return new FieldMetaObjectHandler();
    }
}
