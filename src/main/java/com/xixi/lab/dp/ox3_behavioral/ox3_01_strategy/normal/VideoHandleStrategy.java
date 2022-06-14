package com.xixi.lab.dp.ox3_behavioral.ox3_01_strategy.normal;

/**
 * 具体策略类：视频处理策略
 */
public class VideoHandleStrategy implements IHandleStrategy {
    @Override
    public void handle() {
        System.out.println("handle video...");
    }
}
