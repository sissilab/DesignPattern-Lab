package com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory;


import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.AppleLaptop;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.ApplePhone;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;
import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.AppleEarphone;

/**
 * Apple Concrete Factory
 */
public class AppleFactory implements IFactory {

    public AppleFactory() {
        System.out.println("Create Apple factory.");
    }

    /**
     * produce Apple phones
     */
    @Override
    public IProduct makePhone() {
        System.out.println("Make Apple phone...");
        return new ApplePhone();
    }

    /**
     * produce Apple laptops
     */
    @Override
    public IProduct makeLaptop() {
        System.out.println("Make Apple laptop...");
        return new AppleLaptop();
    }

    /**
     * produce Apple earphones
     */
    @Override
    public IProduct makeEarphone() {
        System.out.println("Make Apple earphone...");
        return new AppleEarphone();
    }
}
