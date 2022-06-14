package com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.factory;


import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.AppleEarphone;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.AppleLaptop;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.ApplePhone;
import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.IProduct;

/**
 * 苹果工厂
 */
public class AppleFactory implements IFactory {

    public AppleFactory() {
        System.out.println("Create Apple factory.");
    }

    /**
     * 生产苹果手机
     */
    @Override
    public IProduct makePhone() {
        System.out.println("Make Apple phone...");
        return new ApplePhone();
    }

    /**
     * 生产苹果笔记本
     */
    @Override
    public IProduct makeLaptop() {
        System.out.println("Make Apple laptop...");
        return new AppleLaptop();
    }

    /**
     * 生产苹果耳机
     */
    @Override
    public IProduct makeEarphone() {
        System.out.println("Make Apple earphone...");
        return new AppleEarphone();
    }
}
