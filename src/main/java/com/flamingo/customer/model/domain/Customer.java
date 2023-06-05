package com.flamingo.customer.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flamingo.customer.common.ExcelExport;
import com.flamingo.customer.common.ExcelImport;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName customer
 */
@TableName(value ="customer")
@Data
public class Customer implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 客户名称
     */
    @ExcelImport("客户姓名")
    private String name;

    /**
     * 客户电话
     */
    @ExcelImport("客户电话")
    private String phone;

    /**
     * 客户性别
     */
    @ExcelImport("客户性别")
    private Integer gender;

    /**
     * 客户邮箱
     */
    @ExcelImport("客户邮箱")
    private String email;

    /**
     * 客户地址
     */
    @ExcelImport("客户地址")
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}