package com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method;

import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.product.IProduct;
import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory.EarphoneFactory;
import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory.LaptopFactory;
import com.sissilab.dp.ox1_creational.ox12_factory_method.factory_method.factory.PhoneFactory;

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
