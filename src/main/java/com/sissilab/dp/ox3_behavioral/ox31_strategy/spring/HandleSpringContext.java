package com.sissilab.dp.ox3_behavioral.ox31_strategy.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理上下文类：
 * 面向策略设计的类，包含策略接口的变量；包含一个委托方法，委托策略变量调用具体实现策略的处理方法
 */
@Component
public class HandleSpringContext {

    /**
     * 策略类型
     */
    public static final String IMAGE_STRATEGY_TYPE = "image";
    public static final String VIDEO_STRATEGY_TYPE = "video";
    public static final String AUDIO_STRATEGY_TYPE = "audio";

    /**
     * Spring会自动将IHandleSpringStrategy接口的实现类注入到这个Map中：bean id -> 对应的策略实现类
     */
    @Autowired
    private Map<String, IHandleSpringStrategy> strategyMap;


    /**
     * 委托处理方法，根据构造函数或改变策略方法设置的策略接口的变量，以决定采用何种处理策略
     */
    public String handle(String type) {
        IHandleSpringStrategy handleSpringStrategy = null;
        try {
            // 根据策略类型，从容器中获取相应具体策略实例
            handleSpringStrategy = strategyMap.get(type);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        if (null == handleSpringStrategy) {
            return "获取具体策略处理类失败！";
        }
        return handleSpringStrategy.handle();
    }
}
