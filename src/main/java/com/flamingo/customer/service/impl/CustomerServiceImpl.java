package com.flamingo.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.common.ExcelExport;
import com.flamingo.customer.common.ExcelUtils;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.mapper.ContactMapper;
import com.flamingo.customer.model.domain.Contact;
import com.flamingo.customer.model.dto.CustomerQuery;
import com.flamingo.customer.model.request.CustomerAddRequest;
import com.flamingo.customer.model.request.CustomerUpdateRequest;
import com.flamingo.customer.service.CustomerService;
import com.flamingo.customer.model.domain.Customer;
import com.flamingo.customer.mapper.CustomerMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
* @author 咏鹅
* @description 针对表【customer】的数据库操作Service实现
* @createDate 2023-05-30 18:24:24
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ContactMapper contactMapper;

    @Override
    public Integer addCustomer(CustomerAddRequest customerAddRequest) {
        if(customerAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        String address = customerAddRequest.getAddress();
        String email = customerAddRequest.getEmail();
        String name = customerAddRequest.getName();
        String phone = customerAddRequest.getPhone();
        Integer gender = customerAddRequest.getGender();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setGender(gender);
        customer.setEmail(email);
        customer.setAddress(address);
        boolean saveResult = this.save(customer);
        if(!saveResult){
            throw new BusinessException(ErrorCode.NO_SAVE);
        }

        return customer.getId();
    }

    @Override
    public Boolean deleteCustomer(Integer id) {
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        //删除客户时，与其相关联的联系人表数据也会删除
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id",id);
        try {
            int deleteCount = contactMapper.delete(queryWrapper);
            int delResult = customerMapper.deleteById(id);
            if(delResult <=0){
                throw new BusinessException(ErrorCode.NO_QUERY,"删除失败，当前数据不存在");
            }
        } catch (Exception e){
            throw new BusinessException(ErrorCode.NO_SAVE);
        }

        return true;
    }

    @Override
    public Boolean updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        if(customerUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerUpdateRequest,customer);

        return this.updateById(customer);
    }

    @Override
    public Page<Customer> listCustomers(CustomerQuery customerQuery) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerQuery,customer);
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();

        String address = customer.getAddress();
        if(StringUtils.isNotBlank(address))
            queryWrapper.like("address",address);

        String phone = customer.getPhone();
        if(StringUtils.isNotBlank(phone))
            queryWrapper.like("phone",phone);

        String name = customer.getName();
        if(StringUtils.isNotBlank(name))
            queryWrapper.like("name",name);

        String email = customer.getEmail();
        if(StringUtils.isNotBlank(email))
            queryWrapper.like("email",email);

        Integer gender = customer.getGender();
        if(gender != null){
            queryWrapper.eq("gender",gender);
        }

        long current = customerQuery.getCurrent();
        long pageSize = customerQuery.getPageSize();
        Page<Customer> customerPage = this.page(new Page<>(current, pageSize), queryWrapper);

        return customerPage;
    }

    @Override
    public void exportCustomer(HttpServletResponse response) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper();
        List<Customer> customerList = customerMapper.selectList(queryWrapper);
        List<List<Object>> sheetDataList = new ArrayList<>();
        List<Object> head = Arrays.asList("客户id","客户姓名","客户电话","客户性别","客户邮箱","客户地址");
        sheetDataList.add(head);

        for (Customer cus: customerList) {
            List<Object> customer = new ArrayList<>();
            customer.add(cus.getId());
            customer.add(cus.getName());
            customer.add(cus.getPhone());
            customer.add(cus.getGender() == 1 ? "男":"女");
            customer.add(cus.getEmail());
            customer.add(cus.getAddress());
            sheetDataList.add(customer);
        }
        ExcelUtils.export(response,"Customer",sheetDataList);
    }

    @Override
    public Boolean importCustomer(List<Customer> customers) {
        try {
            for(Customer cus : customers){
                this.save(cus);
            }
            return true;

        } catch (Exception e){
            throw new BusinessException(ErrorCode.NO_SAVE,"导入失败");
        }

    }
}




