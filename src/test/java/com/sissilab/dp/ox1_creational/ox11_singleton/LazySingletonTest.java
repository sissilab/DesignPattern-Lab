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


public class LazySingletonTest {

    /******************************************************************
     ************** 1.1. Lazy Singleton - Thread Unsafe ***************
     ******************************************************************/

    /**
     * 1.1. Lazy Singleton - Thread Unsafe: in a single-threaded environment
     */
    @Test
    public void testLazySingletonThreadUnsafeSingleThread() {
        LazySingletonThreadUnsafe instance1 = LazySingletonThreadUnsafe.getInstance();
        LazySingletonThreadUnsafe instance2 = LazySingletonThreadUnsafe.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 1.1. Lazy Singleton - Thread Unsafe: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLazySingletonThreadUnsafeMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Future<LazySingletonThreadUnsafe>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(LazySingletonThreadUnsafe::getInstance);
        }

        LazySingletonThreadUnsafe firstInstance = futures[0].get();
        for (Future<LazySingletonThreadUnsafe> future : futures) {
            LazySingletonThreadUnsafe instance = future.get();
            System.out.println(instance);
            // It may break the singleton pattern in a multi-threaded environment, resulting in different instances.
            // If the issue cannot be reproduced, try running the test method multiple times or uncommenting the code in LazySingletonThreadUnsafe.getInstance().
            Assertions.assertSame(firstInstance, instance); // ×
        }

