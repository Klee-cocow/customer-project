package com.flamingo.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flamingo.customer.common.ErrorCode;
import com.flamingo.customer.exception.BusinessException;
import com.flamingo.customer.mapper.CustomerMapper;
import com.flamingo.customer.model.domain.Customer;
import com.flamingo.customer.model.dto.ContactJoinCustomerQuery;
import com.flamingo.customer.model.dto.ContactQuery;
import com.flamingo.customer.model.dto.CustomerQuery;
import com.flamingo.customer.model.request.*;
import com.flamingo.customer.model.vo.ContactJoinCustomerVO;
import com.flamingo.customer.service.ContactService;
import com.flamingo.customer.model.domain.Contact;
import com.flamingo.customer.mapper.ContactMapper;
import com.flamingo.customer.service.CustomerService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
* @author 咏鹅
* @description 针对表【contact】的数据库操作Service实现
* @createDate 2023-05-30 18:24:23
*/
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact>
    implements ContactService {

    @Resource
    private ContactMapper contactMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Integer addContact(ContactAddRequest contactAddRequest) {
        if(contactAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        String caddress = contactAddRequest.getCaddress();
        String cemail = contactAddRequest.getCemail();
        String cphone = contactAddRequest.getCphone();
        String cname = contactAddRequest.getCname();
        Integer cgender = contactAddRequest.getCgender();
        BigInteger customer_id = contactAddRequest.getCustomer_id();

        //先判断当前客户是否存在
        Customer customer = customerMapper.selectById(customer_id);
        if(customer == null){
            throw new BusinessException(ErrorCode.NO_QUERY,"没有当前客户，请重新选择客户");
        }

        Contact contact = new Contact();
        contact.setCname(cname);
        contact.setCphone(cphone);
        contact.setCgender(cgender);
        contact.setCemail(cemail);
        contact.setCaddress(caddress);
        contact.setCustomer_id(customer_id);
        boolean flag = this.save(contact);

        if(!flag){
            throw new BusinessException(ErrorCode.NO_SAVE);
        }
        return contact.getCid();
    }

    @Override
    public Boolean deleteContact(Integer id) {
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }

        int delResult = contactMapper.deleteById(id);

        if(delResult <=0){
            throw new BusinessException(ErrorCode.NO_QUERY,"删除失败，当前数据不存在");
        }
        return true;
    }

    @Override
    public Boolean updateContact(ContactUpdateRequest contactUpdateRequest) {
        if(contactUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactUpdateRequest,contact);

        boolean flag = this.updateById(contact);
        if(!flag){
            throw new BusinessException(ErrorCode.NO_SAVE,"更新失败");
        }
        return true;
    }

    @Override
    public Page<Contact> listContact(ContactQuery contactQuery) {
        if(contactQuery == null){
            throw new BusinessException(ErrorCode.PARAMETER_ERROR);
        }
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactQuery,contact);
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();

        String caddress = contact.getCaddress();
        if(StringUtils.isNotBlank(caddress))
            queryWrapper.eq("caddress",caddress);

        String cphone = contact.getCphone();
        if(StringUtils.isNotBlank(cphone))
            queryWrapper.eq("cphone",cphone);

        BigInteger customer_id = contact.getCustomer_id();
        if(customer_id != null)
            queryWrapper.eq("customer_id",customer_id);

        String cname = contact.getCname();
        if(StringUtils.isNotBlank(cname))
            queryWrapper.eq("cname",cname);

        String cemail = contact.getCemail();
        if(StringUtils.isNotBlank(cemail))
            queryWrapper.eq("cemail",cemail);

        Integer cgender = contact.getCgender();
        if(cgender != null)
            queryWrapper.eq("cgender",cgender);

        long current = contactQuery.getCurrent();
        long pageSize = contactQuery.getPageSize();
        Page<Contact> contactList = this.page(new Page<>(current, pageSize), queryWrapper);

        return contactList;
    }

    @Override
    public Page<ContactJoinCustomerVO> listContactJoinCustomer() {
        PageRequest pageRequest = new ContactJoinCustomerQuery();
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();

        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        Page<Contact> page = new Page<>(current,pageSize);
        Page<ContactJoinCustomerVO> contactJoinCustomerVOIPage = contactMapper.selectContactLeftCustomer(page, queryWrapper);

        return contactJoinCustomerVOIPage;
    }
}


