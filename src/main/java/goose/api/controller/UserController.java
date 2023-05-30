package goose.api.controller;


import com.alibaba.fastjson.JSONObject;
import goose.api.service.UserService;
import goose.api.util.RedisOperator;
import goose.api.util.Result;
import goose.api.util.ResultCode;
import goose.api.util.ResultUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sam
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    private RedisOperator redis;


    // 用户端
    @PostMapping("/register")
    public Result register(@RequestBody String requestBody) {
        int register = userService.register(requestBody);
        switch (register) {
            case 1: return ResultUtil.success(ResultCode.CREATE_SUCCESS, "注册成功");
            case -2: return ResultUtil.fail(ResultCode.CREATE_FAIL, "用户名已存在");
            case -1: return ResultUtil.fail(ResultCode.CREATE_FAIL, "密码要求包含数字与字母且大于6位");
            default: return ResultUtil.fail(ResultCode.CREATE_FAIL, "操作异常");
        }
    }


    @PostMapping("/login")
    public Result login(@RequestBody String requestBody) {
        int login = userService.login(requestBody);
        switch (login) {
            case -2: return ResultUtil.fail(ResultCode.GET_DATA_FAIL, "密码不正确");
            case -1: return ResultUtil.fail(ResultCode.GET_DATA_FAIL, "用户名不存在");
            case 0: return ResultUtil.fail(ResultCode.GET_DATA_FAIL, "操作异常");
            default: {
                // 获取token，存入redis
                String token = UUID.randomUUID().toString().replace("-", "");
                redis.set(token, String.valueOf(login));
                redis.expire(token, 2000);
                return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, "登录成功", token);
            }
        }
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");
        redis.del(token);
    }

    @PostMapping("/loginByToken")
    public Result loginByToken(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");
        long ttl = redis.ttl(token);
        if (ttl > 0) {
            return ResultUtil.success(ResultCode.GET_DATA_SUCCESS);
        } else {
            return ResultUtil.fail(ResultCode.GET_DATA_FAIL);
        }
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody String requestBody) {
        return ResultUtil.success(ResultCode.GET_DATA_SUCCESS, userService.getUserInfo(requestBody));
    }


    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file) throws IOException {
        System.out.println(file);
        String fileName = UUID.randomUUID().toString();  //获取文件原名
        String visibleUri="/"+fileName;     //拼接访问图片的地址
        String saveUri="D:\\tools\\"+fileName;        //拼接保存图片的真实地址


        File saveFile = new File(saveUri);
        //判断是否存在文件夹，不存在就创建，但其实可以直接手动确定创建好，这样不用每次保存都检测
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //保存文件到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        return visibleUri;
    }

}

