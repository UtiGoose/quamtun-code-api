package goose.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import goose.api.dao.LogViewMapper;
import goose.api.dao.ProgramMapper;
import goose.api.model.dto.CollectionDto;
import goose.api.model.dto.ViewDto;
import goose.api.model.pojo.Collection;
import goose.api.model.pojo.LogView;
import goose.api.service.LogViewService;
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
public class LogViewServiceImpl extends ServiceImpl<LogViewMapper, LogView> implements LogViewService {

    @Resource
    LogViewMapper logViewMapper;

    @Resource
    RedisOperator redis;

    @Resource
    ProgramMapper programMapper;

    @Override
    public List<ViewDto> getList(String token) {
        String userId = redis.get(token);
        if (userId == null || userId.equals("")) {
            return null;
        }
        LambdaQueryWrapper<LogView> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LogView::getUserId, userId).eq(LogView::getDeleted, 0);
        List<LogView> views = logViewMapper.selectList(lambdaQueryWrapper);
        List<ViewDto> res = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            ViewDto collectionDto = new ViewDto();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            collectionDto.setDate(sdf.format(views.get(i).getCreateTime()));
            collectionDto.setIp(views.get(i).getIp());
            collectionDto.setId(views.get(i).getId());
            collectionDto.setName(programMapper.selectById(views.get(i).getProgramId()).getLabelTitle());
            res.add(collectionDto);
        }
        return res;
    }

    @Override
    public int del(int id) {
        return logViewMapper.deleteById(id);
    }
}
