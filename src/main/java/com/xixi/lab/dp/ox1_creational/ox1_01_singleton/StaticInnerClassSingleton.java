package com.xixi.lab.dp.ox1_creational.ox1_01_singleton;

/**
 * 静态内部类式单例：
 * 本质上也是利用类的加载机制来保证安全，由于其特性，能保证在实际使用时，才会触发类的初始化，也是一种延迟加载。
 */
public class StaticInnerClassSingleton {

    private static class InstanceHolder {
        private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {
    }

    public static StaticInnerClassSingleton getInstance() {
        // 调用到这里，才会导致 InstanceHolder.instance 的初始化
        return InstanceHolder.instance;
    }
}