package com.flamingo.customer.controller;

import com.flamingo.customer.common.BaseResponse;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.common.ResultUtils;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.model.dto.UserDTO;
import com.flamingo.customer.model.request.UserLoginRequest;
import com.flamingo.customer.model.request.UserRegisterRequest;
import com.flamingo.customer.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flamingo.customer.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 18:29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;



    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request){
        if(userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String againPassword = userRegisterRequest.getAgainPassword();
        if(StringUtils.isAnyBlank(userAccount,password,againPassword)){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR,"用户名或密码不能为空");
        }
        if(!password.equals(againPassword)){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR,"密码不一致");
        }

        Integer result = userService.userRegister(userAccount, password);

        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<UserDTO> userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){
        if(userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if(StringUtils.isAnyBlank(userAccount,password)){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR,"用户名或密码不能为空");
        }

        UserDTO user = userService.userLogin(userAccount, password,request);


        return ResultUtils.success(user);
    }

    @PostMapping("/get/login")
    public BaseResponse<UserDTO> getUserLogin(HttpServletRequest request){
        UserDTO loginUser = userService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }

}
