package com.bruce.demo.bean;

import lombok.Data;

import java.util.List;

/**
 * @author: lql
 * @version: 2020/10/28 12:08 上午
 */
@Data
public class Datas {

    private List<Products> products;
    private int total;

}