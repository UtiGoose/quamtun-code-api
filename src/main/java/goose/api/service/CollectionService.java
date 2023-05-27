package goose.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import goose.api.model.dto.CollectionDto;
import goose.api.model.pojo.Collection;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author goose
 * @since 2023-05-10
 */
public interface CollectionService extends IService<Collection> {

    int add(Collection collection);

    int del(int id);

    List<CollectionDto> getList(String token);

}
