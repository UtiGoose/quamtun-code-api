package goose.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import goose.api.dao.LogViewMapper;
import goose.api.dao.OptionsMapper;
import goose.api.dao.ProgramMapper;
import goose.api.dao.UserMapper;
import goose.api.model.dto.ProgramDto;
import goose.api.model.pojo.LogView;
import goose.api.model.pojo.Options;
import goose.api.model.pojo.Program;
import goose.api.model.pojo.User;
import goose.api.service.ProgramService;
import goose.api.util.RedisOperator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sam
 * @since 2023-03-21
 */
@Service
public class ProgramServiceImpl extends ServiceImpl<ProgramMapper, Program> implements ProgramService {

    @Resource
    RedisOperator redis;

    @Resource
    ProgramMapper programMapper;

    @Resource
    OptionsMapper optionMapper;

    @Resource
    LogViewMapper logViewMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public ProgramDto getOne(int id, String token) {


        String s = redis.get(token);
        if (s == null || s.equals("")) {
            return null;
        }
        int userId = Integer.parseInt(s);

        Program program = programMapper.selectById(id);
        program.setViews(program.getViews() + 1);
        programMapper.updateById(program);
        LambdaQueryWrapper<Options> optQW = new LambdaQueryWrapper<>();
        optQW.eq(Options::getPyId, id);
        List<Options> options = optionMapper.selectList(optQW);
        User user = userMapper.selectById(program.getUserId());

        ProgramDto programDto = new ProgramDto();

        programDto.setId(program.getId());
        programDto.setPyName(program.getPyName());
        programDto.setLabelTitle(program.getLabelTitle());
        programDto.setLabelQuestion(program.getLabelQuestion());
        programDto.setImageCover(program.getImageCover());
        programDto.setLabelFirst(program.getLabelFirst());
        programDto.setImageFirst(program.getImageFirst());
        programDto.setLabelSecond(program.getLabelSecond());
        programDto.setImageSecond(program.getImageSecond());
        programDto.setLabelTask(program.getLabelTask());
        programDto.setCreateTime(program.getCreateTime());
        programDto.setViews(program.getViews());
        programDto.setOptions(options);

        programDto.setAvatar(user.getAvatar());
        programDto.setUsername(user.getUsername());

        LambdaQueryWrapper<LogView> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LogView::getUserId, userId).eq(LogView::getProgramId, id);
        List<LogView> logViews = logViewMapper.selectList(lambdaQueryWrapper);
        if (logViews.size() > 0) {
            LogView logView = logViews.get(0);
            logView.setCreateTime(new Date());
            logView.setIp("127.0.0.1");
            logViewMapper.updateById(logView);
        } else {
            LogView logView = new LogView();
            logView.setProgramId(id);
            logView.setUserId(userId);
            logView.setIp("127.0.0.1");
            logView.setCreateTime(new Date());
            logView.setDeleted(0);
            logViewMapper.insert(logView);
        }
        return programDto;
    }

    @Override
    public Page<Program> getPage(int current, String name) {
        Page<Program> page = new Page<Program>(current, 4);
        LambdaQueryWrapper<Program> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Program::getLabelTitle, name);
        return programMapper.selectPage(page, lambdaQueryWrapper);
    }

    public String getIpAddr(HttpServletRequest request) {
        //目前则是网关ip
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                //只获取第一个值
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            //取不到真实ip则返回空，不能返回内网地址。
            return "";
        }
    }
}
