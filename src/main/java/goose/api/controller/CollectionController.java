package goose.api.controller;


import com.alibaba.fastjson.JSONObject;
import goose.api.model.pojo.Collection;
import goose.api.service.CollectionService;
import goose.api.util.RedisOperator;
import goose.api.util.Result;
import goose.api.util.ResultCode;
import goose.api.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author goose
 * @since 2023-05-10
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Resource
    CollectionService collectionService;

    @Resource
    RedisOperator redis;

    @PostMapping("/add")
    public Result add(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");
        Integer programId = jsonObject.getInteger("programId");
        Collection collection = new Collection();
        collection.setUserId(Integer.parseInt(redis.get(token)));
        collection.setProgramId(programId);
        collection.setIp("127.0.0.1");
        collection.setDeleted(0);
        collection.setCreateTime(new Date());
        int add = collectionService.add(collection);
        if (add == 1) {
            return ResultUtil.success(ResultCode.CREATE_SUCCESS, "收藏成功");
        } else if (add == 2) {
            return ResultUtil.success(ResultCode.CREATE_SUCCESS, "取消收藏成功");
        } else {
            return ResultUtil.fail(ResultCode.CREATE_FAIL);
        }
    }
    @PostMapping("/getList")
    public Result getList(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");

        return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, collectionService.getList(token));
    }

    @PostMapping("/del")
    public Result del(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        int id = jsonObject.getInteger("id");

        return ResultUtil.success(ResultCode.DELETE_SUCCESS, collectionService.del(id));
    }
}

