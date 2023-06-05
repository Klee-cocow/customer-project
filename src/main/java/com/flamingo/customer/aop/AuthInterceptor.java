package com.flamingo.customer.aop;

import com.flamingo.customer.annotation.AuthCheck;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.model.dto.UserDTO;
import com.flamingo.customer.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/6/2 11:57
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {

        Integer mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        UserDTO loginUser = userService.getLoginUser(request);

        if(mustRole == null){
            Integer role = loginUser.getRole();
            if(mustRole != role){
                throw new BusinessException(ErrorCode.NO_AUTH);
            }
        }

        return joinPoint.proceed();
    }
}
