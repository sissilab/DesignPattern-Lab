package com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory;


import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiEarphone;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiLaptop;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiPhone;

/**
 * 华为工厂
 */
public class HuaweiFactory implements IFactory {

    public HuaweiFactory() {
        System.out.println("Create Huawei factory.");
    }

    /**
     * 生产华为手机
     */
    @Override
    public IProduct makePhone() {
        System.out.println("Make Huawei phone...");
        return new HuaweiPhone();
    }

    /**
     * 生产华为笔记本
     */
    @Override
    public IProduct makeLaptop() {
        System.out.println("Make Huawei laptop...");
        return new HuaweiLaptop();
    }

    /**
     * 生产华为耳机
     */
    @Override
    public IProduct makeEarphone() {
        System.out.println("Make Huawei earphone...");
        return new HuaweiEarphone();
    }
}
