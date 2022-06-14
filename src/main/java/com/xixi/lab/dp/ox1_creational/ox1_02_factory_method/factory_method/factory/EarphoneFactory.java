package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.Earphone;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.IProduct;

// 耳机工厂
public class EarphoneFactory implements IFactory {
    @Override
    public IProduct createProduct() {
        return new Earphone();
    }
}
