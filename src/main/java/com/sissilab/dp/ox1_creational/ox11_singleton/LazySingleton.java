package com.sissilab.dp.ox1_creational.ox11_singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Lazy Initialization:
 * - 1.1. Lazy Singleton - Thread Unsafe (unavailable in a multi-threaded environment)
 * - 1.2. Lazy Singleton - synchronized (thread-safety, but inefficient in a multi-threaded environment)
 * - 1.3. Lazy Singleton - Delay Lock (unavailable in a multi-threaded environment)
 * - 1.4. Lazy Singleton - Double-checked Locking Without volatile (unavailable in a multi-threaded environment)
 * - 1.5. Lazy Singleton - Double-checked Locking (perfect ❤️)
 */
public class LazySingleton {
}

/**
 * 1.1. Lazy Singleton - Thread Unsafe (unavailable in a multi-threaded environment)
 * <p>
 * It works fine in a single-threaded environment but may break the singleton pattern in a multi-threaded environment,
 * resulting in different instances.
 */
class LazySingletonThreadUnsafe {

    // holds the singleton instance
    private static LazySingletonThreadUnsafe instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonThreadUnsafe() {
    }

    // controls access to the singleton instance
    public static LazySingletonThreadUnsafe getInstance() {
        if (null == instance) {
            /*try {
                // Simulate the scenario where multiple threads do not always get the same instance of a singleton,
                // increasing the probability of reproducing the issue.
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            instance = new LazySingletonThreadUnsafe();
        }
        return instance;
    }
}

/**
 * 1.2. Lazy Singleton - synchronized (thread-safety, but inefficient in a multi-threaded environment)
 * <p>
 * We can directly add `synchronized` to `getInstance()` method.
 * synchronized static method means that only one thread can execute any synchronized static method of a class at a time,
 * regardless of which object instance the method is called on.
 * <p>
 * Actually, it's a class-level lock and can lead to performance bottlenecks, as it limits concurrent execution.
 */
class LazySingletonSynchronized {

    // holds the singleton instance
    private static LazySingletonSynchronized instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonSynchronized() {
    }

    // synchronized static method: the class-level lock ensures that only one thread can execute the static synchronized method at a time.
    public synchronized static LazySingletonSynchronized getInstance() {
        if (null == instance) {
            /*try {
                // Simulate the scenario where multiple threads do not always get the same instance of a singleton,
                // increasing the probability of reproducing the issue.
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            instance = new LazySingletonSynchronized();
        }
        return instance;
    }
}

/**
 * 1.3. Lazy Singleton - Delay Lock (unavailable in a multi-threaded environment)
 * <p>
 * To achieve locking for instantiating only the first time, and then subsequently return the instance directly as the instance is not null.
 * However, in a multi-threaded environment, there will still be thread safety issues.
 */
class LazySingletonDelayLock {

    // holds the singleton instance
    private static LazySingletonDelayLock instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDelayLock() {
    }

    // delay lock in the if code block when `instance` is null
    public static LazySingletonDelayLock getInstance() {
        if (null == instance) {
            // T2: may still enter if code block here, as the `instance` field hasn't finished instantiation in T1 (`instance` is still null)
            synchronized (LazySingletonDelayLock.class) { // class-level lock
                // T1: just enters the critical section and hasn't instantiated yet
                instance = new LazySingletonDelayLock();
            }
        }
        return instance;
    }
}

/**
 * 1.4. Lazy Singleton - Double-checked Locking Without volatile (unavailable in a multi-threaded environment)
 * <p>
 * Trying to utilise double-checked locking to solve the multi-threaded issue, but still may get problems due to instructions reordering.
 */
class LazySingletonNoVolatileDoubleCheck {

    // holds the singleton instance
    private static LazySingletonNoVolatileDoubleCheck instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonNoVolatileDoubleCheck() {
    }

    // add double-checked locking without volatile `instance`
    public static LazySingletonNoVolatileDoubleCheck getInstance() {
        if (null == instance) { // T2: directly returns the `instance`, but not fully initialized, leading to a partially constructed or uninitialized instance being returned
            synchronized (LazySingletonNoVolatileDoubleCheck.class) {
                if (null == instance) {
                    instance = new LazySingletonNoVolatileDoubleCheck();
                    // bytecode: 1. space allocation -> 2. initialization -> 3. reference assignment
                    // may reorder instructions as follows:
                    // 1. space allocation
                    // 3. reference assignment // T1: the `instance` is assigned the reference, but not fully initialized
                    // 2. initialization
                }
            }
        }
        return instance;
    }
}

/**
 * 1.5. Lazy Singleton - Double-checked Locking (DCL): 👍 Recommended
 * <p>
 * The most recommended lazy initialization singleton pattern, resulting in both thread safety and lazy loading.
 * <p>
 * Pros:
 * 1. lazy initialization
 * 2. can guarantee thread safety using double-checked locking (DCL)
 * 3. can reduce locking overhead
 * <p>
 * Cons:
 * 1. more complex
 * 2. may influence its performance when facing a large number of initial `getInstance()` requests
 * <p>
 * Best Use Cases:
 * 1. Use it when the singleton class holds heavy resources.
 * 2. Use it when we want to delay the creation of the singleton instance until it is actually needed.
 * 3. Use it when we consider both performance and lazy initialization.
 * 4. Avoid it when the singleton class is lightweight or always needed.
 */
class LazySingletonDoubleCheck {

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheck instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDoubleCheck() {
    }

    // use double-checked locking
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

/**
 * 1.5.1. Lazy Singleton - Double-checked Locking (DCL): trys to prevent reflection attack, but fails to.
 * <p>
 * The reasons why it cannot prevent reflection attack?
 * 1. Reflection allows to bypass the typical access control through `constructor.setAccessible(true);`, even if a constructor is private.
 * 2. The Double-checked Locking mechanism is focused more on managing concurrent access and cannot specifically guard against reflection.
 */
class LazySingletonDoubleCheckReflectionProof {

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheckReflectionProof instance;

    // private constructor: restricts users from creating instances themselves, but cannot prevent constructor reflection
    private LazySingletonDoubleCheckReflectionProof() {
        // cannot prevent reflection attack: the first call using reflection and then subsequent calls using getInstance() will result in getting different instances.
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // use double-checked locking
    public static LazySingletonDoubleCheckReflectionProof getInstance() {
        if (null == instance) {
            synchronized (LazySingletonDoubleCheckReflectionProof.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheckReflectionProof();
                }
            }
        }
        return instance;
    }
}

/**
 * 1.5.2.1. Lazy Singleton - Double-checked Locking (DCL): enable serialization -> cannot prevent reflection attacks in all cases
 * The class has to implement `java.io.Serializable` to avoid throwing `java.io.NotSerializableException`.
 */
class LazySingletonDoubleCheckSerialization implements Serializable {

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheckSerialization instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDoubleCheckSerialization() {
        // cannot prevent reflection attacks in all cases
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // use double-checked locking
    public static LazySingletonDoubleCheckSerialization getInstance() {
        if (null == instance) {
            synchronized (LazySingletonDoubleCheckSerialization.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheckSerialization();
                }
            }
        }
        return instance;
    }
}

/**
 * 1.5.2.2. Lazy Singleton - Double-checked Locking (DCL): enable serialization
 */
class LazySingletonDoubleCheckSerializationProof implements Serializable {

    /**
     * It is mainly used for version control, ensuring compatibility between serialized and deserialized class versions.
     * If the `serialVersionUID` is not added during serialization, a version number will be automatically generated based on the current class data.
     * When deserializing, a version number will also be automatically generated.
     * If the two version numbers do not match (e.g. the class content is modified before deserialization), the following error will be reported:
     * `java.io.InvalidClassException: local class incompatible: stream classdesc serialVersionUID = -4190675925334731018, local class serialVersionUID = 1437610846000386433
     * Specifying a serialVersionUID can solve this problem.
     */
    private static final long serialVersionUID = -1L;

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheckSerializationProof instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDoubleCheckSerializationProof() {
        // cannot prevent reflection attacks in all cases
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // use double-checked locking
    public static LazySingletonDoubleCheckSerializationProof getInstance() {
        if (null == instance) {
            synchronized (LazySingletonDoubleCheckSerializationProof.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheckSerializationProof();
                }
            }
        }
        return instance;
    }

    /**
     * For non-Enum Singleton pattern, when facing serialization attacks, the `readResolve()` method must be implemented.
     *
     * @return
     * @throws ObjectStreamException
     */
    Object readResolve() throws ObjectStreamException {
        // during deserialization: returning the existing instance, instead of creating a new one.
        return getInstance();
    }
}
