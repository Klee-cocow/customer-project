package com.flamingo.customer.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 18:31
 */
@Data
public class UserDTO implements Serializable {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户角色
     */
    private Integer role;


}
