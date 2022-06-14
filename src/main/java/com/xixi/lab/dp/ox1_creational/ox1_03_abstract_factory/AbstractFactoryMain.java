package com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory;


import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.factory.AppleFactory;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.factory.HuaweiFactory;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.factory.IFactory;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.IProduct;

public class AbstractFactoryMain {
    public static void main(String[] args) {
        // 创建华为工厂来生产具体产品
        IFactory huaweiFactory = new HuaweiFactory();
        // 通过华为工厂来制作手机
        IProduct huaweiPhone = huaweiFactory.makePhone();
        huaweiPhone.desc();
        // 通过华为工厂来制作笔记本
        IProduct huaweiLaptop = huaweiFactory.makeLaptop();
        huaweiLaptop.desc();
        // 通过华为工厂来制作耳机
        IProduct huaweiEarphone = huaweiFactory.makeEarphone();
        huaweiEarphone.desc();

        System.out.println("-----------------");

        // 创建苹果工厂来生产具体产品
        IFactory appleFactory = new AppleFactory();
        // 通过苹果工厂来制作手机
        IProduct applePhone = appleFactory.makePhone();
        applePhone.desc();
        // 通过苹果工厂来制作笔记本
        IProduct appleLaptop = appleFactory.makeLaptop();
        appleLaptop.desc();
        // 通过苹果工厂来制作耳机
        IProduct appleEarphone = appleFactory.makeEarphone();
        appleEarphone.desc();
    }
}
