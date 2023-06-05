package com.flamingo.customer.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 23:48
 */
@Data
public class CustomerUpdateRequest implements Serializable {
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
