package com.flamingo.customer.model.dto;

import com.flamingo.customer.model.request.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/6/2 11:04
 */
@Data
public class ContactJoinCustomerQuery extends PageRequest implements Serializable {

    private Integer cid;

    /**
     * 联系人名称
     */
    private String cname;

    /**
     * 联系人电话
     */
    private String cphone;

    /**
     * 联系人性别
     */
    private Integer cgender;

    /**
     * 联系人邮箱
     */
    private String cemail;

    /**
     * 联系人地址
     */
    private String caddress;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 客户性别
     */
    private Integer gender;

    /**
     * 客户邮箱
     */
    private String email;

    /**
     * 客户地址
     */
    private String address;
}
