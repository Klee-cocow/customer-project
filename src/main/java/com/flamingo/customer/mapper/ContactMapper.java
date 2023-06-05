package com.flamingo.customer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flamingo.customer.model.domain.Contact;
import com.flamingo.customer.model.dto.ContactQuery;
import com.flamingo.customer.model.vo.ContactJoinCustomerVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 咏鹅
 * @description 针对表【contact】的数据库操作Mapper
 * @createDate 2023-05-30 18:24:23
 * @Entity generator.domain.Contact
 */
public interface ContactMapper extends BaseMapper<Contact> {

    Page<ContactJoinCustomerVO> selectContactLeftCustomer(Page<Contact> page,
                                                    @Param(Constants.WRAPPER) QueryWrapper<Contact> wrapper);

}




