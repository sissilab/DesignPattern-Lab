package com.sissilab.dp.ox1_creational.ox13_abstract_factory.factory;

import com.sissilab.dp.ox1_creational.ox13_abstract_factory.product.IProduct;

// Abstract Factory: create different products
public interface IFactory {

    IProduct makePhone();

    IProduct makeLaptop();

    IProduct makeEarphone();
}
