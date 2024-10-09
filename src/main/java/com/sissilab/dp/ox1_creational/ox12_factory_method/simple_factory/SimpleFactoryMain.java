package com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory;

import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.factory.SimpleFactory;
import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.product.IProduct;

public class SimpleFactoryMain {
    public static void main(String[] args) {
        IProduct phone = SimpleFactory.createProduct(SimpleFactory.PRODUCT_TYPE.PHONE);
        phone.desc();

        IProduct laptop = SimpleFactory.createProduct(SimpleFactory.PRODUCT_TYPE.LAPTOP);
        laptop.desc();

        IProduct earphone = SimpleFactory.createProduct(SimpleFactory.PRODUCT_TYPE.EARPHONE);
        earphone.desc();
    }
}
