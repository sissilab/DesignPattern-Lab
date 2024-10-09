package com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory;


import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.AppleLaptop;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.ApplePhone;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.AppleEarphone;

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
