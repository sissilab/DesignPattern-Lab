package com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.factory;

import com.xixi.lab.dp.ox1_creational.ox1_03_abstract_factory.product.IProduct;

// 抽象工厂
public interface IFactory {
    /**
     * 生产手机
     */
    IProduct makePhone();

    /**
     * 生产笔记本
     */
    IProduct makeLaptop();

    /**
     * 生产耳机
     */
    IProduct makeEarphone();
}