        executorService.shutdown();
    }

    /******************************************************************
     ************** 1.2. Lazy Singleton - synchronized ****************
     ******************************************************************/

    /**
     * 1.2. Lazy Singleton - synchronized: in a single-threaded environment
     */
    @Test
    public void testLazySingletonSynchronizedSingleThread() {
        LazySingletonSynchronized instance1 = LazySingletonSynchronized.getInstance();
        LazySingletonSynchronized instance2 = LazySingletonSynchronized.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 1.2. Lazy Singleton - synchronized: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLazySingletonSynchronizedMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<LazySingletonSynchronized> callableTask = () -> LazySingletonSynchronized.getInstance();
        Future<LazySingletonSynchronized>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        LazySingletonSynchronized firstInstance = futures[0].get();
        for (Future<LazySingletonSynchronized> future : futures) {
            LazySingletonSynchronized instance = future.get();
            System.out.println(instance);
            // When we run `getInstance()` method to return the instance, it will lock each time due to its class-level lock (synchronized static method).
            // We can get the same instance even in a multi-threaded environment.
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    /******************************************************************
     ************** 1.3. Lazy Singleton - Delay Lock ****************
     ******************************************************************/

    /**
     * 1.3. Lazy Singleton - Delay Lock: in a single-threaded environment
     */
    @Test
    public void testLazySingletonDelayLockSingleThread() {
        LazySingletonDelayLock instance1 = LazySingletonDelayLock.getInstance();
        LazySingletonDelayLock instance2 = LazySingletonDelayLock.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 1.3. Lazy Singleton - Delay Lock: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLazySingletonDelayLockMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<LazySingletonDelayLock> callableTask = () -> LazySingletonDelayLock.getInstance();
        Future<LazySingletonDelayLock>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        LazySingletonDelayLock firstInstance = futures[0].get();
        for (Future<LazySingletonDelayLock> future : futures) {
            LazySingletonDelayLock instance = future.get();
            System.out.println(instance);
            // Even if the lock has been delayed in the if code block, it may still return 2 different object instances.
            Assertions.assertSame(firstInstance, instance); // ×
        }

        executorService.shutdown();
    }

    /*********************************************************************************
     ******** 1.4. Lazy Singleton - Double-checked Locking Without volatile **********
     *********************************************************************************/

    /**
     * 1.4. Lazy Singleton - Double-checked Locking Without volatile: in a single-threaded environment
     */
    @Test
    public void testLazySingletonNoVolatileDoubleCheckSingleThread() {
        LazySingletonNoVolatileDoubleCheck instance1 = LazySingletonNoVolatileDoubleCheck.getInstance();
        LazySingletonNoVolatileDoubleCheck instance2 = LazySingletonNoVolatileDoubleCheck.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 1.4. Lazy Singleton - Double-checked Locking Without volatile: in a multi-threaded environment
     * It's difficult to reproduce directly in unit tests!
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLazySingletonNoVolatileDoubleCheckMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<LazySingletonNoVolatileDoubleCheck> callableTask = () -> LazySingletonNoVolatileDoubleCheck.getInstance();
        Future<LazySingletonNoVolatileDoubleCheck>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        LazySingletonNoVolatileDoubleCheck firstInstance = futures[0].get();
        for (Future<LazySingletonNoVolatileDoubleCheck> future : futures) {
            LazySingletonNoVolatileDoubleCheck instance = future.get();
            System.out.println(instance);
            // Even if the lock has been delayed in the if code block, it may still return 2 different object instances.
            Assertions.assertSame(firstInstance, instance); // ×
        }

        executorService.shutdown();
    }

    /****************************************************************
     ******** 1.5. Lazy Singleton - Double-checked Locking (DCL) **********
     ****************************************************************/

    /**
     * 1.5. Lazy Singleton - Double-checked Locking (DCL): in a single-threaded environment
     */
    @Test
    public void testLazySingletonDoubleCheckSingleThread() {
        LazySingletonDoubleCheck instance1 = LazySingletonDoubleCheck.getInstance();
        LazySingletonDoubleCheck instance2 = LazySingletonDoubleCheck.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 1.5. Lazy Singleton - Double-checked Locking (DCL): in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testLazySingletonDoubleCheckMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<LazySingletonDoubleCheck> callableTask = () -> LazySingletonDoubleCheck.getInstance();
        Future<LazySingletonDoubleCheck>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        LazySingletonDoubleCheck firstInstance = futures[0].get();
        for (Future<LazySingletonDoubleCheck> future : futures) {
            LazySingletonDoubleCheck instance = future.get();
            System.out.println(instance);
            // ensure same instance: thread safety + lazy loading
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    /**
     * 1.5. Lazy Singleton - Double-checked Locking (DCL): test reflection attacks without any check in the private constructor
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionAttackOnLazySingletonDoubleCheck() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<LazySingletonDoubleCheck> constructor = LazySingletonDoubleCheck.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);
        // reflectInstance: create the instance through constructor
        LazySingletonDoubleCheck reflectInstance = constructor.newInstance();

        // instance: get the instance through the normal static method `getInstance()`
        LazySingletonDoubleCheck instance = LazySingletonDoubleCheck.getInstance();

        // Compare whether the instance obtained through reflection and the instance obtained through static method `getInstance()` are the same. --> false
        Assertions.assertSame(reflectInstance, instance); // ×
    }

    /**
     * 1.5.1. Lazy Singleton - Double-checked Locking (DCL): test reflection attacks with a check in the private constructor
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionAttackOnLazySingletonDoubleCheckReflectionProof() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<LazySingletonDoubleCheckReflectionProof> constructor = LazySingletonDoubleCheckReflectionProof.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);
        // reflectInstance: create the instance through constructor
        LazySingletonDoubleCheckReflectionProof reflectInstance = constructor.newInstance();

        // instance: get the instance through the normal static method `getInstance()`
        LazySingletonDoubleCheckReflectionProof instance = LazySingletonDoubleCheckReflectionProof.getInstance();

        /*
        - case 1 (√): If we call `getInstance()` first and then call `constructor.newInstance()`, an error will be thrown to prevent reflection attacks.
        - case 2 (×): If we call `constructor.newInstance()` first and then call `getInstance()`, we will get two different instances leading to break singleton pattern.
         */
        Assertions.assertSame(reflectInstance, instance); // ×
    }

    /**
     * 1.5.2.1. Lazy Singleton - Double-checked Locking (DCL): test serialization attacks
     */
    @Test
    public void testSerializationAttackOnLazySingletonDoubleCheckSerialization() {
        LazySingletonDoubleCheckSerialization instance = LazySingletonDoubleCheckSerialization.getInstance();

        final Path serializedClassPath = Paths.get("TestSerialization-" + LazySingletonDoubleCheckSerialization.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize: the constructor will not be called, the data will be initialized by reading from the byte stream.
        LazySingletonDoubleCheckSerialization testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((LazySingletonDoubleCheckSerialization) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertSame(instance, testSerializableInstance); // ×
    }

    /**
     * 1.5.2.2. Lazy Singleton - Double-checked Locking (DCL): test serialization attacks with enhanced practices
     */
    @Test
    public void testSerializationAttackOnLazySingletonDoubleCheckSerializationProof() {
        LazySingletonDoubleCheckSerializationProof instance = LazySingletonDoubleCheckSerializationProof.getInstance();

        final Path serializedClassPath = Paths.get("TestSerialization-" + LazySingletonDoubleCheckSerializationProof.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize:
        LazySingletonDoubleCheckSerializationProof testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((LazySingletonDoubleCheckSerializationProof) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Specifying a serialVersionUID and implementing readResolve() can keep the same instance.
        Assertions.assertSame(instance, testSerializableInstance); // √
    }

}
