package com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.simple_factory.factory;

import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.simple_factory.product.Earphone;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.simple_factory.product.IProduct;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.simple_factory.product.Laptop;
import com.xixi.lab.dp.ox1_creational.ox1_02_factory_method.simple_factory.product.Phone;

public class SimpleFactory {

    /**
     * 产品类型
     */
    public enum PRODUCT_TYPE {
        PHONE,
        LAPTOP,
        EARPHONE
    }

    /**
     * 根据类型，创建不同的产品
     *
     * @param productType
     * @return
     */
    public static IProduct createProduct(PRODUCT_TYPE productType) {
        if (null != productType) {
            switch (productType) {
                case PHONE:
                    return new Phone();
                case LAPTOP:
                    return new Laptop();
                case EARPHONE:
                    return new Earphone();
                default:
                    break;
            }
        }
        return null;
    }
}
