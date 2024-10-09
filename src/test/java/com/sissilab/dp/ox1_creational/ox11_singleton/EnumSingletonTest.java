package com.sissilab.dp.ox1_creational.ox11_singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;


public class EnumSingletonTest {

    /**
     * 4. Enum Singleton: in a single-threaded environment
     */
    @Test
    public void testEnumSingletonSingleThreadSingleThread() {
        EnumSingleton instance1 = EnumSingleton.instance;
        EnumSingleton instance2 = EnumSingleton.instance;
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 4. Enum Singleton: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testEnumSingletonMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<EnumSingleton> callableTask = () -> EnumSingleton.instance;
        Future<EnumSingleton>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        EnumSingleton firstInstance = futures[0].get();
        for (Future<EnumSingleton> future : futures) {
            EnumSingleton instance = future.get();
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    @Test
    public void testReflectionAttackOnEnumSingleton() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor: only automatically generate a private constructor with two parameters, like `private EnumSingleton(String, int)`
        Constructor<EnumSingleton> constructor = EnumSingleton.class.getDeclaredConstructor(String.class, int.class);
        // set the accessibility of the constructor
        constructor.setAccessible(true);
        // reflectInstance: create the instance through constructor
        EnumSingleton reflectInstance = constructor.newInstance("instance", 0); // java.lang.IllegalArgumentException: Cannot reflectively create enum objects

        // instance: get the instance through the normal public static final `instance` field
        EnumSingleton instance = EnumSingleton.instance;

        Assertions.assertSame(reflectInstance, instance); // ×
    }

    @Test
    public void testSerializationAttackOnEnumSingleton() {
        EnumSingleton instance = EnumSingleton.instance;

        final Path serializedClassPath = Paths.get("TestSerialization-" + EnumSingleton.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize:
        EnumSingleton testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((EnumSingleton) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Enum Singleton is inherently safe from serialization attacks.
        Assertions.assertSame(instance, testSerializableInstance); // √
    }

}
