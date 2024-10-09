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


public class EagerSingletonTest {

    /******************************************************************
     ************** 2.1. Eager Singleton - static constant ***************
     ******************************************************************/

    /**
     * 2.1. Eager Singleton - static constant: in a single-threaded environment
     */
    @Test
    public void testEagerSingletonStaticConstantSingleThread() {
        EagerSingletonStaticConstant instance1 = EagerSingletonStaticConstant.getInstance();
        EagerSingletonStaticConstant instance2 = EagerSingletonStaticConstant.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 2.1. Eager Singleton - static constant: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testEagerSingletonStaticConstantMultiThread() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<EagerSingletonStaticConstant> callableTask = () -> EagerSingletonStaticConstant.getInstance();
        Future<EagerSingletonStaticConstant>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        EagerSingletonStaticConstant firstInstance = futures[0].get();
        for (Future<EagerSingletonStaticConstant> future : futures) {
            EagerSingletonStaticConstant instance = future.get();
            System.out.println(instance);
            // The JVM class loading mechanism can guarantee thread safety and only one instance.
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    /**
     * 2.1. Eager Singleton - static constant: cannot prevent reflection attacks
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionAttackOnEagerSingletonStaticConstant() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<EagerSingletonStaticConstant> constructor = EagerSingletonStaticConstant.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);
        // reflectInstance: create the instance through constructor
        EagerSingletonStaticConstant reflectInstance = constructor.newInstance();

        // instance: get the instance through the normal static method `getInstance()`
        EagerSingletonStaticConstant instance = EagerSingletonStaticConstant.getInstance();

        // Compare whether the instance obtained through reflection and the instance obtained through static method `getInstance()` are the same. --> false
        Assertions.assertSame(reflectInstance, instance); // ×
    }

    /**
     * 2.1.1. Eager Singleton: prevent reflection
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionAttackOnEagerSingletonStaticConstantReflectionProof() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<EagerSingletonStaticConstantReflectionProof> constructor = EagerSingletonStaticConstantReflectionProof.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);

        // reflectInstance: create the instance through constructor
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            EagerSingletonStaticConstantReflectionProof reflectInstance = constructor.newInstance();
        }, "Constructor should throw an exception when invoked reflectively.");

        Throwable targetException = exception.getTargetException();
        assertTrue(targetException instanceof IllegalStateException); // √
        assertEquals("This singleton instance already exists.", targetException.getMessage()); // √
    }

    /**
     * 2.1.2. Eager Singleton: prevent serialization
     */
    @Test
    public void testSerializationAttackOnEagerSingletonStaticConstantSerializationProof() {
        EagerSingletonStaticConstantSerializationProof instance = EagerSingletonStaticConstantSerializationProof.getInstance();

        final Path serializedClassPath = Paths.get("TestSerialization-" + EagerSingletonStaticConstantSerializationProof.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize:
        EagerSingletonStaticConstantSerializationProof testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((EagerSingletonStaticConstantSerializationProof) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertSame(instance, testSerializableInstance); // √
    }

    /******************************************************************
     ************** 2.2. Eager Singleton - static block ****************
     ******************************************************************/

    /**
     * 2.2. Eager Singleton - static block: in a single-threaded environment
     */
    @Test
    public void testEagerSingletonStaticBlock() {
        System.out.println("start");
        EagerSingletonStaticBlock instance1 = EagerSingletonStaticBlock.getInstance();
        EagerSingletonStaticBlock instance2 = EagerSingletonStaticBlock.getInstance();
        System.out.println("instance1: " + instance1);
        System.out.println("instance2: " + instance2);
        // `instance1` and `instance2` are the same instance.
        Assertions.assertSame(instance1, instance2); // √
    }

    /**
     * 2.2. Eager Singleton - static block: in a multi-threaded environment
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testEagerSingletonStaticBlockConcurrency() throws ExecutionException, InterruptedException {
        final int THREAD_COUNT = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        Callable<EagerSingletonStaticBlock> callableTask = () -> EagerSingletonStaticBlock.getInstance();
        Future<EagerSingletonStaticBlock>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(callableTask);
        }

        EagerSingletonStaticBlock firstInstance = futures[0].get();
        for (Future<EagerSingletonStaticBlock> future : futures) {
            EagerSingletonStaticBlock instance = future.get();
            System.out.println(instance);
            // We can get the same instance even in a multi-threaded environment.
            Assertions.assertSame(firstInstance, instance); // √
        }

        executorService.shutdown();
    }

    /**
     * 2.2.1. Eager Singleton - static block: prevent reflection
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionAttackOnEagerSingletonStaticBlockReflectionProof() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // get the constructor
        Constructor<EagerSingletonStaticBlockReflectionProof> constructor = EagerSingletonStaticBlockReflectionProof.class.getDeclaredConstructor();
        // set the accessibility of the constructor
        constructor.setAccessible(true);

        // reflectInstance: create the instance through constructor
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            EagerSingletonStaticBlockReflectionProof reflectInstance = constructor.newInstance();
        }, "Constructor should throw an exception when invoked reflectively.");

        Throwable targetException = exception.getTargetException();
        assertTrue(targetException instanceof IllegalStateException); // √
        assertEquals("This singleton instance already exists.", targetException.getMessage()); // √
    }

    /**
     * 2.2.2. Eager Singleton - static block: prevent serialization
     */
    @Test
    public void testSerializationAttackOnEagerSingletonStaticBlockSerializationProof() {
        EagerSingletonStaticBlockSerializationProof instance = EagerSingletonStaticBlockSerializationProof.getInstance();

        final Path serializedClassPath = Paths.get("TestSerialization-" + EagerSingletonStaticBlockSerializationProof.class.getSimpleName());

        // serialize:
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(serializedClassPath))) {
            objectOutputStream.writeObject(instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // deserialize:
        EagerSingletonStaticBlockSerializationProof testSerializableInstance;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(serializedClassPath))) {
            testSerializableInstance = ((EagerSingletonStaticBlockSerializationProof) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertSame(instance, testSerializableInstance); // √
    }

}
