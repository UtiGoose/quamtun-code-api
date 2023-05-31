package goose.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import goose.api.model.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sam
 * @since 2022-12-20
 */
public interface UserService extends IService<User> {


    int register(String requestBody);

    int login(String requestBody);

    User getUserInfo(String requestBody);

    int updateInfo(String requestBody);

}
