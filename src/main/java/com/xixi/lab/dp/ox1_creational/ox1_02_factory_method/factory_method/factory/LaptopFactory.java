package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.IProduct;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.Laptop;

// 笔记本工厂
public class LaptopFactory implements IFactory{
    @Override
    public IProduct createProduct() {
        return new Laptop();
    }
}
