package goose.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import goose.api.model.dto.ProgramDto;
import goose.api.model.pojo.Program;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sam
 * @since 2023-03-21
 */
public interface ProgramService extends IService<Program> {
    ProgramDto getOne(int id, String token);

    Page<Program> getPage(int current, String name, int category);
}
