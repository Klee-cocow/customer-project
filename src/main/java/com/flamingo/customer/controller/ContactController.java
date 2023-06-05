package com.flamingo.customer.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flamingo.customer.common.BaseResponse;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.common.ResultUtils;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.model.domain.Contact;
import com.flamingo.customer.model.domain.Customer;
import com.flamingo.customer.model.dto.ContactJoinCustomerQuery;
import com.flamingo.customer.model.dto.ContactQuery;
import com.flamingo.customer.model.dto.CustomerQuery;
import com.flamingo.customer.model.request.ContactAddRequest;
import com.flamingo.customer.model.request.ContactUpdateRequest;
import com.flamingo.customer.model.request.CustomerAddRequest;
import com.flamingo.customer.model.request.CustomerUpdateRequest;
import com.flamingo.customer.model.vo.ContactJoinCustomerVO;
import com.flamingo.customer.service.ContactService;
import com.flamingo.customer.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/6/1 17:33
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Resource
    private UserService userService;

    @Resource
    private ContactService contactService;

    @PostMapping("/add")
    public BaseResponse<Integer> addContact(@RequestBody ContactAddRequest contactAddRequest, HttpServletRequest request) {
        if (contactAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Integer customerId = contactService.addContact(contactAddRequest);
        return ResultUtils.success(customerId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteContact(@RequestParam Integer id, HttpServletRequest request) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Boolean flag = contactService.deleteContact(id);
        return ResultUtils.success(flag);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateContact(@RequestBody ContactUpdateRequest contactUpdateRequest, HttpServletRequest request){
        if (contactUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Boolean flag = contactService.updateContact(contactUpdateRequest);
        return ResultUtils.success(flag);
    }

    @GetMapping("/list")
    public BaseResponse<Page<Contact>> listContact(ContactQuery contactQuery, HttpServletRequest request){
        if (contactQuery == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Page<Contact> customers = contactService.listContact(contactQuery);

        return ResultUtils.success(customers);
    }

    @PostMapping("/get/customer/list")
    public BaseResponse<Page<ContactJoinCustomerVO>> listContactJoinCustomer(HttpServletRequest request){

        userService.getLoginUser(request);
        Page<ContactJoinCustomerVO> customers = contactService.listContactJoinCustomer();

        return ResultUtils.success(customers);
    }

}
