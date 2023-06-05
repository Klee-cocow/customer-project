package com.flamingo.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flamingo.customer.model.domain.Customer;
import com.flamingo.customer.model.dto.CustomerQuery;
import com.flamingo.customer.model.request.CustomerAddRequest;
import com.flamingo.customer.model.request.CustomerUpdateRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
* @author 咏鹅
* @description 针对表【customer】的数据库操作Service
* @createDate 2023-05-30 18:24:24
*/
public interface CustomerService extends IService<Customer> {

    Integer addCustomer(CustomerAddRequest customerAddRequest);

    Boolean deleteCustomer(Integer id);

    Boolean updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    Page<Customer> listCustomers(CustomerQuery customerQuery);

    void exportCustomer(HttpServletResponse response);

    Boolean importCustomer(List<Customer> customers);
}
