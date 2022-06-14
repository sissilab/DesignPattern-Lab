package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.IProduct;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.Phone;

// 手机工厂
public class PhoneFactory implements IFactory {
    @Override
    public IProduct createProduct() {
        return new Phone();
    }
}
