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

import static org.junit.jupiter.api.Assertions.*;

public class StaticInnerClassSingletonTest {

    /**
     * 3. Static Inner Class Singleton: in a single-threaded environment
     */
    @Test
    public void testStaticInnerClassSingletonSingleThread() {
        StaticInnerClassSingleton instance1 = StaticInnerClassSingleton.getInstance();
        StaticInnerClassSingleton instance2 = StaticInnerClassSingleton.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 3. Static Inner Class Singleton: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testStaticInnerClassSingletonMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<StaticInnerClassSingleton> callableTask = () -> StaticInnerClassSingleton.getInstance();
        Future<StaticInnerClassSingleton>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        StaticInnerClassSingleton firstInstance = futures[0].get();
        for (Future<StaticInnerClassSingleton> future : futures) {
            StaticInnerClassSingleton instance = future.get();
            System.out.println(instance);
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    @Test
    public void testReflectionAttackOnStaticInnerClassSingleton() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<StaticInnerClassSingleton> constructor = StaticInnerClassSingleton.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);
        // reflectInstance: create the instance through constructor
        StaticInnerClassSingleton reflectInstance = constructor.newInstance();

        // instance: get the instance through the normal static method `getInstance()`
        StaticInnerClassSingleton instance = StaticInnerClassSingleton.getInstance();

        // Compare whether the instance obtained through reflection and the instance obtained through static method `getInstance()` are the same. --> false
        Assertions.assertSame(reflectInstance, instance); // ×
    }

    @Test
    public void testReflectionAttackOnStaticInnerSingletonReflectionProof() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<StaticInnerSingletonReflectionProof> constructor = StaticInnerSingletonReflectionProof.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);

        // reflectInstance: create the instance through constructor
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            StaticInnerSingletonReflectionProof reflectInstance = constructor.newInstance();
        }, "Constructor should throw an exception when invoked reflectively.");

        Throwable targetException = exception.getTargetException();
        assertTrue(targetException instanceof IllegalStateException); // √
        assertEquals("This singleton instance already exists.", targetException.getMessage()); // √
    }

    @Test
    public void testStaticInnerSingletonSerializationProof() {
        StaticInnerSingletonSerializationProof instance = StaticInnerSingletonSerializationProof.getInstance();

        final Path serializedClassPath = Paths.get("TestSerialization-" + StaticInnerSingletonSerializationProof.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize:
        StaticInnerSingletonSerializationProof testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((StaticInnerSingletonSerializationProof) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertSame(instance, testSerializableInstance); // √
    }


}
