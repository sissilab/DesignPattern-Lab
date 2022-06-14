package com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product;

// 华为族具体产品：华为手机
public class HuaweiPhone extends AbstractPhone {
    /**
     * 华为手机产品描述
     */
    @Override
    public void desc() {
        System.out.println("I am Huawei phone.");
    }
}
