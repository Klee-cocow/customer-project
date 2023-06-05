package com.flamingo.customer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flamingo.customer.common.BaseResponse;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.common.ExcelUtils;
import com.flamingo.customer.common.ResultUtils;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.model.domain.Customer;
import com.flamingo.customer.model.dto.CustomerQuery;
import com.flamingo.customer.model.dto.UserDTO;
import com.flamingo.customer.model.request.CustomerAddRequest;
import com.flamingo.customer.model.request.CustomerUpdateRequest;
import com.flamingo.customer.service.CustomerService;
import com.flamingo.customer.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static com.flamingo.customer.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 22:47
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private UserService userService;

    @Resource
    private CustomerService customerService;

    @PostMapping("/add")
    public BaseResponse<Integer> addCustomer(@RequestBody CustomerAddRequest customerAddRequest, HttpServletRequest request) {
        if (customerAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Integer customerId = customerService.addCustomer(customerAddRequest);
        return ResultUtils.success(customerId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCustomer(@RequestParam Integer id, HttpServletRequest request) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Boolean flag = customerService.deleteCustomer(id);
        return ResultUtils.success(flag);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateCustomer(@RequestBody CustomerUpdateRequest customerUpdateRequest, HttpServletRequest request){
        if (customerUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Boolean flag = customerService.updateCustomer(customerUpdateRequest);
        return ResultUtils.success(flag);
    }

    @GetMapping("/list")
    public BaseResponse<Page<Customer>> listCustomers(CustomerQuery customerQuery, HttpServletRequest request){
        if (customerQuery == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        userService.getLoginUser(request);
        Page<Customer> customers = customerService.listCustomers(customerQuery);

        return ResultUtils.success(customers);
    }

    @GetMapping("/export")
    public void exportCustomer(HttpServletRequest request, HttpServletResponse response){
        userService.getLoginUser(request);
        customerService.exportCustomer(response);
    }


    @PostMapping("/import")
    public BaseResponse<Boolean> importCustomer(@RequestPart("file")MultipartFile file) throws Exception {
        List<Customer> customers = ExcelUtils.readMultipartFile(file, Customer.class);
        Boolean flag = customerService.importCustomer(customers);
        return ResultUtils.success(flag);
    }


}
