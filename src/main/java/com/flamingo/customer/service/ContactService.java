package com.flamingo.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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

/**
* @author 咏鹅
* @description 针对表【contact】的数据库操作Service
* @createDate 2023-05-30 18:24:23
*/
public interface ContactService extends IService<Contact> {
    Integer addContact(ContactAddRequest contactAddRequest);

    Boolean deleteContact(Integer id);

    Boolean updateContact(ContactUpdateRequest contactUpdateRequest);

    Page<Contact> listContact(ContactQuery customerQuery);

    Page<ContactJoinCustomerVO> listContactJoinCustomer();
}
