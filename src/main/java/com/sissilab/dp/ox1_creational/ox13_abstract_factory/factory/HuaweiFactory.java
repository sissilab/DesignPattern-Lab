package com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory;


import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiEarphone;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiLaptop;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.HuaweiPhone;

/**
 * Huawei Concrete Factory
 */
public class HuaweiFactory implements IFactory {

    public HuaweiFactory() {
        System.out.println("Create Huawei factory.");
    }

    /**
     * produce Huawei phones
     */
    @Override
    public IProduct makePhone() {
        System.out.println("Make Huawei phone...");
        return new HuaweiPhone();
    }

    /**
     * produce Huawei laptops
     */
    @Override
    public IProduct makeLaptop() {
        System.out.println("Make Huawei laptop...");
        return new HuaweiLaptop();
    }

    /**
     * produce Huawei earphones
     */
    @Override
    public IProduct makeEarphone() {
        System.out.println("Make Huawei earphone...");
        return new HuaweiEarphone();
    }
}
