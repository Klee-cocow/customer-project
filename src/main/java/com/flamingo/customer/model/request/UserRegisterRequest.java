package com.flamingo.customer.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 18:30
 */
@Data
public class UserRegisterRequest implements Serializable {

    private String userAccount;

    private String password;

    private String againPassword;
}
