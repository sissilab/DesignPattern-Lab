package com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory;

import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.Earphone;
import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.IProduct;

// 耳机工厂
public class EarphoneFactory implements IFactory {
    @Override
    public IProduct createProduct() {
        return new Earphone();
    }
}
