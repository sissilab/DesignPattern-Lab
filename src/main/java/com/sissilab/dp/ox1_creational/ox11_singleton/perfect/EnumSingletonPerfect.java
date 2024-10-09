package com.sissilab.dp.ox1_creational.ox11_singleton.perfect;

import java.util.concurrent.TimeUnit;

/**
 * Use this singleton:
 * `EnumSingletonPerfect.instance.anyMethod();`
 */
public enum EnumSingletonPerfect {
    instance;

    public void anyMethod() {
    }

    /**
     * Access the lazy-loaded resource:
     * `EnumSingletonPerfect.instance.getExpensiveResource().useResource();`
     */
    // Using a static inner class to implement lazy loading of heavy resources
    private static class LazyHeavyResource {
        private static final HeavyResource RESOURCE = new HeavyResource();
    }

    public HeavyResource getExpensiveResource() {
        return LazyHeavyResource.RESOURCE;
    }

    // This static inner class represents a heavy resource.
    private static class HeavyResource {
        HeavyResource() {
            // simulate expensive initialization... need a lot of time to load
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        void useResource() {
            System.out.println("Use this heavy resource...");
        }
    }
}
