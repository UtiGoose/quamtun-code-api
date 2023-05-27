package goose.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import goose.api.model.dto.ViewDto;
import goose.api.model.pojo.LogView;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author goose
 * @since 2023-05-10
 */
public interface LogViewService extends IService<LogView> {


    List<ViewDto> getList(String token);

    int del(int id);
}
