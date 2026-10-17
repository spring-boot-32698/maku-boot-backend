package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.JsonUtils;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.cache.SysParamsCache;
import net.maku.system.dao.SysParamsDao;
import net.maku.system.entity.SysParamsEntity;
import net.maku.system.query.SysParamsQuery;
import net.maku.system.service.SysParamsService;
import net.maku.system.vo.SysParamsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Parameter management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsDao, SysParamsEntity> implements SysParamsService {
    private final SysParamsCache sysParamsCache;

    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        IPage<SysParamsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), SysParamsVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<SysParamsEntity> getWrapper(SysParamsQuery query) {
        LambdaQueryWrapper<SysParamsEntity> wrapper = Wrappers.lambdaQuery();

        wrapper.like(StrUtil.isNotBlank(query.getParamKey()), SysParamsEntity::getParamKey, query.getParamKey());
        wrapper.eq(StrUtil.isNotBlank(query.getParamValue()), SysParamsEntity::getParamValue, query.getParamValue());
        wrapper.eq(query.getParamType() != null, SysParamsEntity::getParamType, query.getParamType());
        wrapper.orderByDesc(SysParamsEntity::getId);

        return wrapper;
    }

    @Override
    public void save(SysParamsVO vo) {
        // Check whether the parameter key exists
        boolean exist = baseMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServerException("Parameter key already exists");
        }

        SysParamsEntity entity = BeanUtil.copyProperties(vo, SysParamsEntity.class);

        baseMapper.insert(entity);

        // Save to cache
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(SysParamsVO vo) {
        SysParamsEntity entity = baseMapper.selectById(vo.getId());

        // If the parameter key was modified
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // Check whether the new parameter key exists
            boolean exist = baseMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServerException("Parameter key already exists");
            }

            // Delete the cache before modification
            sysParamsCache.del(entity.getParamKey());
        }

        // Update data
        updateById(BeanUtil.copyProperties(vo, SysParamsEntity.class));

        // Save to cache
        sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // Query list
        List<SysParamsEntity> list = baseMapper.selectBatchIds(idList);

        // Delete data
        removeByIds(idList);

        // Delete cache
        String[] keys = list.stream().map(SysParamsEntity::getParamKey).toArray(String[]::new);
        sysParamsCache.del(keys);
    }

    @Override
    public String getString(String paramKey) {
        String value = sysParamsCache.get(paramKey);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }

        // If not in cache, query the database
        SysParamsEntity entity = baseMapper.get(paramKey);
        if (entity == null) {
            throw new ServerException("Parameter value does not exist, paramKey:" + paramKey);
        }

        // Save to cache
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());

        return entity.getParamValue();
    }

    @Override
    public int getInt(String paramKey) {
        String value = getString(paramKey);

        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String paramKey) {
        String value = getString(paramKey);

        return Boolean.parseBoolean(value);
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        String value = getString(paramKey);

        return JsonUtils.parseObject(value, valueType);
    }

}