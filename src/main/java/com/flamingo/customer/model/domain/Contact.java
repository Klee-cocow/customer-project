package com.flamingo.customer.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 
 * @TableName contact
 */
@TableName(value ="contact")
@Data
public class Contact implements Serializable {
    /**
     * 联系人id
     */
    @TableId(type = IdType.AUTO)
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
     * 所属客户id
     */
    private BigInteger customer_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}