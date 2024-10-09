package com.sissilab.dp.ox1_creational.ox11_singleton;

/**
 * 2. Eager Singleton Wasting Reason
 * - 2.1. Eager Singleton Wasting Reason - static constant
 * - 2.2. Eager Singleton Wasting Reason - static block
 * - 2.3. Lazy Singleton - Double-checked Locking (only for comparison)
 */
public class EagerSingletonWastingReason {

    /**
     * For Eager Singleton, even if you don't get the instance of the Eager Singleton and just call any other methods, it'll still trigger the class creation due to the class loading mechanism.
     * But For Lazy Singleton, it won't trigger class creation and just execute that other method.
     */
    public static void main(String[] args) {
        /*
        EagerSingletonWastingReasonStaticConstant: Constructor...
        EagerSingletonWastingReasonStaticConstant: print test...
         */
        EagerSingletonWastingReasonStaticConstant.print(); // it triggers the class creation

        System.out.println("\n");

        /*
        EagerSingletonWastingReasonStaticBlock: static block start...
        EagerSingletonWastingReasonStaticBlock: Constructor...
        EagerSingletonWastingReasonStaticBlock: static block end!
        EagerSingletonWastingReasonStaticBlock: print test...
         */
        EagerSingletonWastingReasonStaticBlock.print(); // it triggers the class creation

        System.out.println("\n");

        /*
        LazySingletonDoubleCheckSample: print test...
         */
        LazySingletonDoubleCheckSample.print(); // it just executes `print()` without triggering the class creation
    }

}

/**
 * 2.1. Eager Singleton Wasting Reason - static constant
 */
class EagerSingletonWastingReasonStaticConstant {

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonWastingReasonStaticConstant instance = new EagerSingletonWastingReasonStaticConstant();

    // private constructor: restricts users from creating instances themselves.
    private EagerSingletonWastingReasonStaticConstant() {
        System.out.println("EagerSingletonWastingReasonStaticConstant: Constructor...");
    }

    // controls access to the singleton instance
    public static EagerSingletonWastingReasonStaticConstant getInstance() {
        System.out.println("EagerSingletonWastingReasonStaticConstant: getInstance...");
        return instance;
    }

    public static void print() {
        System.out.println("EagerSingletonWastingReasonStaticConstant: print test...");
    }

}

/**
 * 2.2. Eager Singleton Wasting Reason - static block
 */
class EagerSingletonWastingReasonStaticBlock {

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonWastingReasonStaticBlock instance;

    static {
        System.out.println("EagerSingletonWastingReasonStaticBlock: static block start...");
        instance = new EagerSingletonWastingReasonStaticBlock();
        System.out.println("EagerSingletonWastingReasonStaticBlock: static block end!");
    }

    // private constructor: restricts users from creating instances themselves.
    private EagerSingletonWastingReasonStaticBlock() {
        System.out.println("EagerSingletonWastingReasonStaticBlock: Constructor...");
    }

    // controls access to the singleton instance
    public static EagerSingletonWastingReasonStaticBlock getInstance() {
        System.out.println("EagerSingletonWastingReasonStaticBlock: getInstance...");
        return instance;
    }

    public static void print() {
        System.out.println("EagerSingletonWastingReasonStaticBlock: print test...");
    }

}

/**
 * 2.3. Lazy Singleton - Double-checked Locking
 */
class LazySingletonDoubleCheckSample {

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheckSample instance = null;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDoubleCheckSample() {
        System.out.println("LazySingletonDoubleCheckSample: Constructor...");
    }

    // use double-checked locking
    public static LazySingletonDoubleCheckSample getInstance() {
        System.out.println("LazySingletonDoubleCheckSample: getInstance start...");
        if (null == instance) {
            synchronized (LazySingletonDoubleCheckSample.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheckSample();
                }
            }
        }
        System.out.println("LazySingletonDoubleCheckSample: getInstance end!");
        return instance;
    }

    public static void print() {
        System.out.println("LazySingletonDoubleCheckSample: print test...");
    }

}
