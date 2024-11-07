package com.sissilab.dp.ox1_creational.ox13_abstract_factory;


import com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory.AppleFactory;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory.HuaweiFactory;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory.IFactory;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;

public class AbstractFactoryMain {
    public static void main(String[] args) {
        // Create a Huawei factory to produce concrete products.
        IFactory huaweiFactory = new HuaweiFactory();
        // Create a phone through the Huawei factory.
        IProduct huaweiPhone = huaweiFactory.makePhone();
        huaweiPhone.desc();
        // Create a laptop through the Huawei factory.
        IProduct huaweiLaptop = huaweiFactory.makeLaptop();
        huaweiLaptop.desc();
        // Create an earphone through the Huawei factory.
        IProduct huaweiEarphone = huaweiFactory.makeEarphone();
        huaweiEarphone.desc();

        System.out.println("-----------------");

        // Create a Apple factory to produce concrete products.
        IFactory appleFactory = new AppleFactory();
        // Create a phone through the Apple factory.
        IProduct applePhone = appleFactory.makePhone();
        applePhone.desc();
        // Create a laptop through the Apple factory.
        IProduct appleLaptop = appleFactory.makeLaptop();
        appleLaptop.desc();
        // Create an earphone through the Apple factory.
        IProduct appleEarphone = appleFactory.makeEarphone();
        appleEarphone.desc();
    }
}
