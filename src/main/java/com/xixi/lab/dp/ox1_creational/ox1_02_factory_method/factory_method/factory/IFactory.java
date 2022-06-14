package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.IProduct;

// 抽象工厂接口
public interface IFactory {
    // 创建产品
    IProduct createProduct();
}