//    declare namespace API {
//    type BaseResponseBoolean = {
//            code?: number;
//    message?: string;
//    description?: string;
//    data?: boolean;
//  };
//
//    type BaseResponseInteger = {
//            code?: number;
//    message?: string;
//    description?: string;
//    data?: number;
//  };
//
//    type BaseResponsePageContact = {
//            code?: number;
//    message?: string;
//    description?: string;
//    data?: PageContact;
//  };
//
//    type BaseResponsePageCustomer = {
//            code?: number;
//    message?: string;
//    description?: string;
//    data?: PageCustomer;
//  };
//
//    type BaseResponseUserDTO = {
//            code?: number;
//    message?: string;
//    description?: string;
//    data?: UserDTO;
//  };
//
//    type Contact = {
//            cid?: number;
//    cname?: string;
//    cphone?: string;
//    cgender?: number;
//    cemail?: string;
//    caddress?: string;
//    customer_id?: number;
//  };

//
//    type ContactAddRequest = {
//            cname?: string;
//    cphone?: string;
//    cgender?: number;
//    cemail?: string;
//    caddress?: string;
//    customer_id?: number;
//  };
//
//    type ContactQuery = {
//            pageSize?: number;
//    pageNum?: number;
//    cid?: number;
//    cname?: string;
//    cphone?: string;
//    cgender?: number;
//    cemail?: string;
//    caddress?: string;
//    customer_id?: number;
//  };
//
//    type ContactUpdateRequest = {
//            cid?: number;
//    cname?: string;
//    cphone?: string;
//    cgender?: number;
//    cemail?: string;
//    caddress?: string;
//    customer_id?: number;
//  };
//
//    type Customer = {
//            id?: number;
//    name?: string;
//    phone?: string;
//    gender?: number;
//    email?: string;
//    address?: string;
//  };
//

//
//    type CustomerAddRequest = {
//            name?: string;
//    phone?: string;
//    gender?: number;
//    email?: string;
//    address?: string;
//  };
//
//    type CustomerQuery = {
//            pageSize?: number;
//    pageNum?: number;
//    id?: number;
//    name?: string;
//    phone?: string;
//    gender?: number;
//    email?: string;
//    address?: string;
//  };
//
//    type CustomerUpdateRequest = {
//            id?: number;
//    name?: string;
//    phone?: string;
//    gender?: number;
//    email?: string;
//    address?: string;
//  };
//
//    type deleteContactParams = {
//            id: number;
//  };
//
//    type deleteCustomerParams = {
//            id: number;
//  };
//
//    type listContactParams = {
//            contactQuery: ContactQuery;
//  };
//
//    type listCustomersParams = {
//            customerQuery: CustomerQuery;
//  };
//
//    type OrderItem = {
//            column?: string;
//    asc?: boolean;
//  };
//
//    type PageContact = {
//            records?: Contact[];
//    total?: number;
//    size?: number;
//    current?: number;
//    orders?: OrderItem[];
//    optimizeCountSql?: boolean;
//    searchCount?: boolean;
//    optimizeJoinOfCountSql?: boolean;
//    maxLimit?: number;
//    countId?: string;
//    pages?: number;
//  };
//
//    type PageCustomer = {
//            records?: Customer[];
//    total?: number;
//    size?: number;
//    current?: number;
//    orders?: OrderItem[];
//    optimizeCountSql?: boolean;
//    searchCount?: boolean;
//    optimizeJoinOfCountSql?: boolean;
//    maxLimit?: number;
//    countId?: string;
//    pages?: number;
//  };
//
//    type UserDTO = {
//            uid?: number;
//    nickname?: string;
//    username?: string;
//    role?: number;
//  };
//
//    type UserLoginRequest = {
//            userAccount?: string;
//    password?: string;
//  };
//
//    type UserRegisterRequest = {
//            userAccount?: string;
//    password?: string;
//    againPassword?: string;
//  };
//}





