package com.xixi.lab.dp.ox1_creational.ox1_01_singleton.attack;

import java.io.*;

/**
 * 模拟序列化攻击 静态内部类
 */
public class SerializableAttack {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /**
         * 序列化攻击 静态内部类：
         * 对单例对象 instance 先序列化，再反序列化后，拿到的 testSerializableInstance，不是同一个对象
         */
        StaticInnerClassSingletonSerializable instance = StaticInnerClassSingletonSerializable.getInstance();
        serialize(instance);
        deserialize(instance); // --> false

        /**
         * 序列化攻击 静态内部类 v2：
         */
        StaticInnerClassSingletonSerializableV2 instancev2 = StaticInnerClassSingletonSerializableV2.getInstance();
        serialize(instancev2);
        deserialize(instancev2); // --> true
    }

    /**
     * 序列化
     */
    private static <T> void serialize(T instance) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("TestSerializable"));
        objectOutputStream.writeObject(instance);
        objectOutputStream.close();
    }

    /**
     * 反序列化：不会调用构造函数，从字节流中读取数据进行初始化
     */
    private static <T> void deserialize(T instance) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("TestSerializable"));
        T testSerializableInstance = ((T) objectInputStream.readObject());
        System.out.println(instance == testSerializableInstance);
    }
}

/**
 * 静态内部类 单例模式：实现序列化
 */
class StaticInnerClassSingletonSerializable implements Serializable {

    private static class InstanceHolder {
        private static StaticInnerClassSingletonSerializable instance = new StaticInnerClassSingletonSerializable();
    }

    private StaticInnerClassSingletonSerializable() {
        if (null != StaticInnerClassSingletonSerializable.InstanceHolder.instance) {
            throw new RuntimeException("This Singleton class has been instantiated.");
        }
    }

    public static StaticInnerClassSingletonSerializable getInstance() {
        return InstanceHolder.instance;
    }
}

/**
 * 静态内部类 单例模式
 * 序列化有它自己的一套机制，它读取字节流数据，并不会调用构造函数。
 * 针对非枚举型单例，要想实现可序列化，需加上版本号 serialVersionUID，还需要实现方法 readResolve()，并返回该类实例。这样就可以拿到同一个对象
 */
class StaticInnerClassSingletonSerializableV2 implements Serializable {

    // 若序列化时未加版本号 serialVersionUID，会根据当前类数据自动生成一个版本号，当反序列化时，亦会自动生成一个版本号，若2个版本号不对应（反序列化前修改类内容），则会报如下错：
    // java.io.InvalidClassException: local class incompatible: stream classdesc serialVersionUID = -6193354024038071874, local class serialVersionUID = 3430229600994946531
    // 指定一个版本号 serialVersionUID 即可解决该问题
    private static final long serialVersionUID = -1L;

    private static class InstanceHolder {
        private static StaticInnerClassSingletonSerializableV2 instance = new StaticInnerClassSingletonSerializableV2();
    }

    private StaticInnerClassSingletonSerializableV2() {
        if (null != StaticInnerClassSingletonSerializableV2.InstanceHolder.instance) {
            throw new RuntimeException("This Singleton class has been instantiated.");
        }
    }

    public static StaticInnerClassSingletonSerializableV2 getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 对于非枚举型单例模式在面对序列化攻击时，必须实现readResolve方法
     *
     * @return
     * @throws ObjectStreamException
     */
    Object readResolve() throws ObjectStreamException {
        return StaticInnerClassSingletonSerializableV2.getInstance();
    }
}
