package com.xixi.lab.dp.ox1_creational.ox1_01_singleton;

/**
 * 饿汉式单例：
 * 借助 JVM 的类加载机制，来保证实例的唯一性，因为初始化只会执行一次，且 JVM 会以同步的形式来完成类的加载过程。
 * 在类加载时就完成初始化了，基于类加载机制来保证单例，没有加锁，从而执行效率更高。
 * 但由于在类装载时就实例化，没有延迟加载的优势，如实例化后，后续缺没用到，则会造成浪费。
 */
public class HungrySingleton {

    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}


