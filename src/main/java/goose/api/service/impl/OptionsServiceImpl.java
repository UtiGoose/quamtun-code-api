package goose.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import goose.api.dao.OptionsMapper;
import goose.api.model.pojo.Options;
import goose.api.service.OptionsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sam
 * @since 2023-03-21
 */
@Service
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {

}
