package com.sissilab.dp.ox1_creational.ox11_singleton.attack;

import com.sissilab.dp.ox1_creational.ox11_singleton.EnumSingleton;
import com.sissilab.dp.ox1_creational.ox11_singleton.HungrySingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 模拟反射攻击
 */
public class ReflectionAttackTest {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // 反射攻击静态内部类 StaticInnerClassSingleton
        //reflectionAttackStaticInnerClassSingleton();

        // 反射攻击静态内部类安全加固版 StaticInnerClassSingletonV2
        //reflectionAttackStaticInnerClassSingletonV2();

        // 反射攻击 饿汉式 HungrySingleton
        //reflectionAttackHungrySingleton();

        // 反射攻击 饿汉式 防反射攻击加固版 HungrySingletonV2
        //reflectionAttackHungrySingletonV2();

        // 反射攻击 枚举类：java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        //reflectionAttackEnumSingleton();
    }


    /**
     * 反射攻击 饿汉式 HungrySingleton
     */
    private static void reflectionAttackHungrySingleton() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // 获取构造器
        Constructor<HungrySingleton> declaredConstructor = HungrySingleton.class.getDeclaredConstructor();
        // 拿到权限
        declaredConstructor.setAccessible(true);
        // reflectInstance：通过构造器来创建对象，拿到 HungrySingleton 类对象
        HungrySingleton reflectInstance = declaredConstructor.newInstance();

        // instance：直接通过静态方法拿到对象
        HungrySingleton instance = HungrySingleton.getInstance();

        // 比较通过反射拿到的对象和类静态方法拿到的对象是否为同一个 --> false
        System.out.println(reflectInstance == instance);
    }

    /**
     * 反射攻击 饿汉式 防反射攻击加固版 HungrySingletonV2
     */
    private static void reflectionAttackHungrySingletonV2() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // 获取构造器
        Constructor<HungrySingletonV2> declaredConstructor = HungrySingletonV2.class.getDeclaredConstructor();
        // 拿到权限
        declaredConstructor.setAccessible(true);
        // reflectInstance：通过构造器来创建对象，拿到 HungrySingletonV2 类对象
        HungrySingletonV2 reflectInstance = declaredConstructor.newInstance();

        // instance：直接通过静态方法拿到对象
        HungrySingletonV2 instance = HungrySingletonV2.getInstance();

        // 比较通过反射拿到的对象和类静态方法拿到的对象是否为同一个 --> 抛出异常，阻止反射攻击
        System.out.println(reflectInstance == instance);
    }

    /**
     * 反射攻击 枚举类：
     * 攻击失败 java.lang.IllegalArgumentException: Cannot reflectively create enum objects
     */
    private static void reflectionAttackEnumSingleton() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // 从字节码分析得知，枚举型会自动生成一个私有构造函数，含2个参数 (String, int)
        Constructor<EnumSingleton> declaredConstructors = EnumSingleton.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructors.setAccessible(true);
        EnumSingleton reflectInstance = declaredConstructors.newInstance("instance", 0);
    }
}

/**
 * 饿汉式单例 防反射攻击加固版
 */
class HungrySingletonV2 {

    private static HungrySingletonV2 instance = new HungrySingletonV2();

    private HungrySingletonV2() {
        // 防止反射攻击：饿汉/静态内部类 可通过该方式来阻止（懒汉无法防止）
        if (null != instance) {
            throw new RuntimeException("This Singleton class has been instantiated.");
        }
    }

    public static HungrySingletonV2 getInstance() {
        return instance;
    }
}

