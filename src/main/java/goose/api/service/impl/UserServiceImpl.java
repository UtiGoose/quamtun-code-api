package goose.api.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import goose.api.dao.UserMapper;
import goose.api.model.pojo.User;
import goose.api.service.UserService;
import goose.api.util.RedisOperator;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sam
 * @since 2022-12-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    RedisOperator redis;


    @Override
    public int register(String requestBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");

            // 正则 包含数字与字母
            String regex = "^[a-z0-9A-Z]+$";
            if ( !password.matches(regex) || password.length() < 6 ) {
                return -1;
            }

            // 判断用户名是否重复
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUsername, username);
            if ( userMapper.selectCount(lambdaQueryWrapper) != 0 ) {
                return -2;
            }

            // 加盐加密
            String salt = UUID.randomUUID().toString().replace("-", ""); // 加密盐值
            String passwordBySalt = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
            Date date = new Date();
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordBySalt);
            user.setAvatar("user.png");
            user.setSalt(salt);
            user.setCreateTime(date);
            int insert = userMapper.insert(user);
            return insert;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int login(String requestBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUsername, username);
            List<User> users = userMapper.selectList(lambdaQueryWrapper);

            // 判断用户是否存在
            if (users.size() == 0) {
                return -1;
            }

            // 判断密码是否正确
            User user = users.get(0);
            String salt = user.getSalt();
            String passwordBySaltOnDb = user.getPassword();
            String passwordBySalt = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
            if (!passwordBySaltOnDb.equals(passwordBySalt)) {
                return -2;
            }

            // 密码正确
            return user.getId();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public User getUserInfo(String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");
        int id = Integer.parseInt(redis.get(token));
        return userMapper.selectById(id);
    }

    @Override
    public int updateInfo(String requestBody) {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String token = jsonObject.getString("token");
        int id = Integer.parseInt(redis.get(token));
        String avatar = jsonObject.getString("avatar");
        Integer age = jsonObject.getInteger("age");
        String education = jsonObject.getString("education");
        String description = jsonObject.getString("description");
        User user = userMapper.selectById(id);
        user.setAvatar(avatar);
        user.setAge(age);
        user.setEducation(education);
        user.setDescription(description);

        return userMapper.updateById(user);
    }


}
