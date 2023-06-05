package com.flamingo.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flamingo.customer.common.BaseResponse;
import com.flamingo.customer.model.domain.User;
import com.flamingo.customer.model.dto.UserDTO;
import com.flamingo.customer.model.request.UserRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 咏鹅
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-30 18:24:24
*/
public interface UserService extends IService<User> {


    Integer userRegister(String userAccount, String password);

    UserDTO userLogin(String userAccount, String password,HttpServletRequest request);

    UserDTO getLoginUser(HttpServletRequest request);
}
