package com.xixi.lab.dp.ox3_behavioral.ox3_01_strategy.normal;

/**
 * 具体策略类：音频处理策略
 */
public class AudioHandleStrategy implements IHandleStrategy {
    @Override
    public void handle() {
        System.out.println("handle audio...");
    }
}
