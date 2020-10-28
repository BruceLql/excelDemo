package com.bruce.demo.bean;

import lombok.Data;

/**
 * @author: lql
 * @version: 2020/10/28 12:07 上午
 */
@Data
public class Products {

    private String color;
    private String cover;
    private int item_type;
    private int platform;
    private String platform_label;
    private int price;
    private String product_id;
    private String promotion_id;
    private Stats stats;
    private int status;
    private String title;

}