package com.sissilab.dp.ox3_behavioral.ox31_strategy.spring;

/**
 * 抽象策略类：文件处理策略
 */
public interface IHandleSpringStrategy {
    /**
     * 针对不同类型的文件，采用不同的处理策略
     */
    String handle();
}