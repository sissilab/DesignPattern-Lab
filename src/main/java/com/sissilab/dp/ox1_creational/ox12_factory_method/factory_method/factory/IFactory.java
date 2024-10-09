package com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory;

import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.IProduct;

// 抽象工厂接口
public interface IFactory {
    // 创建产品
    IProduct createProduct();
}
