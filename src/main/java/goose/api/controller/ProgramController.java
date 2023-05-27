package goose.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import goose.api.model.dto.ProgramDto;
import goose.api.model.pojo.Program;
import goose.api.service.ProgramService;
import goose.api.util.Result;
import goose.api.util.ResultCode;
import goose.api.util.ResultUtil;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sam
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/program")
public class ProgramController {

    @Resource
    ProgramService programService;

    @PostMapping("/getOne")
    public Result<ProgramDto> getOne(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        int id = jsonObject.getInteger("id");
        String token = jsonObject.getString("token");
        return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, programService.getOne(id, token));
    }

    @PostMapping("/getPage")
    public Result<Page<Program>> getPage(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        int current = jsonObject.getInteger("current");
        String name = jsonObject.getString("name");
        return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, programService.getPage(current, name));
    }

}

