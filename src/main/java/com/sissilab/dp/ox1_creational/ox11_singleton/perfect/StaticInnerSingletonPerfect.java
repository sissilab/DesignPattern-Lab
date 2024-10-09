package com.sissilab.dp.ox1_creational.ox11_singleton.perfect;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Static Inner Class Singleton: Perfect Version
 * 1. Using the characteristics of static inner class to provide lazy initialization and thread safety based on class loading mechanism
 * 2. Trying to use the private constructor checks to prevent reflection attacks, but cannot guarantee in all cases
 * 3. Using defined serialVersionUID and readResolve() to prevent serialization attacks
 * 4. Overriding clone() to prevent cloning attacks
 * 5. Using final class to prevent subclassing and avoid any subclass to break the singleton pattern
 */
public final class StaticInnerSingletonPerfect implements Serializable {

    // Explicitly defining `serialVersionUID` can avoid potential incompatibility issues.
    private static final long serialVersionUID = -1L;

    // static inner class to provide the singleton instance of the class
    private static class InstanceHolder {
        private static StaticInnerSingletonPerfect instance = new StaticInnerSingletonPerfect();
    }

    // private constructor: restricts users from creating instances themselves
    private StaticInnerSingletonPerfect() {
        // prevent reflection attack
        if (null != InstanceHolder.instance) {
            throw new IllegalStateException("This singleton instance already exists.");
        }
    }

    // controls access to the singleton instance
    public static StaticInnerSingletonPerfect getInstance() {
        return StaticInnerSingletonPerfect.InstanceHolder.instance;
    }

    // prevent serialization attacks
    Object readResolve() throws ObjectStreamException {
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
