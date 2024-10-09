package com.sissilab.dp.ox1_creational.ox11_singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 2. Eager Singleton
 * - 2.1. Eager Singleton - static constant: 👍 Recommended
 * - 2.1.1. Eager Singleton: prevent reflection
 * - 2.1.2. Eager Singleton: prevent serialization
 * ---
 * - 2.2. Eager Singleton - static block: 👍 Recommended
 * - 2.2.1. Eager Singleton - static block: prevent reflection
 * - 2.2.2. Eager Singleton - static block: prevent serialization
 */
public class EagerSingleton {
}

/**
 * 2.1. Eager Singleton - static constant: 👍 Recommended
 * <p>
 * Pros:
 * 1. can guarantee the creation is thread-safe based on class loading mechanism without the need for synchronization
 * 2. more straightforward and easy to understand, and no need for additional code to handle lazy initialization or synchronization mechanisms
 * 3. no performance overhead during `getInstance()`, as the instance is created at the time of class loading
 * <p>
 * Cons:
 * 1. may waste resources due to prematurely creation, especially for the resource-heavy singleton classes with large caches or database connections
 * 2. lack of lazy initialization
 * 3. inflexible to handle exceptions during the class creation using static constant
 * <p>
 * Best Use Cases:
 * 1. Use it when the singleton class is relatively lightweight.
 * 2. Use it when the singleton class is frequently needed during the application's lifecycle.
 * 3. Avoid it when lazy loading is preferable.
 */
class EagerSingletonStaticConstant {

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonStaticConstant instance = new EagerSingletonStaticConstant();

    // private constructor: restricts users from creating instances themselves.
    private EagerSingletonStaticConstant() {
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticConstant getInstance() {
        return instance;
    }
}

/**
 * 2.1.1. Eager Singleton: prevent reflection
 */
class EagerSingletonStaticConstantReflectionProof {

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonStaticConstantReflectionProof instance = new EagerSingletonStaticConstantReflectionProof();

    // private constructor: restricts users from creating instances themselves
    private EagerSingletonStaticConstantReflectionProof() {
        // prevent reflection attack
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticConstantReflectionProof getInstance() {
        return instance;
    }
}

/**
 * 2.1.2. Eager Singleton: prevent serialization
 */
class EagerSingletonStaticConstantSerializationProof implements Serializable {

    // Explicitly defining `serialVersionUID` can avoid potential incompatibility issues.
    private static final long serialVersionUID = -1L;

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonStaticConstantSerializationProof instance = new EagerSingletonStaticConstantSerializationProof();

    // private constructor: restricts users from creating instances themselves
    private EagerSingletonStaticConstantSerializationProof() {
        // prevent reflection attack
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticConstantSerializationProof getInstance() {
        return instance;
    }

    Object readResolve() throws ObjectStreamException {
        // during deserialization: returning the existing instance, instead of creating a new one.
        return getInstance();
    }
}

/**
 * 2.2. Eager Singleton - static block: 👍 Recommended
 * <p>
 * Pros:
 * 1. can guarantee the creation is thread-safe based on class loading mechanism without the need for synchronization
 * 2. more straightforward and easy to understand, and no need for additional code to handle lazy initialization or synchronization mechanisms
 * 3. no performance overhead during `getInstance()`, as the instance is created at the time of class loading
 * 4. flexible to handle more complex logic or exception in static block during the creation
 * <p>
 * Cons:
 * 1. may waste resources due to prematurely creation, especially for the resource-heavy singleton classes with large caches or database connections
 * 2. lack of lazy initialization
 * <p>
 * Best Use Cases:
 * 1. Use it when the singleton class is relatively lightweight.
 * 2. Use it when the singleton class is frequently needed during the application's lifecycle.
 * 3. Use it when we require complex initialization steps, as the static block gives flexibility to handle.
 * 4. Avoid it when lazy loading is preferable.
 */
class EagerSingletonStaticBlock {

    // holds the singleton instance: initialized when loading the class
    private static final EagerSingletonStaticBlock instance;

    static {
        // ...
        instance = new EagerSingletonStaticBlock();
        // ...
    }

    // private constructor: restricts users from creating instances themselves.
    private EagerSingletonStaticBlock() {
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticBlock getInstance() {
        return instance;
    }
}

/**
 * 2.2.1. Eager Singleton - static block: prevent reflection
 */
class EagerSingletonStaticBlockReflectionProof {

    // holds the singleton instance: initialized when loading the class in the static block
    private static final EagerSingletonStaticBlockReflectionProof instance;

    static {
        instance = new EagerSingletonStaticBlockReflectionProof();
    }

    // private constructor: restricts users from creating instances themselves
    private EagerSingletonStaticBlockReflectionProof() {
        // prevent reflection attack
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticBlockReflectionProof getInstance() {
        return instance;
    }
}

/**
 * 2.2.2. Eager Singleton - static block: prevent serialization
 */
class EagerSingletonStaticBlockSerializationProof implements Serializable {

    // Explicitly defining `serialVersionUID` can avoid potential incompatibility issues.
    private static final long serialVersionUID = -1L;

    // holds the singleton instance: initialized when loading the class in the static block
    private static final EagerSingletonStaticBlockSerializationProof instance;

    static {
        instance = new EagerSingletonStaticBlockSerializationProof();
    }

    // private constructor: restricts users from creating instances themselves
    private EagerSingletonStaticBlockSerializationProof() {
        // prevent reflection attack
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static EagerSingletonStaticBlockSerializationProof getInstance() {
        return instance;
    }

    // prevent serialization attack
    Object readResolve() throws ObjectStreamException {
        // during deserialization: returning the existing instance, instead of creating a new one.
        return getInstance();
    }
}
