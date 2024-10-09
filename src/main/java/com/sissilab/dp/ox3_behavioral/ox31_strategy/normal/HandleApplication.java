package com.sissilab.dp.ox3_behavioral.ox31_strategy.normal;

/**
 * 测试应用
 */
public class HandleApplication {

    public static void main(String[] args) {
        // 图片处理策略
        HandleContext imageHandleContext = new HandleContext(new ImageHandleStrategy());
        imageHandleContext.handle();

        // 视频处理策略
        HandleContext videoHandleContext = new HandleContext(new VideoHandleStrategy());
        videoHandleContext.handle();

        // 音频处理策略
        HandleContext audioHandleContext = new HandleContext(new AudioHandleStrategy());
        audioHandleContext.handle();
    }
}
