package com.sissilab.dp.ox1_creational.ox11_singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 3. Static Inner Class Singleton:
 * <p>
 * In essence, it also leverages the class loading mechanism to ensure security.
 * Due to its characteristics, it guarantees that class initialization is triggered only when actually used, making it a form of lazy loading.
 * <p>
 * Pros:
 * 1. lazy initialization based on the characteristics of static inner class
 * 2. can guarantee thread safety for the static initialization of the `InstanceHolder` class based on class loading mechanism
 * 3. can avoid synchronization overhead, unlike the DCL pattern
 * 4. relatively easier to understand compared to the DCL pattern
 * <p>
 * Cons:
 * 1. inflexible to handle exceptions during the class creation
 * 2. unexpected issues due to class loading timing
 * <p>
 * Best Use Cases:
 * 1. Use it when the singleton class holds heavy resources.
 * 2. Use it when we want to delay the creation of the singleton instance until it is actually needed.
 * 3. Avoid it when we require complex initialization steps
 */
public class StaticInnerClassSingleton {

    // static inner class to provide the singleton instance of the class
    private static class InstanceHolder {
        private static final StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    // private constructor: restricts users from creating instances themselves.
    private StaticInnerClassSingleton() {
    }

    // controls access to the singleton instance
    public static StaticInnerClassSingleton getInstance() {
        // It is only here that the initialization of `InstanceHolder.instance` will be triggered.
        return InstanceHolder.instance;
    }
}

/**
 * 3.1. Static Inner Class Singleton: prevent reflection attack
 */
class StaticInnerSingletonReflectionProof {

    // static inner class to provide the singleton instance of the class
    private static class InstanceHolder {
        private static StaticInnerSingletonReflectionProof instance = new StaticInnerSingletonReflectionProof();
    }

    // private constructor: restricts users from creating instances themselves, but cannot prevent constructor reflection
    private StaticInnerSingletonReflectionProof() {
        // prevent reflection attack
        if (null != InstanceHolder.instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static StaticInnerSingletonReflectionProof getInstance() {
        // It is only here that the initialization of `InstanceHolder.instance` will be triggered.
        return InstanceHolder.instance;
    }
}

/**
 * 3.2. Static Inner Class Singleton: prevent serialization attack
 */
class StaticInnerSingletonSerializationProof implements Serializable {

    /**
     * If the `serialVersionUID` is not added during serialization, a version number will be automatically generated based on the current class data.
     * When deserializing, a version number will also be automatically generated.
     * If the two version numbers do not match (the class content is modified before deserialization), the following error will be reported:
     * `java.io.InvalidClassException: local class incompatible: stream classdesc serialVersionUID = -6193354024038071874, local class serialVersionUID = 3430229600994946531`
     * Specifying a serialVersionUID can solve this problem.
     */
    private static final long serialVersionUID = -1L;

    // static inner class to provide the singleton instance of the class
    private static class InstanceHolder {
        private static StaticInnerSingletonSerializationProof instance = new StaticInnerSingletonSerializationProof();
    }

    // private constructor: restricts users from creating instances themselves, but cannot prevent constructor reflection
    private StaticInnerSingletonSerializationProof() {
        // prevent reflection attack
        if (null != InstanceHolder.instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static StaticInnerSingletonSerializationProof getInstance() {
        return InstanceHolder.instance;
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
