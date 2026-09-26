package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.dao.SysDictDataDao;
import net.maku.system.dao.SysDictTypeDao;
import net.maku.system.entity.SysDictDataEntity;
import net.maku.system.entity.SysDictTypeEntity;
import net.maku.system.enums.DictSourceEnum;
import net.maku.system.query.SysDictTypeQuery;
import net.maku.system.service.SysDictTypeService;
import net.maku.system.vo.SysDictTypeVO;
import net.maku.system.vo.SysDictVO;
import net.maku.framework.common.trans.DictionaryTransService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Dictionary type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService, InitializingBean {
    private final SysDictDataDao sysDictDataDao;
    private final DictionaryTransService dictionaryTransService;

    @Override
    public PageResult<SysDictTypeVO> page(SysDictTypeQuery query) {
        IPage<SysDictTypeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), SysDictTypeVO.class), page.getTotal());
    }

    @Override
    public List<SysDictTypeVO> list(Long pid) {
        LambdaQueryWrapper<SysDictTypeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(pid != null, SysDictTypeEntity::getPid, pid);

        List<SysDictTypeEntity> list = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(list, SysDictTypeVO.class);
    }

    private Wrapper<SysDictTypeEntity> getWrapper(SysDictTypeQuery query) {
        LambdaQueryWrapper<SysDictTypeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getDictType()), SysDictTypeEntity::getDictType, query.getDictType());
        wrapper.like(StrUtil.isNotBlank(query.getDictName()), SysDictTypeEntity::getDictName, query.getDictName());
        wrapper.isNull(SysDictTypeEntity::getPid);
        wrapper.orderByAsc(SysDictTypeEntity::getSort).orderByDesc(SysDictTypeEntity::getId);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictTypeVO vo) {
        SysDictTypeEntity entity = BeanUtil.copyProperties(vo, SysDictTypeEntity.class);

        // Update the parent, which has child nodes
        if (vo.getPid() != null) {
            this.updateHasChild(vo.getPid(), 1);
        }

        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeVO vo) {
        SysDictTypeEntity entity = BeanUtil.copyProperties(vo, SysDictTypeEntity.class);

        // Update the parent, which has child nodes
        if (vo.getPid() != null) {
            this.updateHasChild(vo.getPid(), 1);
        }

        // Get the original parent ID
        Long oldPid = baseMapper.selectById(vo.getId()).getPid();

        // Update
        updateById(entity);

        // Parent has been changed
        if (!ObjectUtil.equals(oldPid, vo.getPid())) {
            long count = baseMapper.selectCount(new LambdaQueryWrapper<SysDictTypeEntity>().eq(SysDictTypeEntity::getPid, oldPid));
            if (count == 0) {
                // Update the original parent, which has no child nodes
                this.updateHasChild(oldPid, 0);
            }
        }

    }

    /**
     * Update the parent, which has child nodes
     *
     * @param pid      Parent ID
     * @param hasChild Whether there are child nodes
     */
    private void updateHasChild(Long pid, Integer hasChild) {
        SysDictTypeEntity entity = new SysDictTypeEntity();
        entity.setId(pid);
        entity.setHasChild(hasChild);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<SysDictVO.DictData> getDictSql(Long id) {
        SysDictTypeEntity entity = this.getById(id);
        try {
            return sysDictDataDao.getListForSql(entity.getDictSql());
        } catch (Exception e) {
            throw new ServerException("Dynamic SQL execution failed, please check that the SQL is correct!");
        }
    }

    @Override
    public List<SysDictVO> getDictList() {
        // All dictionary type list
        List<SysDictTypeEntity> typeList = this.list(Wrappers.emptyWrapper());

        // All dictionary data list
        QueryWrapper<SysDictDataEntity> query = new QueryWrapper<SysDictDataEntity>().orderByAsc("sort");
        List<SysDictDataEntity> dataList = sysDictDataDao.selectList(query);

        // All dictionary list
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setDictType(type.getDictType());

            for (SysDictDataEntity data : dataList) {
                if (type.getId().equals(data.getDictTypeId())) {
                    dict.getDataList().add(new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass(), null));
                }
            }

            // Data source dynamic SQL
            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
                // Add dynamic list
                String sql = type.getDictSql();
                try {
                    dict.setDataList(sysDictDataDao.getListForSql(sql));
                } catch (Exception e) {
                    log.error("Failed to add dynamic dictionary: type=" + type, e);
                }
            }

            dictList.add(dict);
        }

        return dictList;
    }

    @Override
    public void afterPropertiesSet() {
        refreshTransCache();
    }

    public void refreshTransCache() {
        // Async, does not block the main thread and does not increase startup time
        CompletableFuture.supplyAsync(() -> {
            // Get all dictionary item data
            List<SysDictDataEntity> dataList = sysDictDataDao.selectList(new LambdaQueryWrapper<>());
            // Group by type
            Map<Long, List<SysDictDataEntity>> dictTypeDataMap = dataList.stream().collect(Collectors
                    .groupingBy(SysDictDataEntity::getDictTypeId));
            List<SysDictTypeEntity> dictTypeEntities = super.list();
            for (SysDictTypeEntity dictTypeEntity : dictTypeEntities) {
                if (dictTypeDataMap.containsKey(dictTypeEntity.getId())) {
                    try {
                        dictionaryTransService.refreshCache(dictTypeEntity.getDictType(), dictTypeDataMap.get(dictTypeEntity.getId())
                                .stream().collect(Collectors.toMap(SysDictDataEntity::getDictValue, SysDictDataEntity::getDictLabel)));
                    } catch (Exception e) {
                        log.error("Failed to refresh dictionary cache: type=" + dictTypeEntity, e);
                    }
                } else {
                    try {
                        dictionaryTransService.refreshCache(dictTypeEntity.getDictType(), sysDictDataDao.getListForSql(dictTypeEntity.getDictSql())
                                .stream().collect(Collectors.toMap(SysDictVO.DictData::getDictValue, SysDictVO.DictData::getDictLabel)));
                    } catch (Exception e) {
                        log.error("Failed to refresh dynamic SQL dictionary cache: type=" + dictTypeEntity, e);
                    }
                }
            }
            return null;
        });
    }
}
