package com.sissilab.dp.ox1_creational.ox11_singleton;

/**
 * 4. Enum Singleton: 👍 Recommended
 * <p>
 * Pros:
 * 1. very easy to implement and the code is concise.
 * 2. inherently thread-safe as classloader mechanism, private constructor, and public static final instance field.
 * 3. can prevent reflection attacks because it'll throw an IllegalArgumentException while calling `newInstance()`.
 * 4. inherently serializable and can prevent serialization attacks.
 * <p>
 * Cons:
 * 1. no lazy loading.
 * 2. cannot extend other classes because an enum class implicitly inherits `java.lang.Enum`.
 * 3. inconvenient to debug because some key codes are implicitly auto-generated, like private constructor, static initialization block, ...
 */
public enum EnumSingleton {
    instance;

    public void anyMethod() {
    }

}
