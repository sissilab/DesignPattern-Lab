package com.sissilab.dp.ox1_creational.ox11_singleton.perfect;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Lazy Singleton - Double-checked Locking (DCL): Perfect Version
 * 1. Using volatile + DCL to guarantee thread safety and minimize the use of synchronization
 * 2. Trying to use the private constructor checks to prevent reflection attacks, but cannot guarantee in all cases
 * 3. Using defined serialVersionUID and readResolve() to prevent serialization attacks
 * 4. Overriding clone() to prevent cloning attacks
 * 5. Using final class to prevent subclassing and avoid any subclass to break the singleton pattern
 */
public final class LazySingletonDoubleCheckPerfect implements Serializable {

    // Explicitly defining `serialVersionUID` can avoid potential incompatibility issues.
    private static final long serialVersionUID = -1L;

    // holds the singleton instance: add volatile to prevent reordering
    private static volatile LazySingletonDoubleCheckPerfect instance;

    // private constructor: restricts users from creating instances themselves.
    private LazySingletonDoubleCheckPerfect() {
        // cannot prevent reflection attacks in all cases
        if (null != instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // use double-checked locking
    public static LazySingletonDoubleCheckPerfect getInstance() {
        if (null == instance) {
            synchronized (LazySingletonDoubleCheckPerfect.class) {
                if (null == instance) {
                    instance = new LazySingletonDoubleCheckPerfect();
                }
            }
        }
        return instance;
    }

    // Prevent serialization attacks
    private Object readResolve() throws ObjectStreamException {
        // during deserialization: returning the existing instance, instead of creating a new one.
        return getInstance();
    }

    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // (1) throw an exception to prevent cloning
        throw new CloneNotSupportedException("Cloning of this singleton instance is not allowed");
        // (2) or directly return the same instance from the clone method
        //return getInstance();
    }
}
