package com.flamingo.customer.model.request;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/6/1 17:34
 */
@Data
public class ContactAddRequest {

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
     * 所属客户id
     */
    private BigInteger customer_id;

}
