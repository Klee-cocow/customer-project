package com.flamingo.customer.model.request;

import lombok.Data;

import java.io.Serializable;


/**
 * @author 咏鹅
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 23:01
 */

@Data
public class PageRequest implements Serializable {


    //页面大小
    private long pageSize = 10;

    //当前页号
    private long current = 1;


    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "ascend";


}
