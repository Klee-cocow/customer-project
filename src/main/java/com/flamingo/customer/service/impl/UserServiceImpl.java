package com.flamingo.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.model.dto.UserDTO;
import com.flamingo.customer.service.UserService;
import com.flamingo.customer.model.domain.User;
import com.flamingo.customer.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.flamingo.customer.constant.UserConstant.SALT;
import static com.flamingo.customer.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 咏鹅
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-05-30 18:24:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    public final String validPattern = ".*[[ _`=|\\[\\].~……——+|{}‘]|\\n|\\r|\\t].*";

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer userRegister(String userAccount, String password) {
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "用户名或密码不能为空");
        }
        Pattern compile = Pattern.compile(validPattern);
        boolean m1 = compile.matcher(userAccount).matches();
        if (m1) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "账户不能有特殊字符");
        }
        boolean m2 = compile.matcher(password).matches();
        if (m2) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "密码不能有特殊字符");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "账户长度必须大于4位");
        }
        if (password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "密码长度必须大于6位");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "当前账户已存在");
        }
        String entryPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = new User();
        Long timeMillis = System.currentTimeMillis();
        user.setNickname(timeMillis.toString());
        user.setUsername(userAccount);
        user.setPassword(entryPassword);
        user.setRole(0);
        user.setIs_del(0);
        boolean saveResult = this.save(user);

        if (!saveResult) {
            throw new BusinessException(ErrorCode.NO_SAVE);
        }

        return user.getUid();
    }

    @Override
    public UserDTO userLogin(String userAccount, String password,HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "用户名或密码不能为空");
        }
        Pattern compile = Pattern.compile(validPattern);
        //判断是否拥有特殊字符
        boolean m = compile.matcher(userAccount).matches();
        if (m) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "账户不能拥有特殊字符");
        }
        m = compile.matcher(password).matches();
        if (m) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "密码不能拥有特殊字符");
        }
        String entryPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("username", userAccount);
        queryWrapper.eq("password", entryPassword);
        User user = userMapper.selectOne(queryWrapper);
        UserDTO userResult = new UserDTO();
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_QUERY, "此用户不存在");
        }
        BeanUtils.copyProperties(user, userResult);
        request.getSession().setAttribute(USER_LOGIN_STATE,userResult);

        return userResult;
    }

    @Override
    public UserDTO getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN, "未登录");
        }
        return (UserDTO) userObj;

    }

}




