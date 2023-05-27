package goose.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import goose.api.dao.CollectionMapper;
import goose.api.dao.ProgramMapper;
import goose.api.dao.UserMapper;
import goose.api.model.dto.CollectionDto;
import goose.api.model.pojo.Collection;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import goose.api.service.CollectionService;
import goose.api.util.RedisOperator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author goose
 * @since 2023-05-10
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Resource
    CollectionMapper collectionMapper;

    @Resource
    ProgramMapper programMapper;

    @Resource
    RedisOperator redis;

    @Override
    public int add(Collection collection) {

        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collection::getUserId, collection.getUserId()).eq(Collection::getProgramId, collection.getProgramId());

        List<Collection> collections = collectionMapper.selectList(lambdaQueryWrapper);
        if ( collections.size() > 0) {
            Collection collection1 = collections.get(0);
            if (collection1.getDeleted() == 1) {
                collection1.setDeleted(0);
                collectionMapper.updateById(collection1);
                return 1;
            } else {
                collection1.setDeleted(1);
                collectionMapper.updateById(collection1);
                return 2;
            }

        }

        int insert = collectionMapper.insert(collection);
        return insert;
    }

    @Override
    public int del(int id) {
        return collectionMapper.deleteById(id);
    }

    @Override
    public List<CollectionDto> getList(String token) {
        String userId = redis.get(token);
        if (userId == null || userId.equals("")) {
            return null;
        }
        LambdaQueryWrapper<Collection> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Collection::getUserId, userId).eq(Collection::getDeleted, 0);
        List<Collection> collections = collectionMapper.selectList(lambdaQueryWrapper);
        List<CollectionDto> res = new ArrayList<>();
        for (int i = 0; i < collections.size(); i++) {
            CollectionDto collectionDto = new CollectionDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            collectionDto.setDate(sdf.format(collections.get(i).getCreateTime()));
            collectionDto.setIp(collections.get(i).getIp());
            collectionDto.setId(collections.get(i).getId());
            collectionDto.setName(programMapper.selectById(collections.get(i).getProgramId()).getLabelTitle());
            res.add(collectionDto);
        }
        return res;
    }
}
