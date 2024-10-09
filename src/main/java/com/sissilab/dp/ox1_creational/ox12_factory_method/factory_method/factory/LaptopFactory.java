package com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory;

import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.IProduct;
import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.Laptop;

// 笔记本工厂
public class LaptopFactory implements IFactory{
    @Override
    public IProduct createProduct() {
        return new Laptop();
    }
}
