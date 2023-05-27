package goose.api.controller;


import com.alibaba.fastjson.JSONObject;
import goose.api.service.LogViewService;
import goose.api.util.Result;
import goose.api.util.ResultCode;
import goose.api.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author goose
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/log-view")
public class LogViewController {

    @Resource
    LogViewService logViewService;

    @PostMapping("/getList")
    public Result getList(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");

        return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, logViewService.getList(token));
    }

    @PostMapping("/del")
    public Result del(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        int id = jsonObject.getInteger("id");

        return ResultUtil.success(ResultCode.DELETE_SUCCESS, logViewService.del(id));
    }
}

