package com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.factory;

import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.product.Laptop;
import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.product.Earphone;
import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.product.IProduct;
import com.sissilab.dp.ox1_creational.ox12_factory_method.simple_factory.product.Phone;

public class SimpleFactory {

    public enum PRODUCT_TYPE {
        PHONE,
        LAPTOP,
        EARPHONE
    }

    /**
     * Create a new product object according to the product type
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
