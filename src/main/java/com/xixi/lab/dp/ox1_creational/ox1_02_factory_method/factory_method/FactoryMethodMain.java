package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory.EarphoneFactory;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory.LaptopFactory;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.factory.PhoneFactory;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.factory_method.product.IProduct;

public class FactoryMethodMain {
    public static void main(String[] args) {
        IProduct phone = new PhoneFactory().createProduct();
        phone.desc();

        IProduct laptop = new LaptopFactory().createProduct();
        laptop.desc();

        IProduct earphone = new EarphoneFactory().createProduct();
        earphone.desc();
    }
}
