package com.xixi.lab.dp.ox1_creational.ox1_01_singleton;

import java.util.concurrent.TimeUnit;

/**
 * 懒汉式单例：
 * 推荐 “五、懒汉式 - volatile 双重检查（推荐使用）”
 */
public class LazySingleton {
    public static void main(String[] args) {
        /**
         * 一、模拟：懒汉式 - 线程不安全
         */
        // 1.1. 单线程情况
        /*LazySingletonUnsafeThread instance1 = LazySingletonUnsafeThread.getInstance();
        LazySingletonUnsafeThread instance2 = LazySingletonUnsafeThread.getInstance();
        System.out.println(instance1 == instance2);*/

        // 1.2. 多线程情况: instance1 和 instance2 有几率非同一个实例
        /*new Thread(() -> {
            LazySingletonUnsafeThread instance1 = LazySingletonUnsafeThread.getInstance();
            System.out.println(instance1); // com.xixi.lab.dp.ox1_creational.ox1_01_singleton.LazySingletonUnsafeThread@31bfbbf6
        }).start();
        new Thread(() -> {
            LazySingletonUnsafeThread instance2 = LazySingletonUnsafeThread.getInstance();
            System.out.println(instance2); // com.xixi.lab.dp.ox1_creational.ox1_01_singleton.LazySingletonUnsafeThread@7330523d
        }).start();*/

        /**
         * 二、模拟：懒汉式 - synchronized 方法
         */
        // 2.1. 多线程：instance1 和 instance2 为同一个实例
        /*new Thread(() -> {
            LazySingletonSynchronized instance1 = LazySingletonSynchronized.getInstance();
            System.out.println(instance1);
        }).start();
        new Thread(() -> {
            LazySingletonSynchronized instance2 = LazySingletonSynchronized.getInstance();
            System.out.println(instance2);
        }).start();*/

        /**
         * 三、模拟：懒汉式 - 延迟加锁
         */
        // 3.1. 多线程：instance1 和 instance2 一定几率非同一实例（多线程不可用）
        /*new Thread(() -> {
            LazySingletonDelayLock instance1 = LazySingletonDelayLock.getInstance();
            System.out.println(instance1);
        }).start();
        new Thread(() -> {
            LazySingletonDelayLock instance2 = LazySingletonDelayLock.getInstance();
            System.out.println(instance2);
        }).start();*/

    }
}

/**
 * 一、懒汉式 - 线程不安全（不可用）
 * 在单线程下正常，但在多线程在就会破坏单例，产生不同实例。
 */
class LazySingletonUnsafeThread {
    private static LazySingletonUnsafeThread instance = null;

    // 私有构造函数，限制用户自己创建实例
    private LazySingletonUnsafeThread() {
    }

    public static LazySingletonUnsafeThread getInstance() {
        if (null == instance) {
            try {
                // 模拟多线程下获取单例非都同一实例情况，增加模拟复现概率
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new LazySingletonUnsafeThread();
        }
        return instance;
    }
}

/**
 * 二、懒汉式 - synchronized 方法（不推荐使用）
 * 简单粗暴地在 getInstance() 方法上加锁synchronized
 * 在静态方法上加锁，实际上是对该类对象加锁（类锁），效率太低。当每次线程想通过 getInstance() 方法来获取该单例类的实例时，都会加锁，十分影响性能。
 */
class LazySingletonSynchronized {

    private static LazySingletonSynchronized instance = null;

    // 私有构造函数，限制用户自己创建实例
    private LazySingletonSynchronized() {
    }

    // 同步方法
    public synchronized static LazySingletonSynchronized getInstance() {
        if (null == instance) {
            try {
                // 模拟多线程下获取单例非都同一实例情况，增加模拟复现概率
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new LazySingletonSynchronized();
        }
        return instance;
    }
}

/**
 * 三、懒汉式 - 延迟加锁（不可用）
 * 欲实现只在第一次加锁，然后在临界区内进行实例化，后面判断 instance 非空时，会直接返回该单例类的实例对象。
 * 但是，在多线程环境下，依旧会存在线程安全问题
 */
class LazySingletonDelayLock {

    private static LazySingletonDelayLock instance = null;

    // 私有构造函数，限制用户自己创建实例
    private LazySingletonDelayLock() {
    }

    public static LazySingletonDelayLock getInstance() {
        if (null == instance) {
            // T2：由于T1还未实例化，此时 instance 为空，还会进来
            synchronized (LazySingletonDelayLock.class) {
                // T1：刚进临界区，还没完成实例化
                instance = new LazySingletonDelayLock();
            }
        }
        return instance;
    }
}

/**
 * 四、懒汉式 - 无 volatile 双重检查（不推荐使用）
 * 由于 instance 属性没有被 volatile 修饰，还是会因为指定重排导致错误。
 */
class LazySingletonNoVolatileDoubleCheck {

    private static LazySingletonNoVolatileDoubleCheck instance = null;

    // 私有构造函数，限制用户自己创建实例
    private LazySingletonNoVolatileDoubleCheck() {
    }

    public static LazySingletonNoVolatileDoubleCheck getInstance() {
        if (null == instance) { // T2：直接返回，而实际 T1 还没完成初始化，返回可导致空指针
            synchronized (LazySingletonNoVolatileDoubleCheck.class) {
                if (null == instance) {
                    instance = new LazySingletonNoVolatileDoubleCheck();
                    // 字节码层：1、开辟空间 2、初始化 3、引用赋值
                    // ==> 实际 JIT / CPU，可能发生指定重排：1、开辟空间 3、引用赋值（T1） 2、初始化
                }
            }
        }
        return instance;
    }
}

/**
 * 五、懒汉式 - volatile 双重检查（推荐使用）
 * 最推荐的懒汉式单例，既能保证线程安全，又可实现延迟加载
 */
class LazySingletonDoubleCheck {

    // volatile: 防止重排
    private static volatile LazySingletonDoubleCheck instance = null;

    // 私有构造函数，限制用户自己创建实例
    private LazySingletonDoubleCheck() {
    }

    public static LazySingletonDoubleCheck getInstance() {
        if (null == instance) {
            synchronized (LazySingletonDoubleCheck.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheck();
                }
            }
        }
        return instance;
    }
}
